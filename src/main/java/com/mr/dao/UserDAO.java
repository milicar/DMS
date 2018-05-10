
package com.mr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mr.domain.User;


public interface UserDAO extends JpaRepository<User, Long>{

}
