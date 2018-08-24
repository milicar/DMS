package com.mr.service.impl;

import com.mr.dao.ProcessDAO;
import com.mr.domain.Activity;
import com.mr.domain.Company;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.Subprocess;
import com.mr.service.ActivityService;
import com.mr.service.ProcessService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessDAO processDAO;
    @Autowired
    private ActivityService activityService;

    @Override
    public List<Process> findAll() {
        return processDAO.findAll();
    }

    @Override
    public Process findById(Long id) {
        return processDAO.getOne(id);
    }
    
    @Override
    public List<Process> findAllFor(Company parent){
        FirstLevelProcess process = new FirstLevelProcess();
        process.setParent(parent);
        return processDAO.findAll(Example.of(process));
    }
    
    @Override
    public List<Process> findAllFor(Process parent){
        Subprocess subprocess = new Subprocess();
        subprocess.setParent(parent);
        return processDAO.findAll(Example.of(subprocess));
    }

    @Override
    public Process save(Process process) {
        return processDAO.save(process);
    }

    @Override
    public void delete(Process process) {
        processDAO.delete(process);
    }

    @Override
    public Company addFirstLevelProcess(Process process, Company company) {
        ((FirstLevelProcess)process).setParent(company);
        save(process);
        return company;
    }

    @Override
    public Company removeFirstLevelProcess(Process process, Company company) {
        delete(process);
        return company;
    }

    @Override
    public Process addSubprocess(Process subprocess, Process parent) {
        if(!parent.isPrimitive()) ((Subprocess)subprocess).setParent(parent);
        save(subprocess);
        return parent;
    }

    @Override
    public Process deleteSubprocess(Process subprocess, Process parent) {
        delete(subprocess);
        return parent;
    }

    @Override
    public Process addActivity(Activity activity, Process parent) {
        if(!parent.isComplex()) activity.setParent(parent);
        activityService.save(activity);
        return parent;
    }

    @Override
    public Process deleteActivity(Activity activity, Process parent) {
        activityService.delete(activity);
        return parent;
    }

    
}
