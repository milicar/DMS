
package com.mr.service;

import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import java.util.List;


public interface DocumentDescriptorService {

    List<DocumentDescriptor> findAll();
    
    List<DocumentDescriptor> findAllFor(Document document);
    
    DocumentDescriptor findById(Long id);
    
    DocumentDescriptor save(DocumentDescriptor descriptor);
    
    void delete(DocumentDescriptor descriptor);
}
