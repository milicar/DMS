package com.mr.controller;

import com.mr.domain.User;
import com.mr.service.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class UserController {

    @Autowired
    private UserService userService;
    
    private User user;
    private List<User> userList;
    
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
    
    
}
