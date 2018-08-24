package com.mr.service;

import com.mr.domain.Company;
import com.mr.domain.User;
import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> findAll();
    
    List<User> findAllFor(Company company);
    
    User findById(Long id);
    
    User save(User user);
    
    void delete(User user);
    
    Optional<User> findByUsername(String username);
    
}
