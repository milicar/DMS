
package com.mr.service;

import java.util.List;
import com.mr.domain.Process;



public interface ProcessService {

    List<Process> findAll();

    Process findById(Long id);

    Process save(com.mr.domain.Process process);

    void delete(Process process);
}
