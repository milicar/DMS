package com.mr.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Document implements Serializable{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_id")
    private Long documentID;
    @Column(name="document_location")
    private String documentLocation;
    @Column(name="document_name")
    private String documentName;
    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name="document_type_id")
    private DocumentType documentType;
    
    @OneToMany(mappedBy = "document")
    private List<DocumentTag> tagList;
    
    @ElementCollection
    @CollectionTable(name = "document_descriptor")
    @MapKeyJoinColumn(name = "descriptor_id")
    @Column(name = "descriptor_value")
    private Map<DocumentTypeDescriptor, String> documentDescriptorMap;

    public Document() {
    }

    public Long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Long documentID) {
        this.documentID = documentID;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
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

    public Map<DocumentTypeDescriptor, String> getDocumentDescriptorMap() {
        return documentDescriptorMap;
    }

    public void setDocumentDescriptorMap(Map<DocumentTypeDescriptor, String> documentDescriptorMap) {
        this.documentDescriptorMap = documentDescriptorMap;
    }
    
    
    
}
