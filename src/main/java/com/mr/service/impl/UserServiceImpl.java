package com.mr.service.impl;

import com.mr.dao.UserDAO;
import com.mr.domain.Company;
import com.mr.domain.User;
import com.mr.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findAllFor(Company company) {
        User user = new User();
        user.setCompany(company);
        return userDao.findAll(Example.of(user));
    }

    @Override
    public User findById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
    
    @Override
    public Optional<User> findByUsername(String username){
        User user = new User();
        user.setUsername(username);
        return userDao.findOne(Example.of(user));
    }

}
