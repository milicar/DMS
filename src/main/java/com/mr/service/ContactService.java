
package com.mr.service;

import com.mr.domain.Contact;
import java.util.List;


public interface ContactService {

    List<Contact> findAll();
    
    Contact save(Contact contact);
    
    void delete(Contact contact);
}
