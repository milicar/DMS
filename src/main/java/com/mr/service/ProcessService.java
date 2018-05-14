
package com.mr.service;

import com.mr.domain.Activity;
import com.mr.domain.Company;
import java.util.List;
import com.mr.domain.Process;



public interface ProcessService {

    List<Process> findAll();

    Process findById(Long id);

    Process save(Process process);

    void delete(Process process);
    
    Company addFirstLevelProcess(Process process, Company company);
    
    Company removeFirstLevelProcess(Process process, Company company);
    
    Process addSubprocess(Process subprocess, Process parent);
    
    Process deleteSubprocess(Process subprocess, Process parent);
    
    Process addActivity(Activity activity, Process parent);
    
    Process deleteActivity(Activity activity, Process parent);
}
