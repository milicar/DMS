package com.mr.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "process_type")
public abstract class Process implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long processID;
    @Column(name = "process_name")
    private String processName;
    @Column(name = "process_description")
    private String processDescription;
//    @Column
//    private boolean primitive;
//    
    @OneToMany(mappedBy = "parent")
    private List<Subprocess> subprocessList;
    @OneToMany(mappedBy = "parent")
    private List<Activity> activityList;

    public Process() {
    }

    public Long getProcessID() {
        return processID;
    }

    public void setProcessID(Long processID) {
        this.processID = processID;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public boolean isPrimitive() {
        return !(getActivityList().isEmpty());
    }
    
    public boolean isComplex(){
        return !(getSubprocessList().isEmpty());
    }

//    public void setPrimitive(boolean primitive) {
//        this.primitive = primitive;
//    }

    public List<Subprocess> getSubprocessList() {
        return subprocessList;
    }

    public void setSubprocessList(List<Subprocess> subprocessList) {
        this.subprocessList = subprocessList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
    
    
    
}
