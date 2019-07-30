package controller;

import com.mr.controller.UserController;
import com.mr.domain.Company;
import com.mr.domain.User;
import com.mr.service.CompanyService;
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private User user;
    private List<User> userList;
    @Mock
    private UserService userService;
    @Mock
    private CompanyService companyService;
    @InjectMocks @Spy
    private UserController userController;
    
    
    @Test
    public void shouldCreateNewUser() {
        
        userController.createNew();
        
        assertEquals("0", userController.getCompanyIdString());
        assertNotNull(userController.getUser());
        assertEquals("user_form", userController.createNew());
    }
    
    @Test
    public void shouldShowAUser() {
        user = new User();
        
        userController.show(user);
        
        assertEquals(user, userController.getUser());
        assertEquals("user", userController.show(user));
    }
    
    @Test
    public void shouldEditAUser() {
        user = new User();
        Company company = new Company();
        company.setCompanyID(202l);
        user.setCompany(company);        
        
        userController.edit(user);
        
        assertEquals(user, userController.getUser());
        assertEquals("202", userController.getCompanyIdString());
        assertEquals("user_form", userController.edit(user));
    }
    
    @Test
    public void shouldSaveAUser() {
        user = new User();
        userController.setUser(user);
        Company company = new Company();
        userController.setCompanyIdString("202");
        when(companyService.findById(202l)).thenReturn(company);
        
        userController.save();
        
        assertEquals(user, userController.getUser());
        assertEquals(company, userController.getUser().getCompany());
        verify(userService).save(user);
        verify(userController).showAll();
    }    
    
    @Test
    public void shouldDeleteAUser() {
        user = new User();
        
        userController.delete(user);
        
        verify(userService).delete(user);
        verify(userController).showAll();        
    }
    
    @Test
    public void shouldShowAllUsers() {
        user = new User();
        userList = new ArrayList();
        userList.add(user);
        when(userService.findAll()).thenReturn(userList);
        
        userController.showAll();
        
        assertEquals(userList, userController.getUserList());
        assertEquals(user, userController.getUserList().get(0));
        assertEquals("list_users", userController.showAll());
    }
      
}
