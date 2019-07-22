package com.mr.controller;

import com.mr.domain.Activity;
import com.mr.domain.Document;
import com.mr.domain.DocumentType;
import com.mr.domain.Process;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import com.mr.service.ProcessService;
import com.mr.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyController companyController;
    @Autowired
    private ProcessService processService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DocumentTypeService documentTypeService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private HierarchyController hierarchyController;
   
    private User loggedInUser;
    private String username;
    private String password;

    public User getLoggedInUser() {
        return loggedInUser;
    }
//make private all of these

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return this.loggedInUser.getUserRole().toString().equals("ADMIN"); //delegate to user service
    }

    public String login() {
        Optional<User> queriedUser = userService.findByUsername(username);
        if (queriedUser.isPresent()) {
            User qu = (User) queriedUser.get();
            if (getPassword().equals(qu.getPassword())) {
                setLoggedInUser(qu);
                return hierarchyController.start(getLoggedInUser());
            } else {
                getFacesContextInstance().addMessage(null, new FacesMessage("Invalid password!"));
                return "";
            }

        } else {
            getFacesContextInstance().addMessage(null, new FacesMessage("Invalid credentials!"));
            return "";
        }
    }

    protected FacesContext getFacesContextInstance() {
        return FacesContext.getCurrentInstance();
    }


    public String logout() {
        setLoggedInUser(null);
        return "index";
    }

    public List<Process> getFlProcessesForUser() {
        return hierarchyController.getFlProcessesForUser(loggedInUser); 
       // findAllFlProcesses = processService.findAllFor(getLoggedInUser().getCompany()); // user service // ping-pong!!
    }

    public List<Process> getSubprocessesForUser() {
        return hierarchyController.getSubprocessesForUser(loggedInUser);
    }

    public List<Activity> getActivitiesForUser() {
        return hierarchyController.getActivitiesForUser(loggedInUser);
       
    }

    public List<DocumentType> getDocTypesForUser() {
        return hierarchyController.getDocTypesForUser(loggedInUser);
       
    }

    public List<Document> getDocumentsForUser() {
        return hierarchyController.getDocumentsForUser(loggedInUser);
        
    }
}
