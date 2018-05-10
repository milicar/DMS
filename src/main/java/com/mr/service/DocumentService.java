package com.mr.service;

import com.mr.domain.Document;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import java.util.List;

public interface DocumentService {
    
    List<Document> findAll();
    
    List<Document> findForDocType(DocumentType documentType);
    
    Document findById(Long id);
    
    Document save(Document document);
    
    void delete(Document document);
    
    List<DocumentTag> listTagsFor(Document document);
}
