package com.mr.service.impl;

import com.mr.dao.DocumentDAO;
import com.mr.dao.TagDAO;
import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.service.DocumentDescriptorService;
import com.mr.service.DocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDAO documentDao;
    @Autowired
    private TagDAO tagDao;
    @Autowired
    private DocumentDescriptorService documentDescriptorService;

    private List<DocumentDescriptor> descriptors;

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
    public Document loadDocument(Document document) {
        Document fullyLoaded = document;
        fullyLoaded.setTagList(findTagsFor(document));
        this.descriptors = findDescriptorsFor(document);
        fullyLoaded.setDocumentDescriptorList(descriptors);
        return fullyLoaded;
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
    public List<DocumentTag> findTagsFor(Document document) {
        DocumentTag tag = new DocumentTag();
        tag.setDocument(document);
        return tagDao.findAll(Example.of(tag));
    }

    @Override
    public Document addTag(String tagValue, Document document) {
        DocumentTag newTag = new DocumentTag();
        newTag.setDocument(document);
        newTag.setTagValue(tagValue);
        tagDao.save(newTag);
        document.setTagList(findTagsFor(document));
        return document;
    }

    @Override
    public Document removeTag(DocumentTag tag, Document document) {
        tagDao.delete(tag);
        document.setTagList(findTagsFor(document));
        return document;
    }

    @Override
    public List<DocumentDescriptor> findDescriptorsFor(Document document) {
        DocumentDescriptor dd = new DocumentDescriptor();
        dd.setDocument(document);
        return documentDescriptorService.findAllForDocument(document);
    }

    @Override
    public Document addDescriptor(Document document, DocumentTypeDescriptor typeDescriptor, String descriptorValue) {
        DocumentDescriptor newDescriptor = new DocumentDescriptor();
        if (document.getDocumentType() == typeDescriptor.getDocumentType()) {
            newDescriptor.setDescriptorValue(descriptorValue);
            newDescriptor.setDocument(document);
            newDescriptor.setDocumentTypeDescriptor(typeDescriptor);
            documentDescriptorService.save(newDescriptor);
        }
        document.setDocumentDescriptorList(findDescriptorsFor(document));
        return document;
    }

    @Override
    public Document removeDescriptor(Document document, DocumentDescriptor descriptor) {
        documentDescriptorService.delete(descriptor);
        document.setDocumentDescriptorList(findDescriptorsFor(document));
        return document;
    }

}
