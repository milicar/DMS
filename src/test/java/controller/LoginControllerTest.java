package controller;

import com.mr.controller.CompanyController;
import com.mr.domain.User;
import com.mr.controller.LoginController;
import com.mr.domain.Activity;
import com.mr.domain.Company;
import com.mr.domain.DocumentType;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.Role;
import com.mr.domain.Subprocess;
import com.mr.service.ActivityService;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import com.mr.service.ProcessService;
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.faces.context.FacesContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private User loggedInUser;
    private User registeredUser;
    private String enteredUsername;
    private String enteredPassword;
    @Mock
    private UserService userService;
    @Mock
    private FacesContext facesContext;
    @Mock private CompanyController companyController;
    @Mock private ProcessService processService;
    @Mock private ActivityService activityService;
    @Mock DocumentTypeService docTypeService;
    @Mock DocumentService documentService;
    @InjectMocks @Spy
    TestableLoginController loginController = new TestableLoginController();

    @Test
    public void should_not_let_unregistered_user_login() {
        enteredUsername = "anyUsername";

        loginController.setUsername(enteredUsername);
        when(userService.findByUsername(anyString())).thenReturn(Optional.empty());
        assertEquals("", loginController.login());
        verify(userService).findByUsername(enteredUsername);
    }

    @Test
    public void should_not_let_user_login_with_wrong_password() {
        enteredUsername = "someUsername";
        enteredPassword = "somePassword";
        loginController.setPassword(enteredPassword);
        loginController.setUsername(enteredUsername);

        registeredUser = new User();
        registeredUser.setUsername(enteredUsername);
        registeredUser.setPassword("differentPassword");

        when(userService.findByUsername(enteredUsername)).thenReturn(Optional.of(registeredUser));

        assertEquals("", loginController.login());
    }

    @Test
    public void should_login_user_with_correct_credentials() {
        enteredUsername = "someUsername";
        enteredPassword = "somePassword";
        loginController.setUsername(enteredUsername);
        loginController.setPassword(enteredPassword);
        loginController.setLoggedInUser(loggedInUser);

        registeredUser = new User();
        registeredUser.setUsername(enteredUsername);
        registeredUser.setPassword(enteredPassword);

        when(userService.findByUsername(enteredUsername)).thenReturn(Optional.of(registeredUser));
        doReturn("started").when(loginController).start();
        loginController.login();
        assertEquals("started", loginController.login());
    }

    @Test
    public void should_check_if_user_is_administrator() {
        Role admin = Role.ADMIN;
        when(loggedInUser.getUserRole()).thenReturn(admin);

        assertTrue(loginController.isAdmin());
    }

    @Test
    public void should_show_all_companies_for_admin() {
        doReturn(Boolean.TRUE).when(loginController).isAdmin();
        when(companyController.showAll()).thenReturn("all companies");
        
        assertEquals("all companies", loginController.start());
    }
    
    @Test
    public void should_show_only_users_company() {
        Company usersCompany = new Company();
        when(loggedInUser.getCompany()).thenReturn(usersCompany);
        doReturn(Boolean.FALSE).when(loginController).isAdmin();
        
        when(companyController.show(usersCompany)).thenReturn("user's company");
        assertEquals("user's company", loginController.start());
        
    }
    
    @Test
    public void should_logout_a_user() {
        assertNotNull(loggedInUser);
        assertEquals("index", loginController.logout());
        verify(loginController).setLoggedInUser(null);
        
        
    }
    
    @Test
    public void should_return_first_level_processes_for_logged_in_user() {
        Company company = new Company();
        when(loggedInUser.getCompany()).thenReturn(company);
        
        loginController.getFlProcessesForUser();
        verify(processService).findAllFor(company);
    }
    
    @Test
    public void should_return_subprocesses_for_logged_in_user() {
        Company company = new Company();
        FirstLevelProcess parent = new FirstLevelProcess();
        List<Process> processList = new ArrayList<>();
        processList.add(parent);
        doReturn(processList).when(loginController).getFlProcessesForUser();
        loginController.getSubprocessesForUser();
        verify(processService).findAllFor(parent);
        
    }
    
    @Test
    public void should_return_activities_for_logged_in_user() {
        FirstLevelProcess process = new FirstLevelProcess();
        List<Process> processList = new ArrayList<>();
        processList.add(process);
        Subprocess subprocess = new Subprocess();
        List<Process> subprocessList = new ArrayList<>();
        subprocessList.add(subprocess);
        
        doReturn(processList).when(loginController).getFlProcessesForUser();
        doReturn(subprocessList).when(loginController).getSubprocessesForUser();
        loginController.getActivitiesForUser();
        verify(activityService).findAllFor(process);
        verify(activityService).findAllFor(subprocess);
    }
    
    @Test
    public void should_return_document_types_for_logged_in_user() {
        Activity activity = new Activity();
        Activity activity2 = new Activity();
        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity);
        activityList.add(activity2);
        
        doReturn(activityList).when(loginController).getActivitiesForUser();
        loginController.getDocTypesForUser();
        verify(docTypeService, times(2)).findAllFor(any());
    }
    
    @Test
    public void shouldReturnDocumentsForLoggedInUser() {
        DocumentType documentType = new DocumentType();
        List<DocumentType> docTypeList = new ArrayList<>();
        docTypeList.add(documentType);
        docTypeList.add(documentType);
        
        
        doReturn(docTypeList).when(loginController).getDocTypesForUser();
        loginController.getDocumentsForUser();
        verify(documentService, times(2)).findAllFor(documentType);
    }

    private class TestableLoginController extends LoginController {        
        @Override
        protected FacesContext getFacesContextInstance() {
            return facesContext;
        }

        @Override
        protected String start() {
           return super.start();
        }

    }
}
