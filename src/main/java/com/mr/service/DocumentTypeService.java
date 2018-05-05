package com.mr.service;

import com.mr.domain.DocumentType;

import java.util.List;

public interface DocumentTypeService {

    List<DocumentType> findAll();

    DocumentType findById(Long id);

    DocumentType save(DocumentType documentType);

    void delete(Long id);
}
