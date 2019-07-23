package controller;

import com.mr.controller.ActivityController;
import com.mr.controller.LoginController;
import com.mr.domain.Activity;
import com.mr.domain.ActivityDocumentType;
import com.mr.domain.DocumentType;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.Role;
import com.mr.domain.Subprocess;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.DocumentTypeService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {
    
    private List<Activity> activityList;
    private Activity activity;
    private List<DocumentType> inDocumentTypeList;
    private List<DocumentType> outDocumentTypeList;
    private Process parent;
    
    @Mock
    ActivityService activityService;
    @Mock
    DocumentTypeService documentTypeService;
    @Mock
    LoginController loginController;
    @InjectMocks @Spy
    ActivityController activityController;
    
    @Test
    public void shouldShowAllActivities() {
        activityList = new ArrayList<>();
        activityList.add(new Activity());
        when(activityService.findAll()).thenReturn(activityList);
        
        activityController.showAll();
        
        verify(activityService).findAll();
        assertEquals(activityList, activityController.getActivityList());
        assertEquals("list_activities", activityController.showAll());
    }
   
    @Test
    public void shouldShowAllActivitiesForAParentSubprocess() {
        parent = new Subprocess();
        activityList = new ArrayList<>();
        activityList.add(new Activity());
        when(activityService.findAllFor(parent)).thenReturn(activityList);
        
        activityController.showAllFor(parent);
        
        verify(activityService).findAllFor(parent);
        assertEquals(parent, activityController.getParent());
        assertEquals(activityList, activityController.getActivityList());
        assertEquals("list_activities", activityController.showAllFor(parent));
    }
   
    @Test
    public void shouldShowAllActivitiesForAParentFlProcess() {
        parent = new FirstLevelProcess();
        activityList = new ArrayList<>();
        activityList.add(new Activity());
        when(activityService.findAllFor(parent)).thenReturn(activityList);
        
        activityController.showAllFor(parent);
        
        verify(activityService).findAllFor(parent);
        assertEquals(parent, activityController.getParent());
        assertEquals(activityList, activityController.getActivityList());
        assertEquals("list_activities", activityController.showAllFor(parent));
    }
    
    @Test
    public void shouldShowAllActivitiesForAdmin() {
        User admin = new User();
        admin.setUserRole(Role.ADMIN);
        
        activityController.showAllFor(admin);
        
        verify(activityController).showAll();
    }
    
    @Test
    public void shouldShowActivitiesDefinedForEmployeesCompany() {
        User user = new User();
        user.setUserRole(Role.USER);
        activityList = new ArrayList<>();
        activityList.add(new Activity());
        when(loginController.getActivitiesForUser()).thenReturn(activityList);
        
        activityController.showAllFor(user);
        
        verify(loginController).getActivitiesForUser();
        assertEquals(activityList, activityController.getActivityList());
        assertEquals("list_activities", activityController.showAllFor(user));
    }
    
    @Test
    public void shouldShowOneActivity() {       
        activity = new Activity();
        parent = new Subprocess();
        activity.setParent(parent);
        inDocumentTypeList = new ArrayList<>();
        inDocumentTypeList.add(new DocumentType());
        outDocumentTypeList = new ArrayList<>();
        outDocumentTypeList.add(new DocumentType());
        when(documentTypeService.findAllFor(activity, ActivityDocumentType.Direction.IN)).thenReturn(inDocumentTypeList);
        when(documentTypeService.findAllFor(activity, ActivityDocumentType.Direction.OUT)).thenReturn(outDocumentTypeList);
                
        activityController.show(activity);
        
        verify(documentTypeService).findAllFor(activity, ActivityDocumentType.Direction.IN);
        verify(documentTypeService).findAllFor(activity, ActivityDocumentType.Direction.OUT);
        assertEquals(activity, activityController.getActivity());
        assertEquals(parent, activityController.getParent());
        assertEquals(inDocumentTypeList, activityController.getInDocumentTypeList());
        assertEquals(outDocumentTypeList, activityController.getOutDocumentTypeList());
        assertEquals("activity", activityController.show(activity));
    }
    
    @Test
    public void shouldSaveActivity() {
        activity = new Activity();
        parent = new Subprocess();
        activityController.setActivity(activity);
        activityController.setParent(parent);
        
        activityController.save();
        
        verify(activityService).save(activity);
        verify(activityController).showAllFor(parent);
    }
    
    @Test
    public void shouldEditActivity() {
        activity = new Activity();

        activityController.edit(activity);
        
        assertEquals(activity, activityController.getActivity());
        assertEquals("activity_form", activityController.edit(activity));
    }
    
    @Test
    public void shouldDeleteActivity() {
        activity = new Activity();
        parent= new Subprocess();
        activityController.setParent(parent);
        
        activityController.delete(activity);
        
        verify(activityService).delete(activity);
        verify(activityController).showAllFor(parent);
    }
    
    @Test
    public void shouldCreateNewActivityForFlProcess() {
        parent = new FirstLevelProcess();
                
        activityController.createNewFor(parent);
        
        assertEquals(parent, activityController.getParent());
        assertNotNull(activityController.getActivity());
    }
    
    @Test
    public void shouldCreateNewActivityForSubprocess() {
        parent = new Subprocess();
        
        activityController.createNewFor(parent);

        assertEquals(parent, activityController.getParent());
        assertNotNull(activityController.getActivity());
    }
    
}
