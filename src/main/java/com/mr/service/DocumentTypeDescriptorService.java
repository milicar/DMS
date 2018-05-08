package com.mr.service;

import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import java.util.List;


public interface DocumentTypeDescriptorService {
    
    List<DocumentTypeDescriptor> findAllForDocType(DocumentType documentType);
    
    DocumentTypeDescriptor save(DocumentTypeDescriptor descriptor);
    
    List<DocumentTypeDescriptor> save(List<DocumentTypeDescriptor> descriptorList);
    
    List<DocumentTypeDescriptor> update(List<DocumentTypeDescriptor> descriptorList, DocumentType documentType);
    
    void delete(DocumentTypeDescriptor descriptor);
    
}
