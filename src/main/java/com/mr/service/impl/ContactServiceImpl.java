package com.mr.service.impl;

import com.mr.dao.ContactDAO;
import com.mr.domain.Company;
import com.mr.domain.Contact;
import com.mr.service.ContactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactDAO contactDAO;

    @Override
    public List<Contact> findAll() {
        return contactDAO.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return contactDAO.getOne(id);
    }
    
    @Override
    public List<Contact> findAllFor(Company company){
        Contact contact = new Contact();
        contact.setCompany(company);
        
        return contactDAO.findAll(Example.of(contact));
    }

    @Override
    public Contact save(Contact contact) {
        return contactDAO.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        contactDAO.delete(contact);
    }

}
