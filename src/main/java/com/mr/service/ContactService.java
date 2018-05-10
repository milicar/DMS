
package com.mr.service;

import com.mr.domain.Company;
import com.mr.domain.Contact;
import java.util.List;


public interface ContactService {

    List<Contact> findAll();
    
    List<Contact> findAllFor(Company company);
    
    Contact save(Contact contact);
    
    void delete(Contact contact);
}
