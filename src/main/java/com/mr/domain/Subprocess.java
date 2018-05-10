package com.mr.domain;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("sub")
public class Subprocess extends Process implements Serializable{

    @ManyToOne
    @JoinColumn(name = "process_parent")
    private Process parent;

    public Subprocess() {
    }

    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }
    
    
}
