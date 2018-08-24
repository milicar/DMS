package com.mr.service.impl;

import com.mr.dao.DocumentTypeDescriptorDAO;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import com.mr.service.DocumentTypeDescriptorService;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeDescriptorServiceImpl implements DocumentTypeDescriptorService {

    @Autowired
    DocumentTypeDescriptorDAO documentTypeDescriptorDAO;

    @Override
    public List<DocumentTypeDescriptor> findAllForDocType(DocumentType documentType) {
        DocumentTypeDescriptor descriptor = new DocumentTypeDescriptor();
        descriptor.setDocumentType(documentType);
        Example<DocumentTypeDescriptor> example = Example.of(descriptor);
        return documentTypeDescriptorDAO.findAll(example);
    }

    @Override
    public DocumentTypeDescriptor save(DocumentTypeDescriptor descriptor) {
        return documentTypeDescriptorDAO.save(descriptor);
    }

    @Override
    public List<DocumentTypeDescriptor> save(List<DocumentTypeDescriptor> descriptorList) {
        return documentTypeDescriptorDAO.saveAll(descriptorList);
    }

    @Override
    public List<DocumentTypeDescriptor> update(List<DocumentTypeDescriptor> newDescriptorList, DocumentType documentType) {
//        List<DocumentTypeDescriptor> oldDescriptors = findAllForDocType(documentType);
//        oldDescriptors.stream()
//                .filter((oldDescriptor) -> (!newDescriptorList.contains(oldDescriptor)))
//                .forEach((oldDescriptor) -> {
//                    documentTypeDescriptorDAO.delete(oldDescriptor);
//                });
        documentTypeDescriptorDAO.deleteAll(findAllForDocType(documentType));
        return documentTypeDescriptorDAO.saveAll(newDescriptorList);
    }

    @Override
    public void delete(DocumentTypeDescriptor descriptor) {
        documentTypeDescriptorDAO.deleteById(descriptor.getDescriptorID());
    }
    
    
    
    

}
