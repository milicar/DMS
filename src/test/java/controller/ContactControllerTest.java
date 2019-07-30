package controller;

import com.mr.controller.ContactController;
import com.mr.domain.Company;
import com.mr.domain.Contact;
import com.mr.domain.User;
import com.mr.service.CompanyService;
import com.mr.service.ContactService;
import com.mr.service.UserService;
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
public class ContactControllerTest {

    private Contact contact;
    private List<Contact> contactList;
    private @Mock
    UserService userService;
    private @Mock
    ContactService contactService;
    private @Mock
    CompanyService companyService;
    @InjectMocks
    @Spy
    private ContactController contactController;

    @Test
    public void shouldShowAllContactsToAdmin() {
        User admin = new User();
        when(userService.isAdmin(admin)).thenReturn(Boolean.TRUE);
        contactList = new ArrayList<>();
        when(contactService.findAll()).thenReturn(contactList);

        contactController.showAllFor(admin);

        assertEquals(contactList, contactController.getContactList());
        assertEquals("list_contacts", contactController.showAllFor(admin));
    }

    @Test
    public void shouldShowOnlyCompanyContactsToEmployee() {
        User employee = new User();
        when(userService.isAdmin(employee)).thenReturn(Boolean.FALSE);
        contactList = new ArrayList<>();
        contactList.add(new Contact());
        Company company = new Company();
        when(userService.getUsersCompany(employee)).thenReturn(company);
        when(contactService.findAllFor(company)).thenReturn(contactList);

        contactController.showAllFor(employee);

        assertEquals("list_contacts", contactController.showAllFor(employee));
        assertEquals(contactList, contactController.getContactList());
    }

    @Test
    public void shouldCreateNewContact() {

        contactController.createNew();

        assertEquals("0", contactController.getCompanyIdString());
        assertNotNull(contactController.getContact());
        assertEquals("contact_form", contactController.createNew());
    }

    @Test
    public void shouldShowContact() {
        contact = new Contact();

        contactController.show(contact);

        assertEquals(contact, contactController.getContact());
        assertEquals("contact", contactController.show(contact));
    }

    @Test
    public void shouldEditAContact() {
        contact = new Contact();
        Company company = new Company();
        company.setCompanyID(101l);
        contact.setCompany(company);

        contactController.edit(contact);

        assertEquals(contact, contactController.getContact());
        assertEquals("101", contactController.getCompanyIdString());
        assertEquals("contact_form", contactController.edit(contact));
    }

    @Test
    public void shouldSaveAContact() {
        contact = new Contact();
        contactController.setContact(contact);
        Company company = new Company();
        contactController.setCompanyIdString("101");
        when(companyService.findById(101l)).thenReturn(company);

        contactController.save();

        assertEquals(contact, contactController.getContact());
        assertEquals(company, contactController.getContact().getCompany());
        verify(contactService).save(contact);
        verify(contactController).showAll();
    }

    @Test
    public void shouldDeleteAContact() {

        contactController.delete(contact);

        verify(contactService).delete(contact);
        verify(contactController).showAll();
    }
}
