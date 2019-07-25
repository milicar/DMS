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
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class HierarchyController {

    @Autowired
    private CompanyController companyController;
    @Autowired
    private UserService userService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DocumentTypeService documentTypeService;
    @Autowired
    private DocumentService documentService;

    public String start(User loggedInUser) {
        if (userService.isAdmin(loggedInUser)) {
            return companyController.showAll();
        } else {
            return companyController.show(userService.getUsersCompany(loggedInUser));
        }
    }

    public List<Process> buildListOfFirstLevelProcessesFor(User loggedInUser) {
        return processService.findAllFor(userService.getUsersCompany(loggedInUser));
    }

    public List<Process> buildListOfSubprocessesFor(User loggedInUser) {
        List<Process> subs = new ArrayList<>();
        buildListOfFirstLevelProcessesFor(loggedInUser).forEach(fl -> processService.findAllFor(fl)
                .forEach(s -> subs.add(s)));
        return subs;
    }

    public List<Activity> buildListOfActivitiesFor(User loggedInUser) {
        List<Activity> acts = new ArrayList<>();
        buildListOfFirstLevelProcessesFor(loggedInUser).forEach(fl -> activityService.findAllFor(fl)
                .forEach(a -> acts.add(a)));
        buildListOfSubprocessesFor(loggedInUser).forEach(sub -> activityService.findAllFor(sub)
                .forEach(a -> acts.add(a)));
        return acts;
    }

    public List<DocumentType> buildListOfDocumentTypesFor(User loggedInUser) {
        List<DocumentType> doctypes = new ArrayList<>();
        buildListOfActivitiesFor(loggedInUser).forEach(act -> documentTypeService.findAllFor(act)
                .forEach(dt -> doctypes.add(dt)));
        return doctypes;
    }

    public List<Document> BuildListOfDocumentsFor(User loggedInUser) {
        List<Document> docs = new ArrayList<>();
        buildListOfDocumentTypesFor(loggedInUser).forEach(dt -> documentService.findAllFor(dt)
                .forEach(d -> docs.add(d)));
        return docs;
    }

}
