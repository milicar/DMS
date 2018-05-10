package com.mr.service.impl;

import com.mr.dao.DocumentDAO;
import com.mr.domain.Document;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.service.DocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDAO documentDao;
    
    @Override
    public List<Document> findAll() {
        return documentDao.findAll();
    }

    @Override
    public List<Document> findForDocType(DocumentType documentType) {
        Document document = new Document();
        document.setDocumentType(documentType);
        Example<Document> example = Example.of(document);
        return documentDao.findAll(example);
    }
   
    @Override
    public Document findById(Long id) {
        return documentDao.getOne(id);
    }

    @Override
    public Document save(Document document) {
        return documentDao.save(document);
    }

    @Override
    public void delete(Document document) {
        documentDao.delete(document);
    }

    @Override
    public List<DocumentTag> listTagsFor(Document document) {
        return documentDao.getOne(document.getDocumentID()).getTagList();
    }
    
    

}
