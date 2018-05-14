package com.mr.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "document_descriptor")
public class DocumentDescriptor implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "descriptor_id")
    private Long id;
    @Column(name = "descriptor_value")
    private String descriptorValue;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "document_type_descriptor_id")
    private DocumentTypeDescriptor documentTypeDescriptor;

    public DocumentDescriptor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptorValue() {
        return descriptorValue;
    }

    public void setDescriptorValue(String descriptorValue) {
        this.descriptorValue = descriptorValue;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentTypeDescriptor getDocumentTypeDescriptor() {
        return documentTypeDescriptor;
    }

    public void setDocumentTypeDescriptor(DocumentTypeDescriptor documentTypeDescriptor) {
        this.documentTypeDescriptor = documentTypeDescriptor;
    }
    
    
}
