package service;

import com.mr.dao.ContactDAO;
import com.mr.domain.Company;
import com.mr.domain.Contact;
import com.mr.service.ContactService;
import com.mr.service.impl.ContactServiceImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @Mock
    ContactDAO contactDAO;
    @InjectMocks
    ContactService contactService = new ContactServiceImpl();

    @Test
    public void shouldAskForCompanyContactsByExample() {
        Contact exampleContact = new Contact();
        Company company = new Company();
        exampleContact.setCompany(company);
        ArgumentCaptor<Example> argument = ArgumentCaptor.forClass(Example.class);

        contactService.findAllFor(company);

        verify(contactDAO).findAll(argument.capture());
        exampleContact = (Contact) argument.getValue().getProbe();
        assertEquals(company, exampleContact.getCompany());
    }
}
