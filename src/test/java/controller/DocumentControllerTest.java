package controller;

import com.mr.controller.DocumentController;
import com.mr.controller.LoginController;
import com.mr.domain.Document;
import com.mr.domain.DocumentDescriptor;
import com.mr.domain.DocumentTag;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.domain.Role;
import com.mr.domain.User;
import com.mr.service.DocumentService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    private List<Document> documentList;
    private Document document;
    private DocumentType documentType;
    private DocumentTag tag;
    private List<DocumentTag> tagList;
    private List<DocumentDescriptor> descriptorList;

    @Mock
    DocumentService documentService;
    @Mock
    LoginController loginController;
    @InjectMocks
    @Spy
    DocumentController documentController;

    @Test
    public void shouldCreateNewDocumentOfADocumentType() {
        documentType = new DocumentType();
        DocumentTypeDescriptor dtDescriptor = new DocumentTypeDescriptor();
        List<DocumentTypeDescriptor> dtDescriptorList = new ArrayList<>();
        dtDescriptorList.add(dtDescriptor);
        documentType.setDocumentTypeDescriptors(dtDescriptorList);

        documentController.createNewFor(documentType);

        assertNotNull(documentController.getDocument());
        assertEquals(documentType, documentController.getDocumentType());
        assertNotNull(documentController.getTagList());
        assertEquals(dtDescriptorList.size(), documentController.getDescriptorList().size());
        assertEquals(dtDescriptorList.get(0),
                documentController.getDescriptorList().get(0).getDocumentTypeDescriptor());
        assertEquals("document_form", documentController.createNewFor(documentType));
    }

    @Test
    public void shouldShowAllDocumentsOfADocumentType() {
        when(documentService.findAllFor(documentType)).thenReturn(documentList);

        documentController.showAllFor(documentType);

        assertEquals(documentType, documentController.getDocumentType());
        verify(documentService).findAllFor(documentType);
        assertEquals(documentList, documentController.getDocumentList());
        assertEquals("list_documents", documentController.showAllFor(documentType));
    }

    @Test
    public void shouldShowDocument() {
        when(documentService.findTagsFor(document)).thenReturn(tagList);
        when(documentService.findDescriptorsFor(document)).thenReturn(descriptorList);

        documentController.show(document);

        assertEquals(document, documentController.getDocument());
        assertEquals(tagList, documentController.getTagList());
        assertEquals(descriptorList, documentController.getDescriptorList());
        assertEquals("document", documentController.show(document));
    }

    @Test
    public void shouldShowAllDocuments() {
        when(documentService.findAll()).thenReturn(documentList);

        documentController.showAll();

        assertEquals(documentList, documentController.getDocumentList());
        assertEquals("list_documents", documentController.showAll());
    }

    @Test
    public void shouldShowAllDocumentsToAdmin() {
        User admin = new User();
        admin.setUserRole(Role.ADMIN);

        documentController.showAllFor(admin);

        verify(documentController).showAll();
    }

    @Test
    public void shouldShowOnlyCompanyDocumentsToEmployees() {
        User user = new User();
        user.setUserRole(Role.USER);
        when(loginController.getDocumentsForUser()).thenReturn(documentList);

        documentController.showAllFor(user);

        verify(loginController).getDocumentsForUser();
        assertEquals(documentList, documentController.getDocumentList());
        assertEquals("list_documents", documentController.showAllFor(user));
    }

    @Test
    public void shouldDeleteADocument() {
        documentController.setDocumentType(documentType);

        documentController.delete(document);

        verify(documentService).delete(document);
        verify(documentService).findAllFor(documentType);
        assertEquals("list_documents", documentController.delete(document));
    }
    
    @Test
    public void shouldDeleteThis() {
        documentController.setDocument(document);
        
        documentController.deleteThis();
        
        verify(documentController).delete(document);
    }
    
    @Test
    public void shouldEditADocument() {
        when(documentService.findDescriptorsFor(document)).thenReturn(descriptorList);
        when(documentService.findTagsFor(document)).thenReturn(tagList);
        
        documentController.edit(document);
    
        verify(documentService).findDescriptorsFor(document);
        verify(documentService).findTagsFor(document);
        assertEquals(document, documentController.getDocument());
        assertEquals(descriptorList, documentController.getDescriptorList());
        assertEquals(tagList, documentController.getTagList());
        assertEquals("document_form", documentController.edit(document));
    }
    
    @Test
    public void shouldSaveADocument() {
        documentController.setDocument(document);
        documentController.setDocumentType(documentType);
        
        documentController.save();
        
        verify(documentService).save(document);
        verify(documentService).save(any());
        verify(documentController).showAllFor(documentType);
    }
    
    @Test
    public void shouldRemoveATagFromDocument() {
        documentController.setDocument(document);
        when(documentService.findTagsFor(document)).thenReturn(tagList);
        
        documentController.removeTag(tag);
        
        verify(documentService).removeTag(tag, document);
        verify(documentService).findTagsFor(document);
        assertEquals(tagList, documentController.getTagList());
    }
    
    @Test
    public void shouldAddATagToADocument() {
        documentController.setDocument(document);
        String tagValue = "tag";
        tag = new DocumentTag();
        tag.setTagValue(tagValue);
        tag.setDocument(document);
        tagList = new ArrayList<>();
        tagList.add(tag);
        when(documentService.findTagsFor(document)).thenReturn(tagList);
        assertTrue(documentController.getTagList().isEmpty());
        
        documentController.addTag(tagValue);
   
        verify(documentService).addTag(tagValue, document);
        verify(documentService).findTagsFor(document);
        assertEquals(tagList, documentController.getTagList());
        assertEquals(1, documentController.getTagList().size());
        assertEquals("document_form", documentController.addTag(tagValue));
    }
    
}
