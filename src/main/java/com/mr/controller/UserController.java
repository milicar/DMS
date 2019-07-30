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
    private String companyIdString;

    public UserController() {
    }
    
    
    
    @PostConstruct
    public void init(){
        userList = userService.findAll();
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

    public String getCompanyIdString() {
        return companyIdString;
    }

    public void setCompanyIdString(String companyIdString) {
        this.companyIdString = companyIdString;
    }
    
    private Company getCompanyFromId(String companyIdString) {
        return companyService.findById(Long.parseLong(companyIdString));
    }
  
     
    
    
   
    
    public String createNew(){
        setCompanyIdString("0");
        setUser(new User());
        return "user_form";
    }
    
    public String show(User user){
        setUser(user);
        return "user";
    }
    
    public String edit(User user){
        setUser(user);
        setCompanyIdString(user.getCompany().getCompanyID().toString());
        return "user_form";
    }
    
    public String save(){
        this.user.setCompany(getCompanyFromId(companyIdString));
        userService.save(user);
        return showAll();
    }
    
    public String delete(User user){
        userService.delete(user);
        return showAll();
    }
    
    public String showAll(){
        setUserList(userService.findAll());
        return "list_users";
    }

}
