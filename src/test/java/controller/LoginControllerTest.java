package controller;

import com.mr.domain.Role;
import com.mr.domain.User;
import com.mr.controller.HierarchyController;
import com.mr.controller.LoginController;
import com.mr.service.DocumentService;
import com.mr.service.DocumentTypeService;
import com.mr.service.UserService;
import java.util.Optional;
import javax.faces.context.FacesContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

   
    private User registeredUser;
    private String enteredUsername;
    private String enteredPassword;
    @Mock private User loggedInUser;
    @Mock private UserService userService;
    @Mock private FacesContext facesContext;
    @Mock HierarchyController hierarchyController;
    @Mock DocumentTypeService docTypeService;
    @Mock DocumentService documentService;
    @InjectMocks @Spy
    TestableLoginController loginController = new TestableLoginController();

    @Test
    public void shouldNotLetUnregisteredUsersLogIn() {
        enteredUsername = "anyUsername";

        loginController.setUsername(enteredUsername);
        when(userService.findByUsername(anyString())).thenReturn(Optional.empty());
        assertEquals("", loginController.login());
        verify(userService).findByUsername(enteredUsername);
    }

    @Test
    public void shouldNotLetUserLogInWithWrongPassword() {
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
    public void shouldLoginUserWithCorrectCredentials() {
        enteredUsername = "someUsername";
        enteredPassword = "somePassword";
        loginController.setUsername(enteredUsername);
        loginController.setPassword(enteredPassword);

        registeredUser = new User();
        registeredUser.setUsername(enteredUsername);
        registeredUser.setPassword(enteredPassword);

        when(userService.findByUsername(enteredUsername)).thenReturn(Optional.of(registeredUser));
        loginController.login();
        verify(hierarchyController).start(registeredUser);
    }

    @Test
    public void shouldCheckIfUserIsAdministrator() {
        Role admin = Role.ADMIN;
        when(loggedInUser.getUserRole()).thenReturn(admin);

        assertTrue(loginController.isAdmin());
    }

    
    @Test
    public void shouldLogoutAUser() {
        assertNotNull(loggedInUser);
        assertEquals("index", loginController.logout());
        verify(loginController).setLoggedInUser(null);
        
        
    }
   
    private class TestableLoginController extends LoginController {        
        @Override
        protected FacesContext getFacesContextInstance() {
            return facesContext;
        }

  }
}
