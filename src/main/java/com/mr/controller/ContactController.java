package com.mr.controller;

import com.mr.domain.Contact;
import com.mr.service.ContactService;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
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

    public String createNew() {
        return "";
    }

    public String show(Contact contact) {
        return "";
    }

    public String edit(Contact contact){
        return "";
    }
    
    public String delete(Contact contact){
        contactService.delete(contact);
        contactList = contactService.findAll();
        return "list_contacts";
    }

    @PostConstruct
    public void init() {
        contactList = contactService.findAll();
    }
}
