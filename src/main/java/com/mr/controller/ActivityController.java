package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.service.ActivityDocTypeService;
import com.mr.service.ActivityService;
import com.mr.service.DocumentTypeService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private DocumentTypeService documentTypeService;
    
    private List<Activity> activityList;
    private Activity activity;
    private List<DocumentType> inDocumentTypeList;
    private List<DocumentType> outDocumentTypeList;
    

    public ActivityController() {
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<DocumentType> getInDocumentTypeList() {
        return inDocumentTypeList;
    }

    public void setInDocumentTypeList(List<DocumentType> inDocumentTypeList) {
        this.inDocumentTypeList = inDocumentTypeList;
    }

    public List<DocumentType> getOutDocumentTypeList() {
        return outDocumentTypeList;
    }

    public void setOutDocumentTypeList(List<DocumentType> outDocumentTypeList) {
        this.outDocumentTypeList = outDocumentTypeList;
    }
    
    public String show(Activity activity){
        setActivity(activity);
        setInDocumentTypeList(documentTypeService.findAllDocTypesFor(activity, ActivityDocumentType.Direction.IN));
        setOutDocumentTypeList(documentTypeService.findAllDocTypesFor(activity, ActivityDocumentType.Direction.OUT));
        return "activity";
    }
   
    @PostConstruct
    public void init(){
        activityList = activityService.findAll();
    }
}
