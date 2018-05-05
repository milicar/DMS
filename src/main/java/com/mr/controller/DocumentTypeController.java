package com.mr.controller;

import com.mr.domain.DocumentType;
import com.mr.service.DocumentTypeService;
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

    private DocumentType documentType = new DocumentType();
    private List<DocumentType> documentTypeList;

    public DocumentType getDocumentType(){
        return documentType;
    }

    public String show(DocumentType documentType){
        this.documentType = documentType;
        return "document_type.xhtml";
    }

    public String save(){
        documentTypeService.save(documentType);
        documentType = new DocumentType();
        return "list_document_types.xhtml";
    }

    public String delete(DocumentType documentType){
        documentTypeService.delete(documentType.getDocumentTypeID());
        return "list_document_types.xhtml";
    }

    public List<DocumentType> getDocumentTypeList() {
        return documentTypeService.findAll();
    }
    public void setDocumentTypeList(List<DocumentType> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    @PostConstruct
    public void init(){
        documentTypeList = documentTypeService.findAll();
    }
}
