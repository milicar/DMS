package com.mr.service.impl;

import com.mr.dao.ProcessDAO;
import com.mr.domain.Process;
import com.mr.service.ProcessService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    ProcessDAO processDAO;

    @Override
    public List<Process> findAll() {
        return processDAO.findAll();
    }

    @Override
    public Process findById(Long id) {
        return processDAO.getOne(id);
    }

    @Override
    public Process save(Process process) {
        return processDAO.save(process);
    }

    @Override
    public void delete(Process process) {
        processDAO.delete(process);
    }

}
