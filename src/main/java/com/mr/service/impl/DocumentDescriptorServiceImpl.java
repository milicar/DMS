package com.mr.service.impl;

import com.mr.dao.DocumentDescriptorDAO;
import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.service.DocumentDescriptorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class DocumentDescriptorServiceImpl implements DocumentDescriptorService {

    @Autowired
    private DocumentDescriptorDAO descriptorDAO;

    @Override
    public List<DocumentDescriptor> findAll() {
        return descriptorDAO.findAll();
    }

    @Override
    public List<DocumentDescriptor> findAllFor(Document document) {
        DocumentDescriptor dd = new DocumentDescriptor();
        dd.setDocument(document);
        return descriptorDAO.findAll(Example.of(dd));
    }
    
    @Override
    public DocumentDescriptor findById(Long id) {
        return descriptorDAO.getOne(id);
    }

    @Override
    public DocumentDescriptor save(DocumentDescriptor descriptor) {
        return descriptorDAO.save(descriptor);
    }

    @Override
    public void delete(DocumentDescriptor descriptor) {
        descriptorDAO.delete(descriptor);
    }
    
    public DocumentDescriptor update(DocumentDescriptor descriptor, String newValue){
        descriptor.setDescriptorValue(newValue);
        return descriptor;
    }

}
