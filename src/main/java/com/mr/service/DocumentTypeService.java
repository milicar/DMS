package com.mr.service;

import com.mr.domain.Activity;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;

import java.util.List;

public interface DocumentTypeService {

    List<DocumentType> findAll();
    
    DocumentType findById(Long id);

    DocumentType save(DocumentType documentType);

    void delete(DocumentType documentType);
    
    DocumentType addDescriptor(DocumentTypeDescriptor descriptor, DocumentType documentType);
        
    DocumentType addDescriptor(String descriptor, DocumentType documentType); 
    
    DocumentType removeDescriptor(DocumentTypeDescriptor descriptor, DocumentType documentType);
    
    
}
