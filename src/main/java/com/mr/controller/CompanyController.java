package com.mr.controller;

import com.mr.domain.Company;
import com.mr.service.CompanyService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    private List<Company> companyList;
    private Company company;

    public CompanyController() {
        company = new Company();
        companyList = new ArrayList<>();
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
        setCompany(new Company());
        return "company_form";
    }
    
    public String show(Company company){
        setCompany(company);
        return "company";
    }
    
    public String showAll(){
        setCompanyList(companyService.findAll());
        return "list_companies";
    }
    
    public String edit(Company company){
        setCompany(company);
        return "company_form";
    }
    
    public String save(){
        companyService.save(company);
        return showAll();
    }
    
    public String delete(Company company){
        companyService.delete(company);
        return showAll();
    }
    
    @PostConstruct
    public void init(){
    }
}
