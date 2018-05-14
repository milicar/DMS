package com.mr.controller;

import com.mr.domain.Company;
import com.mr.service.CompanyService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    private List<Company> companyList;
    private Company company;

    public CompanyController() {
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public String createNew(){
        return "";
    }
    
    public String show(Company company){
        return "";
    }
    
    public String edit(Company company){
        return "";
    }
    
    public String delete(Company company){
        companyService.delete(company);
        setCompanyList(companyService.findAll());
        return "list_companies";
    }
    
    @PostConstruct
    public void init(){
        setCompanyList(companyService.findAll());
        setCompany(new Company());
    }
}
