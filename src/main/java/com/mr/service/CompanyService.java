
package com.mr.service;

import com.mr.domain.Company;
import java.util.List;


public interface CompanyService {

    List<Company> findAll();
    
    Company findById(Long id);
    
    Company save(Company company);
    
    void delete(Company company);
}
