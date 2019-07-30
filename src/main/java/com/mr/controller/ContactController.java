package com.mr.controller;

import com.mr.domain.Company;
import com.mr.domain.Contact;
import com.mr.domain.User;
import com.mr.service.CompanyService;
import com.mr.service.ContactService;
import com.mr.service.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class ContactController {

    @Autowired
    CompanyService companyService;
    @Autowired 
    private UserService userService;    
    @Autowired
    private ContactService contactService;
    
    private List<Contact> contactList;
    private Contact contact;
    private String companyIdString;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public String getCompanyIdString() {
        return companyIdString;
    }

    public void setCompanyIdString(String companyIdString) {
        this.companyIdString = companyIdString;
    }
    
    private Company getCompanyFromId(String companyIdString){
        return companyService.findById(Long.parseLong(companyIdString));
    }
        
    public String showAll(){
        setContactList(contactService.findAll());
        return "list_contacts";
    }
    
    public String showAllFor(User loggedInUser){
        if (userService.isAdmin(loggedInUser)) return showAll();
        else {
            setContactList(contactService.findAllFor(userService.getUsersCompany(loggedInUser)));
            return "list_contacts";
        }
    }

    public String createNew() {
        setCompanyIdString("0");
        setContact(new Contact());
        return "contact_form";
    }

    public String show(Contact contact) {
        setContact(contact);
        return "contact";
    }

    public String edit(Contact contact){
        setContact(contact);
        setCompanyIdString(contact.getCompany().getCompanyID().toString());
        return "contact_form";
    }
    
    public String save(){
        this.contact.setCompany(getCompanyFromId(companyIdString));
        contactService.save(contact);
        return showAll();
    }
    
    public String delete(Contact contact){
        contactService.delete(contact);
        return showAll();
    }

    @PostConstruct
    public void init() {
        contactList = contactService.findAll();
    }
}
