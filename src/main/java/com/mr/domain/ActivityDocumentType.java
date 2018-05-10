package com.mr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity_document_type")
@IdClass(ActivityDocTypeId.class)
public class ActivityDocumentType {

    @Id
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @Id
    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    
    
    public enum Direction{
        IN, OUT
    }

    public ActivityDocumentType() {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    
}
