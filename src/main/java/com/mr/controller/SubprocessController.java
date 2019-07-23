package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.Process;
import com.mr.domain.Subprocess;
import com.mr.service.ActivityService;
import com.mr.service.ProcessService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class SubprocessController {

    @Autowired
    private ProcessService processService;
    @Autowired
    private ActivityService activityService;

    private Subprocess subprocess;
    private Process parent;
    private List<Process> subprocessList;
    private List<Process> processChildren;
    private List<Activity> activityChildren;

    public SubprocessController() {
        this.subprocess = new Subprocess();
        this.activityChildren = new ArrayList<>();
        this.processChildren = new ArrayList<>();
    }

    public Subprocess getSubprocess() {
        return subprocess;
    }

    public void setSubprocess(Subprocess subprocess) {
        this.subprocess = subprocess;
    }

    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }

    public List<Process> getSubprocessList() {
        return subprocessList;
    }

    public void setSubprocessList(List<Process> subprocessList) {
        this.subprocessList = subprocessList;
    }

    public List<Process> getProcessChildren() {
        return processChildren;
    }

    public void setProcessChildren(List<Process> processChildren) {
        this.processChildren = processChildren;
    }

    public List<Activity> getActivityChildren() {
        return activityChildren;
    }

    public void setActivityChildren(List<Activity> activityChildren) {
        this.activityChildren = activityChildren;
    }

    public String createNewFor(Process parent) {
        setParent(parent);
        setSubprocess(new Subprocess());
        return "subprocess_form";
    }

    public String showAllFor(Process parent) {
        setParent(parent);
        setSubprocessList(processService.findAllFor(parent));
        return "list_subprocesses";
    }

    public String show(Subprocess subprocess) {
        setParent(subprocess.getParent());
        setSubprocess(subprocess);
        setProcessChildren(processService.findAllFor(subprocess));
        setActivityChildren(activityService.findAllFor(subprocess));
        return "subprocess";
    }

    public String save() {
        this.subprocess.setParent(this.parent);
        processService.save(subprocess);
        return showAllFor(parent);
    }

    public String edit(Subprocess subprocess) {
        setSubprocess(subprocess);
        return "subprocess_form";
    }

    public String delete(Subprocess subprocess) {
        processService.delete(subprocess);
        return showAllFor(parent);
    }

}
