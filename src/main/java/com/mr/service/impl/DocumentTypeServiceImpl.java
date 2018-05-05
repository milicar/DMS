package com.mr.service.impl;

import com.mr.dao.DocumentTypeDAO;
import com.mr.domain.DocumentType;
import com.mr.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    DocumentTypeDAO documentTypeDAO;

    @Override
    public List<DocumentType> findAll() {
        return documentTypeDAO.findAll();
    }

    @Override
    public DocumentType findById(Long id) {
        return documentTypeDAO.getOne(id);
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        return documentTypeDAO.save(documentType);
    }

    @Override
    public void delete(Long id) {
        documentTypeDAO.deleteById(id);
    }
}
