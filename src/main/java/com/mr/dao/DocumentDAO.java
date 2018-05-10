package com.mr.dao;

import com.mr.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentDAO extends JpaRepository<Document, Long>{
}
