
package com.mr.dao;

import com.mr.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactDAO extends JpaRepository<Contact, Long>{

}
