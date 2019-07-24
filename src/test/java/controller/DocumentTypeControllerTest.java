package controller;

import com.mr.controller.DocumentTypeController;
import com.mr.controller.LoginController;
import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.domain.DocumentTypeDescriptor;
import com.mr.domain.Role;
import com.mr.domain.User;
import com.mr.service.ActivityDocTypeService;
import com.mr.service.DocumentTypeDescriptorService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentTypeControllerTest {

    private DocumentType documentType;
    private List<DocumentType> documentTypeList;
    private DocumentTypeDescriptor descriptor;
    private List<DocumentTypeDescriptor> descriptorList;
    private Activity activity;
    private ActivityDocumentType.Direction direction;
    
    @Mock
    DocumentTypeService documentTypeService;
    @Mock
    DocumentTypeDescriptorService dtdescriptorService;
    @Mock
    ActivityDocTypeService activityDocTypeService;
    @Mock
    LoginController loginController;
    @InjectMocks
    @Spy
    DocumentTypeController documentTypeController;

    @Test
    public void shouldCreateNewDocumentType() {
        documentTypeController.createNew();

        assertNotNull(documentTypeController.getDocumentType());
        assertNotNull(documentTypeController.getDescriptorList());
        assertEquals("document_type_form", documentTypeController.createNew());
    }

    @Test
    public void shouldCreateNewDTForActivityAndDirectionString() {
        activity = new Activity();
        String directionString = "IN";

        documentTypeController.createNewFor(activity, directionString);

        assertEquals(activity, documentTypeController.getActivity());
        assertEquals(directionString, documentTypeController.getDirection().toString());
        assertNotNull(documentTypeController.getDocumentType());
        assertNotNull(documentTypeController.getDescriptorList());
        assertEquals("document_type_form", documentTypeController.createNewFor(activity, directionString));
    }

    @Test
    public void shouldCreateNewDTForThis() {
        activity = new Activity();
        documentTypeController.setActivity(activity);
        direction = ActivityDocumentType.Direction.OUT;
        documentTypeController.setDirection(direction);

        documentTypeController.createNewForThis();

        verify(documentTypeController).createNewFor(activity, "OUT");
    }

    @Test
    public void shouldShowDocumentType() {
        documentType = new DocumentType();
        descriptorList = new ArrayList<>();
        descriptorList.add(new DocumentTypeDescriptor());
        when(documentTypeController.findAllForDocType(documentType)).thenReturn(descriptorList);

        documentTypeController.show(documentType);

        assertEquals(documentType, documentTypeController.getDocumentType());
        assertEquals(descriptorList, documentTypeController.getDescriptorList());
        assertEquals("document_type", documentTypeController.show(documentType));
    }
    
    @Test
    public void shouldShowAllDocumentTypesForActivityAndDirectionString() {
        activity = new Activity();
        direction = ActivityDocumentType.Direction.IN;
        String directionString = direction.toString();
        when(documentTypeService.findAllFor(activity, direction)).thenReturn(documentTypeList);
        
        documentTypeController.showAllFor(activity, directionString);
        
        assertEquals(activity, documentTypeController.getActivity());
        assertEquals(direction, documentTypeController.getDirection());
        assertEquals(documentTypeList, documentTypeController.getDocumentTypeList());
        assertEquals("list_document_types", documentTypeController.showAllFor(activity, directionString));
    }
    
    @Test
    public void shouldSaveDocumentType() {
        direction = ActivityDocumentType.Direction.IN;
        String directionString = direction.toString();
        documentTypeController.setDocumentType(documentType);
        documentTypeController.setActivity(activity);
        documentTypeController.setDirection(direction);
        documentTypeController.setDescriptorList(descriptorList);
     
        documentTypeController.save();
        
        verify(documentTypeService).save(documentType);
        verify(activityDocTypeService).save(activity, documentType, direction);
        verify(dtdescriptorService).update(descriptorList, documentType);
        verify(documentTypeController).showAllFor(activity, directionString);
    }
    
    @Test
    public void shouldDeleteDocumentType() {
        direction = ActivityDocumentType.Direction.OUT;
        String directionString = direction.toString();
        documentTypeController.setActivity(activity);
        documentTypeController.setDirection(direction);
        
        documentTypeController.delete(documentType);
        
        verify(documentTypeService).delete(documentType);
        verify(documentTypeController).showAllFor(activity, directionString);
    }
    
    @Test
    public void shouldEditDocumentType() {
        documentType = new DocumentType();
        descriptorList = new ArrayList<>();
        descriptorList.add(descriptor);
        documentType.setDocumentTypeDescriptors(descriptorList);
        
        documentTypeController.edit(documentType);
        
        assertEquals(documentType, documentTypeController.getDocumentType());
        assertEquals(descriptorList, documentTypeController.getDescriptorList());
        assertEquals("document_type_form", documentTypeController.edit(documentType));
    }
    
    @Test
    public void shouldShowAllDocumentTypes() {
        when(documentTypeService.findAll()).thenReturn(documentTypeList);
        
        documentTypeController.showAll();
        
        verify(documentTypeService).findAll();
        assertEquals(documentTypeList, documentTypeController.getDocumentTypeList());
        assertEquals("list_document_types", documentTypeController.showAll());
    }
    
    @Test
    public void shouldShowAllDocumentTypesToAdmin() {
        User loggedInUser = new User();
        loggedInUser.setUserRole(Role.ADMIN);
        
        documentTypeController.showAllFor(loggedInUser);
        
        verify(documentTypeController).showAll();
    }
    
    @Test
    public void shouldShowDocumentTypesDefinedWithinEmployeesCompany() {
        User loggedInUser = new User();
        loggedInUser.setUserRole(Role.USER);
        when(loginController.getDocTypesForUser()).thenReturn(documentTypeList);
        
        documentTypeController.showAllFor(loggedInUser);
        
        verify(loginController).getDocTypesForUser();
        assertEquals(documentTypeList, documentTypeController.getDocumentTypeList());
        assertEquals("list_document_types", documentTypeController.showAllFor(loggedInUser));
    }
    
    @Test
    public void shouldAddDescriptorToDocumentType() {
        String descriptorName = "somename";
        descriptorList = new ArrayList<>();
        documentTypeController.setDocumentType(documentType);
        documentTypeController.setDescriptorList(descriptorList);
        assertTrue(documentTypeController.getDescriptorList().isEmpty());
        
        documentTypeController.addDescriptor(descriptorName);
        
        assertEquals(1, documentTypeController.getDescriptorList().size());
        assertEquals(descriptorName, documentTypeController.getDescriptorList().get(0).getDescriptorName());
        assertNull(documentTypeController.getDescriptor().getDescriptorName());
        assertEquals("document_type_form", documentTypeController.addDescriptor(descriptorName));
    }
    
    @Test
    public void shouldDeleteDescriptorOfADocumentType() {
        descriptorList = new ArrayList<>();
        descriptor = new DocumentTypeDescriptor();
        descriptorList.add(descriptor);
        documentTypeController.setDescriptorList(descriptorList);
        assertEquals(1, documentTypeController.getDescriptorList().size()); 
        
        documentTypeController.deleteDescriptor(descriptor);
        
        assertTrue(documentTypeController.getDescriptorList().isEmpty());
        assertEquals("document_type_form", documentTypeController.deleteDescriptor(descriptor));
    }
}
