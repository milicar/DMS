package com.mr.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    
    @OneToMany(mappedBy = "documentType", fetch=FetchType.EAGER)
    private List<DocumentTypeDescriptor> documentTypeDescriptors;

    
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
    
    public List<DocumentTypeDescriptor> getDocumentTypeDescriptors() {
        return documentTypeDescriptors;
    }

    public void setDocumentTypeDescriptors(List<DocumentTypeDescriptor> documentTypeDescriptors) {
        this.documentTypeDescriptors = documentTypeDescriptors;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.documentTypeID);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.modelLocation);
        hash = 59 * hash + Objects.hashCode(this.shortDescription);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentType other = (DocumentType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.modelLocation, other.modelLocation)) {
            return false;
        }
        if (!Objects.equals(this.shortDescription, other.shortDescription)) {
            return false;
        }
        if (!Objects.equals(this.documentTypeID, other.documentTypeID)) {
            return false;
        }
        return true;
    }
    
    
    
    @Override
    public String toString() {
        return "DocumentType{" + "documentTypeID=" + documentTypeID + ", name=" + name + ", modelLocation=" + modelLocation + ", shortDescription=" + shortDescription + ", documentTypeDescriptors=" + documentTypeDescriptors + '}';
    }
    
}
