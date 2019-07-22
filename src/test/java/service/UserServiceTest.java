package service;

import com.mr.dao.UserDAO;
import com.mr.domain.Company;
import com.mr.domain.Role;
import com.mr.domain.User;
import com.mr.service.UserService;
import com.mr.service.impl.UserServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock 
    UserDAO userDAO;
    @InjectMocks 
    UserService userService = new UserServiceImpl();
    
    @Test
    public void shoulAskForCompanyEmployersByExample() {
        User user = new User();
        Company company = new Company();
        user.setCompany(company);
        ArgumentCaptor<Example> argument = ArgumentCaptor.forClass(Example.class);
        
        userService.findAllFor(company);
        verify(userDAO).findAll(argument.capture());
       
        user = (User) argument.getValue().getProbe();
        assertEquals(company, user.getCompany());
        assertNull(user.getFirstName());     
    }
    
    @Test
    public void shouldCheckIfUserIsAdministrator() {
        User user = new User();
        user.setUserRole(Role.ADMIN);

        assertTrue(userService.isAdmin(user));
        
        user.setUserRole(Role.USER);
        
        assertFalse(userService.isAdmin(user));
        
    }
}
