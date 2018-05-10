
package com.mr.dao;

import com.mr.domain.DocumentTag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagDAO extends JpaRepository<DocumentTag, Long>{

}
