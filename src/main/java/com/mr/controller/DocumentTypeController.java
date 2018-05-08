package com.mr.controller;

import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.service.DocumentTypeDescriptorService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.List;

@Component
@ManagedBean(name = "documentTypeController")
public class DocumentTypeController {
    
    @Autowired
    private DocumentTypeService documentTypeService;
    @Autowired
    private DocumentTypeDescriptorService descriptorService;
    
    private DocumentType documentType;    
    private List<DocumentType> documentTypeList;
    private DocumentTypeDescriptor descriptor;
    private List<DocumentTypeDescriptor> descriptorList;

    public DocumentTypeController() {
        
    }
    
    

    /* document type */
    public DocumentType getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
    
    public List<DocumentType> getDocumentTypeList() {
        return documentTypeService.findAll();
    }
    
    public void setDocumentTypeList(List<DocumentType> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }
    
    public String show(DocumentType documentType) {
        this.documentType = documentType;
        this.descriptorList = findAllForDocType(documentType);
        return "document_type";
    }
    
    public String save() {
        documentTypeService.save(documentType);
        descriptorService.update(descriptorList, documentType);
        documentType = new DocumentType();
        descriptorList = new ArrayList<>();
        return "list_document_types";
    }
    
    public String delete(DocumentType documentType) {
        documentTypeService.delete(documentType);
        return "list_document_types";
    }
    
    public String delete() {
        documentTypeService.delete(this.getDocumentType());
        return "list_document_types";
    }
    
    public String edit(DocumentType documentType) {
        this.documentType = documentType;
        return "document_type_form";
    }
    
    public String edit() {
        return "document_type_form";
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

    public String insertDescriptor(){
        return "newDescriptorFragment";
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
       // documentType = new DocumentType();
    documentTypeList  = documentTypeService.findAll();
    documentType  = new DocumentType();
    descriptor  = new DocumentTypeDescriptor();
    descriptorList  = new ArrayList<>();
    }
}
