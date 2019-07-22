package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.Document;
import com.mr.domain.DocumentType;
import com.mr.domain.Process;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import com.mr.service.ProcessService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class HierarchyController {

    @Autowired
    LoginController loginController;
    @Autowired
    CompanyController companyController;
    @Autowired
    ProcessService processService;
    @Autowired
    ActivityService activityService;
    @Autowired
    DocumentTypeService documentTypeService;
    @Autowired
    DocumentService documentService;

    public String start(User loggedInUser) {
        if (loginController.isAdmin()) {  //prebaci na user service
            return companyController.showAll();
        } else {
            return companyController.show(loggedInUser.getCompany()); // user service
        }
    }

    public List<Process> getFlProcessesForUser(User loggedInUser) {
        return processService.findAllFor(loggedInUser.getCompany()); // user service // ping-pong!!
    }

    public List<Process> getSubprocessesForUser(User loggedInUser) {
        List<Process> subs = new ArrayList<>();
        getFlProcessesForUser(loggedInUser).forEach(fl -> processService.findAllFor(fl)
                .forEach(s -> subs.add(s)));
        return subs;
    }

    public List<Activity> getActivitiesForUser(User loggedInUser) {
        List<Activity> acts = new ArrayList<>();
        getFlProcessesForUser(loggedInUser).forEach(fl -> activityService.findAllFor(fl)
                .forEach(a -> acts.add(a)));
        getSubprocessesForUser(loggedInUser).forEach(sub -> activityService.findAllFor(sub)
                .forEach(a -> acts.add(a)));
        return acts;
    }

    public List<DocumentType> getDocTypesForUser(User loggedInUser) {
        List<DocumentType> doctypes = new ArrayList<>();
        getActivitiesForUser(loggedInUser).forEach(act -> documentTypeService.findAllFor(act)
                .forEach(dt -> doctypes.add(dt)));
        return doctypes;
    }

    public List<Document> getDocumentsForUser(User loggedInUser) {
        List<Document> docs = new ArrayList<>();
        getDocTypesForUser(loggedInUser).forEach(dt -> documentService.findAllFor(dt)
                .forEach(d -> docs.add(d)));
        return docs;
    }

}
