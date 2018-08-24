package com.mr.controller;

import com.mr.domain.Contact;
import com.mr.service.ContactService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;
    
    private List<Contact> contactList;
    private Contact contact;

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
    
    public String showAll(){
        setContactList(contactService.findAll());
        return "list_contacts";
    }

    public String createNew() {
        setContact(new Contact());
        return "contact_form";
    }

    public String show(Contact contact) {
        setContact(contact);
        return "contact";
    }

    public String edit(Contact contact){
        setContact(contact);
        return "contact_form";
    }
    
    public String save(){
        contactService.save(contact);
        setContactList(contactService.findAll());
        return "list_contacts";
    }
    
    public String delete(Contact contact){
        contactService.delete(contact);
        setContactList(contactService.findAll());
        return "list_contacts";
    }

    @PostConstruct
    public void init() {
        contactList = contactService.findAll();
    }
}
