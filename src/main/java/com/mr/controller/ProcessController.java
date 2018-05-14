package com.mr.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.mr.domain.Process;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Subprocess;
import com.mr.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Controller
@Scope("request")
public class ProcessController {

    @Autowired
    private ProcessService processService;
    
    private List<Process> processList;
    private Process process;

    public ProcessController() {
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    } 
    
    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    public void getProcessById(Long id){
        this.process = processService.findById(1L);
    }
    
    public void init(){
        processList = processService.findAll();
    }
    
    public String showAll(){
        processList = processService.findAll();
        return "list_processes";
    }
    
    public String show(Process process){
        return "";
    }
}
