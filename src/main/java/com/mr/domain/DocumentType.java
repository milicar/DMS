package com.mr.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "document_type")
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id")
    private Long documentTypeID;
    @Column(name = "name")
    private String name;
    @Column(name = "model_location")
    private String modelLocation;
    @Lob
    @Column(name = "short_description")
    private String shortDescription;

    public DocumentType() {
    }

    public Long getDocumentTypeID() {
        return documentTypeID;
    }

    public void setDocumentTypeID(Long documentTypeID) {
        this.documentTypeID = documentTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelLocation() {
        return modelLocation;
    }

    public void setModelLocation(String modelLocation) {
        this.modelLocation = modelLocation;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
