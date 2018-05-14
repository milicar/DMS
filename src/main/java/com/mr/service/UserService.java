package com.mr.service;

import com.mr.domain.Company;
import com.mr.domain.User;
import java.util.List;


public interface UserService {

    List<User> findAll();
    
    List<User> findAllFor(Company company);
    
    User findById(Long id);
    
    User save(User user);
    
    void delete(User user);
    
}