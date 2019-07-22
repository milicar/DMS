package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.Company;
import com.mr.domain.Process;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.ProcessService;
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class FlProcessController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private HierarchyController hierarchyController;
    private FirstLevelProcess flProcess;
    private Company parentCompany;
    private List<Process> processes;
    private List<Process> processChildren;
    private List<Activity> activityChildren;

    public FlProcessController() {
        flProcess = new FirstLevelProcess();
        parentCompany = new Company();
        processChildren = new ArrayList<>();
        activityChildren = new ArrayList<>();

    }

    public FirstLevelProcess getFlProcess() {
        return flProcess;
    }

    public void setFlProcess(FirstLevelProcess flProcess) {
        this.flProcess = flProcess;
    }

    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<Process> getProcessChildren() {
        return processChildren;
    }

    public void setProcessChildren(List<Process> children) {
        this.processChildren = children;
    }

    public List<Activity> getActivityChildren() {
        return activityChildren;
    }

    public void setActivityChildren(List<Activity> activityChildren) {
        this.activityChildren = activityChildren;
    }

    public String showAll() {
        setProcesses(processService.findAll());
        return "list_flprocesses";
    }

    public String showAllFor(Company parent) {
        setParentCompany(parent);
        setProcesses(processService.findAllFor(parent));
        return "list_flprocesses";
    }

    public String showAllFor(User loggedInUser) { 
        if (userService.isAdmin(loggedInUser)) {
            return showAll(); 
        } else {
            setProcesses(hierarchyController.buildListOfFirstLevelProcessesFor(loggedInUser)); 
        }
        return "list_flprocesses";
    }

    public String createNewFor(Company parent) {
        setParentCompany(parent);
        setFlProcess(new FirstLevelProcess());
        return "flprocess_form";
    }

    public String show(FirstLevelProcess flProcess) {
        setFlProcess(flProcess);
        setProcessChildren(processService.findAllFor(flProcess));
        setActivityChildren(activityService.findAllFor(flProcess));
        return "flprocess";
    }

    public String save() {
        this.flProcess.setParent(this.parentCompany);
        processService.save(flProcess);
        return showAllFor(parentCompany);
    }

    public String edit(FirstLevelProcess flp) {
        setFlProcess(flp);
        return "flprocess_form";
    }

    public String delete(FirstLevelProcess flp) {
        processService.delete(flp);
        return showAllFor(parentCompany);
    }

}
