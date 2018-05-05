package com.mr.dao;

import com.mr.domain.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeDAO extends JpaRepository<DocumentType, Long> {
}
