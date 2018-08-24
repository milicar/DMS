package com.mr.controller;

import com.mr.domain.Company;
import com.mr.domain.User;
import com.mr.service.CompanyService;
import com.mr.service.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    
    private User user;
    private List<User> userList;
    private Company company;
    private Long compName;
    private List<Company> companies;

    public UserController() {
    }
    
    
    
    @PostConstruct
    public void init(){
        userList = userService.findAll();
        companies = companyService.findAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getCompName() {
        return compName;
    }

    public void setCompName(Long compName) {
        this.compName = compName;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
    
     
    
    
   
    public String createNewFor(Company company){
        setCompany(company);
        setUser(new User());
        return "user_form";
    } 
    
    public String show(User user){
        setUser(user);
        setCompany(user.getCompany());
        return "user";
    }
    
    public String edit(User user){
        setUser(user);
        setCompany(company);
        return "user_form";
    }
    
    public String save(){
       // this.user.setCompany(companyService.findById(compName));
        userService.save(user);
        setUserList(userService.findAllFor(company));
        return "list_users";
    }
    
    public String delete(User user){
        userService.delete(user);
        setUserList(userService.findAllFor(company));
        return "list_users";
    }
    
    public String showAll(){
        setUserList(userService.findAll());
        return "list_users";
    }

//    private Company convertStringToCompany(String compName) {
//        List<Company> companies = companyService.findAll();
//        if("Company1".equals(compName)) return companies.get(0);
//        else return companies.get(1);
//    }
}
