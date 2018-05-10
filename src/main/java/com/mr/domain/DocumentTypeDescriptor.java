package com.mr.domain;



import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="document_type_descriptor")
public class DocumentTypeDescriptor implements Serializable, Comparable<DocumentTypeDescriptor>{
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="doctype_descriptor_id")
    private Long descriptorID;
    @Column(name="doctype_descriptor_name")
    private String descriptorName;
    @ManyToOne
    @JoinColumn(name="doctype_id")
    private DocumentType documentType;

    public DocumentTypeDescriptor() {
    }

    public Long getDescriptorID() {
        return descriptorID;
    }

    public void setDescriptorID(Long descriptorID) {
        this.descriptorID = descriptorID;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final DocumentTypeDescriptor other = (DocumentTypeDescriptor) obj;
        if (!Objects.equals(this.descriptorName, other.descriptorName)) {
            return false;
        }
        if (!Objects.equals(this.descriptorID, other.descriptorID)) {
            return false;
        }
        if (!Objects.equals(this.documentType, other.documentType)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "DocumentTypeDescriptor{" + "descriptorID=" + descriptorID + ", descriptorName=" + descriptorName + '}';
    }

    @Override
    public int compareTo(DocumentTypeDescriptor that) {
        return this.getDescriptorID().compareTo(that.getDescriptorID());
    }
    
    
    
    
}
