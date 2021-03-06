package com.mr.domain;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("first")
public class FirstLevelProcess extends Process implements Serializable{

    @ManyToOne
    @JoinColumn(name = "company_parent")
    private Company parent;

    public FirstLevelProcess() {
    }

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }
    
    
}
