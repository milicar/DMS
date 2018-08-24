package com.mr.service.impl;

import com.mr.dao.ActivityDocTypeDAO;
import com.mr.dao.DocumentTypeDAO;
import com.mr.dao.DocumentTypeDescriptorDAO;
import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.service.ActivityDocTypeService;
import com.mr.service.DocumentTypeDescriptorService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    DocumentTypeDAO documentTypeDAO;
    @Autowired
    DocumentTypeDescriptorDAO documentTypeDescriptorDAO;
    @Autowired
    ActivityDocTypeDAO activityDocTypeDAO;
    @Autowired
    ActivityDocTypeService activityDocTypeService; 
    @Autowired
    DocumentTypeDescriptorService documentTypeDescriptorService;
    
    
    
    @Override
    public List<DocumentType> findAll() {
        return documentTypeDAO.findAll();
    }
    
    @Override
    public DocumentType findById(Long id) {
        return documentTypeDAO.getOne(id);
    }
    
    @Override
    public List<DocumentType> findAllFor(Activity activity) {
        List<ActivityDocumentType> adtList = activityDocTypeService.findAllFor(activity);
        List<DocumentType> docTypes = new ArrayList<>();
        adtList.forEach((adt) -> {
            docTypes.add(adt.getDocumentType());
        });
        return docTypes;
    }

    @Override
    public List<DocumentType> findAllFor(Activity activity, ActivityDocumentType.Direction direction) {
        List<ActivityDocumentType> adtList = activityDocTypeService.findAllFor(activity, direction);
        List<DocumentType> docTypes = new ArrayList<>();
        adtList.forEach((adt) -> {
            docTypes.add(adt.getDocumentType());
        });
        return docTypes;
    }
    
    @Override
    public DocumentType loadDocumentType(DocumentType documentType){
        DocumentType fullyLoaded = new DocumentType();
        fullyLoaded.setDocumentTypeDescriptors(documentTypeDescriptorService.findAllForDocType(documentType));
        return fullyLoaded;
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        return documentTypeDAO.save(documentType);
    }

    @Override
    public void delete(DocumentType documentType) {
        documentTypeDAO.deleteById(documentType.getDocumentTypeID());
    }

    @Override
    public DocumentType addDescriptor(DocumentTypeDescriptor descriptor, DocumentType documentType) {
        documentTypeDescriptorDAO.save(descriptor);
        return documentType;
    }

    @Override
    public DocumentType addDescriptor(String descriptor, DocumentType documentType) {
        DocumentTypeDescriptor newDescriptor = new DocumentTypeDescriptor();
        newDescriptor.setDescriptorName(descriptor);
        newDescriptor.setDocumentType(documentType);
        documentTypeDescriptorDAO.save(newDescriptor);
        return documentType;
    }
    
    @Override
    public DocumentType removeDescriptor(DocumentTypeDescriptor descriptor, DocumentType documentType) {
        documentTypeDescriptorDAO.delete(descriptor);
        return documentType;
    }
    
    
    
}
