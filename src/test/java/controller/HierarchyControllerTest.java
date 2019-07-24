package controller;

import com.mr.controller.CompanyController;
import com.mr.controller.HierarchyController;
import com.mr.domain.Activity;
import com.mr.domain.Company;
import com.mr.domain.DocumentType;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.Subprocess;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import com.mr.service.ProcessService;
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HierarchyControllerTest {

    @Mock 
    UserService userService;
    @Mock
    CompanyController companyController;
    @Mock
    ProcessService processService;
    @Mock
    ActivityService activityService;
    @Mock
    DocumentTypeService documentTypeService;
    @Mock
    DocumentService documentService;
    @InjectMocks
    @Spy
    HierarchyController hierarchyController;

    final private User loggedInUser = new User();

    @Test
    public void shouldShowAllCompaniesForAdmin() {
        doReturn(Boolean.TRUE).when(userService).isAdmin(any());
        when(companyController.showAll()).thenReturn("all companies");

        assertEquals("all companies", hierarchyController.start(loggedInUser));
    }

    @Test
    public void shouldShowOnlyUsersCompany() {
        Company usersCompany = new Company();
        loggedInUser.setCompany(usersCompany);
        doReturn(Boolean.FALSE).when(userService).isAdmin(loggedInUser);

        when(companyController.show(usersCompany)).thenReturn("user's company");
        when(userService.getUsersCompany(loggedInUser)).thenReturn(usersCompany);
      
        assertEquals("user's company", hierarchyController.start(loggedInUser));

    }

    @Test
    public void shouldReturnFirstLevelProcessesForLoggedInUser() {
        Company company = new Company();
        loggedInUser.setCompany(company);
        when(userService.getUsersCompany(loggedInUser)).thenReturn(company);

        hierarchyController.buildListOfFirstLevelProcessesFor(loggedInUser);
        verify(processService).findAllFor(company);
    }

    @Test
    public void shouldReturnSubprocessesForLoggedInUser() {
        FirstLevelProcess parent = new FirstLevelProcess();
        List<Process> processList = new ArrayList<>();
        processList.add(parent);
        doReturn(processList).when(hierarchyController).buildListOfFirstLevelProcessesFor(loggedInUser);
        
        hierarchyController.buildListOfSubprocessesFor(loggedInUser);
        verify(processService).findAllFor(parent);

    }

    @Test
    public void shouldReturnActivitiesForLoggedInUser() {
        FirstLevelProcess process = new FirstLevelProcess();
        List<Process> processList = new ArrayList<>();
        processList.add(process);
        Subprocess subprocess = new Subprocess();
        List<Process> subprocessList = new ArrayList<>();
        subprocessList.add(subprocess);

        doReturn(processList).when(hierarchyController).buildListOfFirstLevelProcessesFor(loggedInUser);
        doReturn(subprocessList).when(hierarchyController).buildListOfSubprocessesFor(loggedInUser);
       
        hierarchyController.buildListOfActivitiesFor(loggedInUser);
        verify(activityService).findAllFor(process);
        verify(activityService).findAllFor(subprocess);
    }

    @Test
    public void shouldReturnDocumentTypesForLoggedInUser() {
        Activity activity = new Activity();
        Activity activity2 = new Activity();
        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity);
        activityList.add(activity2);

        doReturn(activityList).when(hierarchyController).buildListOfActivitiesFor(loggedInUser);
        
        hierarchyController.buildListOfDocumentTypesFor(loggedInUser);
        verify(documentTypeService, times(2)).findAllFor(any());
    }

    @Test
    public void shouldReturnDocumentsForLoggedInUser() {
        DocumentType documentType = new DocumentType();
        List<DocumentType> docTypeList = new ArrayList<>();
        docTypeList.add(documentType);
        docTypeList.add(documentType);

        doReturn(docTypeList).when(hierarchyController).buildListOfDocumentTypesFor(loggedInUser);
       
        hierarchyController.getDocumentsForUser(loggedInUser);
        verify(documentService, times(2)).findAllFor(documentType);
    }

}
