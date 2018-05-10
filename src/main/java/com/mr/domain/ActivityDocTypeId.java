package com.mr.domain;

import java.io.Serializable;
import java.util.Objects;


public class ActivityDocTypeId implements Serializable{
    private Long activity;
    private Long documentType;

    public ActivityDocTypeId() {
    }

    public Long getActivity() {
        return activity;
    }

    public Long getDocumentType() {
        return documentType;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.activity);
        hash = 97 * hash + Objects.hashCode(this.documentType);
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
        final ActivityDocTypeId other = (ActivityDocTypeId) obj;
        if (!Objects.equals(this.activity, other.activity)) {
            return false;
        }
        if (!Objects.equals(this.documentType, other.documentType)) {
            return false;
        }
        return true;
    }
    
    
}
