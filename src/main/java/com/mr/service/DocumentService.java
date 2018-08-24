package com.mr.service;

import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import java.util.List;

public interface DocumentService {
    
    List<Document> findAll();
    
    List<Document> findAllFor(DocumentType documentType);
    
    Document findById(Long id);
    
    Document save(Document document);
    
    void delete(Document document);
    
    Document loadDocument(Document document);
    
    List<DocumentTag> findTagsFor(Document document);
    
    Document addTag(String tagValue, Document document);
    
    Document removeTag(DocumentTag tag, Document document);
    
    List<DocumentDescriptor> findDescriptorsFor(Document document);
    
    Document addDescriptor(Document document, DocumentTypeDescriptor typeDescriptor, String descriptorValue);

    Document removeDescriptor(Document document, DocumentDescriptor descriptor);
}
