package com.mr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mr.domain.Process;
import java.util.List;


public interface ProcessDAO extends JpaRepository<Process, Long>{
    
   

}
