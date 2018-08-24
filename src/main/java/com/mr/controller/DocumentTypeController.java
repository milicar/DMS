package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.domain.User;
import com.mr.service.ActivityDocTypeService;
import com.mr.service.DocumentTypeDescriptorService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class DocumentTypeController {

    @Autowired
    private LoginController loginController;
    @Autowired
    private DocumentTypeService documentTypeService;
    @Autowired
    private DocumentTypeDescriptorService descriptorService;
    @Autowired
    private ActivityDocTypeService adtService;

    private DocumentType documentType;
    private List<DocumentType> documentTypeList;
    private DocumentTypeDescriptor descriptor;
    private List<DocumentTypeDescriptor> descriptorList;
    private Activity activity;
    private ActivityDocumentType.Direction direction;

    public DocumentTypeController() {

        documentType = new DocumentType();
        descriptor = new DocumentTypeDescriptor();
        descriptorList = new ArrayList<>();
        documentTypeList = new ArrayList<>();
        activity = new Activity();
    }

    /* document type */
    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public List<DocumentType> getDocumentTypeList() {
        return this.documentTypeList;
    }

    public void setDocumentTypeList(List<DocumentType> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityDocumentType.Direction getDirection() {
        return direction;
    }

    public void setDirection(ActivityDocumentType.Direction direction) {
        this.direction = direction;
    }

    public String createNew() {
        setDocumentType(new DocumentType());
        setDescriptorList(new ArrayList<>());
        return "document_type_form";
    }

    public String createNewFor(Activity activity, String sDirection) {
        setActivity(activity);
        setDirection(directionFromString(sDirection));
        setDocumentType(new DocumentType());
        setDescriptorList(new ArrayList<>());
        return "document_type_form";
    }

    public String createNewForThis() {
        return createNewFor(getActivity(), stringFromDirection(getDirection()));
    }

    public String show(DocumentType documentType) {
        //     setDocumentType(documentTypeService.loadDocumentType(documentType));
        setDocumentType(documentType);
        setDescriptorList(findAllForDocType(documentType));
        return "document_type";
    }

    public String showAllFor(Activity activity, String sDirection) {
        setActivity(activity);
        setDirection(directionFromString(sDirection));
        setDocumentTypeList(documentTypeService.findAllFor(activity, this.direction));
        return "list_document_types";
    }

    public String save() {
        documentTypeService.save(this.documentType);
        adtService.save(this.activity, this.documentType, this.direction);
        descriptorService.update(descriptorList, documentType);
        return showAllFor(this.activity, stringFromDirection(this.direction));
    }

    public String delete(DocumentType documentType) {
        documentTypeService.delete(documentType);
        return showAllFor(this.activity, stringFromDirection(this.direction));
    }

    public String edit(DocumentType documentType) {
        setDocumentType(documentType);
        setDescriptorList(documentType.getDocumentTypeDescriptors());
        return "document_type_form";
    }

    public String showAll() {
        documentTypeList = documentTypeService.findAll();
        return "list_document_types";
    }
    
    public String showAllFor(User loggedInUser){
        if (loggedInUser.getUserRole().toString().equals("ADMIN")) return showAll();
        else setDocumentTypeList(loginController.getDocTypesForUser());
        return "list_document_types";
    }

    private ActivityDocumentType.Direction directionFromString(String sDirection) {
        if ("IN".equals(sDirection)) {
            return ActivityDocumentType.Direction.IN;
        } else if ("OUT".equals(sDirection)) {
            return ActivityDocumentType.Direction.OUT;
        } else {
            return null;
        }
    }

    private String stringFromDirection(ActivityDocumentType.Direction direction) {
        if (direction == ActivityDocumentType.Direction.IN) {
            return "IN";
        } else if (direction == ActivityDocumentType.Direction.OUT) {
            return "OUT";
        } else {
            return null;
        }
    }

    /* descriptors */
    public DocumentTypeDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(DocumentTypeDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public List<DocumentTypeDescriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<DocumentTypeDescriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public List<DocumentTypeDescriptor> findAllForDocType(DocumentType documentType) {
        return descriptorService.findAllForDocType(documentType);
    }

    public String insertDescriptor() {
        return "new_descriptor_type_fragment";
    }

    public String cancelDescriptor() {
        return "document_type_form";
    }

    public String addDescriptor(String descriptorName) {
        descriptor.setDescriptorName(descriptorName);
        descriptor.setDocumentType(this.getDocumentType());
        descriptorList.add(descriptor);
        descriptor = new DocumentTypeDescriptor();
        return "document_type_form";
    }

    public String deleteDescriptor(DocumentTypeDescriptor descriptor) {
        descriptorList.remove(descriptor);
        return "document_type_form";
    }

    @PostConstruct
    public void init() {
    }
}
