package com.mr.controller;

import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentTypeService dtService;
    
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
    
    
        
   public String createNew(DocumentType documentType){
       setDocumentType(documentType);
       setDocument(new Document());
       document.setDocumentType(documentType);
       return "document_form";
   } 
   
   public String showDocumentsFor(DocumentType documentType){
       setDocumentType(documentType);
       this.documentList = documentService.findForDocType(documentType);
       return "list_documents";
   }
   
   public String show(Document document){
//       setDocument(document);
//       setTagList(documentService.findTagsFor(document));
//       setDescriptorList(documentService.findDescriptorsFor(document));
       setDocument(documentService.loadDocument(document));
       return "document";
   }
   
   public String showAll(){
       this.documentList = documentService.findAll();
       //setDocumentList(documentService.findAll());
       return "list_documents";
   }
   
   public String delete(Document document){
       documentService.delete(document);
       setDocumentList(documentService.findForDocType(getDocumentType()));
       return "list_documents";
   }
   
   public String deleteThis(){
       return delete(getDocument());
   }
   
   public String edit(Document document){
       setDocument(document);
       return "document_form";
   }
   
   public String editThis(){
       return edit(getDocument());
   }

   @PostConstruct
   public void init(){
//       document = new Document();
//       documentType = dtService.findById(1L);
//       documentList = documentService.findForDocType(documentType);
documentList = documentService.findAll();
   }
           
}
