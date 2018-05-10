package com.mr.service.impl;

import com.mr.dao.CompanyDAO;
import com.mr.domain.Company;
import com.mr.service.CompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDAO companyDAO;

    @Override
    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyDAO.save(company);
    }

    @Override
    public void delete(Company company) {
        companyDAO.delete(company);
    }

}
