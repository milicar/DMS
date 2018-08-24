package com.mr.controller;

import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.domain.User;
import com.mr.service.DocumentDescriptorService;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private LoginController loginController;
    @Autowired
    private DocumentDescriptorService ddService;

    private List<Document> documentList;
    private Document document;
    private DocumentType documentType;
    private List<DocumentTag> tagList;
    private List<DocumentDescriptor> descriptorList;

   
    public DocumentController() {
        document = new Document();
        documentType = new DocumentType();
        tagList = new ArrayList<>();
        descriptorList = new ArrayList<>();
        documentList = new ArrayList<>();
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public List<DocumentTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<DocumentTag> tagList) {
        this.tagList = tagList;
    }

    public List<DocumentDescriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<DocumentDescriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public String createNewFor(DocumentType documentType) {
        setDocument(new Document());
        setDocumentType(documentType);
        document.setDocumentType(documentType);
        setTagList(new ArrayList());
        document.setTagList(this.tagList);
        setDescriptorList(initializeDescriptorsFor(this.document));
        document.setDocumentDescriptorList(this.descriptorList);
        return "document_form";
    }

    public String showAllFor(DocumentType documentType) {
        setDocumentType(documentType);
        setDocumentList(documentService.findAllFor(documentType));
        return "list_documents";
    }

   
    public String show(Document document) {
        setDocument(document);
        //       setDocument(documentService.loadDocument(document));
        setTagList(documentService.findTagsFor(document));
        setDescriptorList(documentService.findDescriptorsFor(document));
        return "document";
    }

    public String showAll() {
        this.documentList = documentService.findAll();
        //setDocumentList(documentService.findAll());
        return "list_documents";
    }
    
    public String showAllFor(User loggedInUser){
        if (loggedInUser.getUserRole().toString().equals("ADMIN")) return showAll();
        else setDocumentList(loginController.getDocumentsForUser());
        return "list_documents";
    }

    public String delete(Document document) {
        documentService.delete(document);
        setDocumentList(documentService.findAllFor(getDocumentType()));
        return "list_documents";
    }

    public String deleteThis() {
        return delete(getDocument());
    }

    public String edit(Document document) {
        setDocument(document);
        setDescriptorList(documentService.findDescriptorsFor(document));
        setTagList(documentService.findTagsFor(document));
        return "document_form";
    }

   
    public String save() {
        documentService.save(document);
        descriptorList.forEach(desc -> ddService.save(desc));
    //    tagList.forEach(tag -> documentService.addTag(tag.getTagValue(), document));
        
        //ddService.update(descriptorList, document);
        
        
        //documentType = document.getDocumentType();
        //descriptorService.update(descriptorList, documentType);
        //descriptorList = new ArrayList<>();
        return showAllFor(documentType);
    }

    public void removeTag(DocumentTag tag){
        documentService.removeTag(tag, document);
        setTagList(documentService.findTagsFor(document));
    }
    
    public String addTag(String tagValue){
        DocumentTag tag = new DocumentTag();
        tag.setTagValue(tagValue);
        tag.setDocument(this.document);
        documentService.addTag(tagValue, document);
        setTagList(documentService.findTagsFor(document));
        tag = new DocumentTag();
        return "document_form";
    }
    
    public String insertTag(){
        return "new_tag_fragment";
    }
    
    public String cancelTag(){
        return "document_form";
    }

    private List<DocumentDescriptor> initializeDescriptorsFor(Document document) {
        List<DocumentDescriptor> descriptors = new ArrayList<>();
        List<DocumentTypeDescriptor> dtDescriptors = document.getDocumentType().getDocumentTypeDescriptors();
        dtDescriptors.forEach(dtd -> {
            DocumentDescriptor dd = new DocumentDescriptor();
            dd.setDocumentTypeDescriptor(dtd);
            dd.setDocument(document);
            dd.setDescriptorValue("");
            descriptors.add(dd);
        });
        return descriptors;
    }

}
