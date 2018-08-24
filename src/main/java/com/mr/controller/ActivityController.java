package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.DocumentTypeService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class ActivityController {

    @Autowired
    private LoginController loginController;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DocumentTypeService documentTypeService;

    private List<Activity> activityList;
    private Activity activity;
    private List<DocumentType> inDocumentTypeList;
    private List<DocumentType> outDocumentTypeList;

    private Process parent;

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

    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }

    public String showAll() {
        setActivityList(activityService.findAll());
        return "list_activities";
    }

    public String showAllFor(Process parent) {
        setParent(parent);
        setActivityList(activityService.findAllFor(parent));
        return "list_activities";
    }
    
    public String showAllFor(User loggedInUser){
        if (loggedInUser.getUserRole().toString().equals("ADMIN")) return showAll();
        else setActivityList(loginController.getActivitiesForUser());
        return "list_activities";
    }

    public String show(Activity activity) {
        setActivity(activity);
        setParent(activity.getParent());
        setInDocumentTypeList(documentTypeService.findAllFor(activity, ActivityDocumentType.Direction.IN));
        setOutDocumentTypeList(documentTypeService.findAllFor(activity, ActivityDocumentType.Direction.OUT));
        return "activity";
    }

    public String save() {
        this.activity.setParent(parent);
        activityService.save(activity);
        return showAllFor(parent);
    }

    public String edit(Activity activity) {
        setActivity(activity);
        return "activity_form";
    }

    public String delete(Activity activity) {
        activityService.delete(activity);
        return showAllFor(parent);
    }

    public String createNewFor(Process parent) {
        setParent(parent);
        setActivity(new Activity());
        return "activity_form";
    }

    @PostConstruct
    public void init() {
        activityList = activityService.findAll();
    }

}
