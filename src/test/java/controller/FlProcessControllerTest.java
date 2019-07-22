package controller;

import com.mr.service.ProcessService;
import com.mr.controller.FlProcessController;
import com.mr.controller.HierarchyController;
import com.mr.domain.Activity;
import com.mr.domain.Company;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Process;
import com.mr.domain.Subprocess;
import com.mr.domain.User;
import com.mr.service.ActivityService;
import com.mr.service.UserService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlProcessControllerTest {

    Company company;
    Process process;
    Process subprocess;
    List<Process> processesList;
    List<Activity> activitiesList;
    @Mock
    HierarchyController hierarchyController;
    @Mock
    UserService userService;
    @Mock
    ProcessService processService;
    @Mock
    ActivityService activityService;
    @InjectMocks
    FlProcessController flProcessControler;

    @Test
    public void shouldReturnAllProcessesForCompany() {
        company = new Company();
        process = new FirstLevelProcess();
        processesList = new ArrayList<>();
        processesList.add(process);
        when(processService.findAllFor(company)).thenReturn(processesList);

        flProcessControler.showAllFor(company);

        verify(processService).findAllFor(company);
        assertEquals(company, flProcessControler.getParentCompany());
        assertEquals(processesList, flProcessControler.getProcesses());
        assertEquals("list_flprocesses", flProcessControler.showAllFor(company));
    }

    @Test
    public void shouldReturnCompanyProcessesForEmployee() {
        User user = new User();
        process = new FirstLevelProcess();
        processesList = new ArrayList<>();
        processesList.add(process);
        when(userService.isAdmin(user)).thenReturn(Boolean.FALSE);
        when(hierarchyController.buildListOfFirstLevelProcessesFor(user)).thenReturn(processesList);

        flProcessControler.showAllFor(user);

        assertEquals(processesList, flProcessControler.getProcesses());
        assertEquals("list_flprocesses", flProcessControler.showAllFor(user));
    }

    @Test
    public void shouldReturnProcessesForAllCompaniesForAdmin() {
        User admin = new User();
        process = new FirstLevelProcess();
        processesList = new ArrayList<>();
        processesList.add(process);
        when(processService.findAll()).thenReturn(processesList);
        when(userService.isAdmin(admin)).thenReturn(Boolean.TRUE);

        flProcessControler.showAllFor(admin);

        verify(processService).findAll();
        assertEquals(processesList, flProcessControler.getProcesses());
        assertEquals("list_flprocesses", flProcessControler.showAllFor(admin));
    }

    @Test
    public void shouldCreateNewFlProcessForACompany() {
        company = new Company();

        flProcessControler.createNewFor(company);

        assertEquals(company, flProcessControler.getParentCompany());
        assertNotNull(flProcessControler.getFlProcess());
        assertEquals("flprocess_form", flProcessControler.createNewFor(company));

    }

    @Test
    public void shouldShowOneProcessWithSubprocesses() {
        process = new FirstLevelProcess();
        subprocess = new Subprocess();
        processesList = new ArrayList<>();
        processesList.add(subprocess);
        when(processService.findAllFor(process)).thenReturn(processesList);

        flProcessControler.show((FirstLevelProcess) process);

        verify(processService).findAllFor(process);
        verify(activityService).findAllFor(process);
        assertEquals(process, flProcessControler.getFlProcess());
        assertEquals(processesList, flProcessControler.getProcessChildren());
        assertTrue(flProcessControler.getActivityChildren().isEmpty());
        assertEquals("flprocess", flProcessControler.show((FirstLevelProcess) process));
    }

    @Test
    public void shouldShowOneProcessWithItsActivities() {
        process = new FirstLevelProcess();
        Activity activity = new Activity();
        activitiesList = new ArrayList<>();
        activitiesList.add(activity);
        when(activityService.findAllFor(process)).thenReturn(activitiesList);

        flProcessControler.show((FirstLevelProcess) process);

        verify(processService).findAllFor(process);
        verify(activityService).findAllFor(process);
        assertEquals(process, flProcessControler.getFlProcess());
        assertTrue(flProcessControler.getProcessChildren().isEmpty());
        assertEquals(activitiesList, flProcessControler.getActivityChildren());
        assertEquals("flprocess", flProcessControler.show((FirstLevelProcess) process));
    }

    @Test
    public void shouldSaveProcess() {
        process = new FirstLevelProcess();
        flProcessControler.setFlProcess((FirstLevelProcess) process);
        company = new Company();
        flProcessControler.setParentCompany(company);

        flProcessControler.save();

        verify(processService).save(process);
        assertEquals(company, flProcessControler.getFlProcess().getParent());
    }

    @Test
    public void shouldEditProcess() {
        process = new FirstLevelProcess();

        flProcessControler.edit((FirstLevelProcess) process);

        assertEquals(process, flProcessControler.getFlProcess());
        assertEquals("flprocess_form", flProcessControler.edit((FirstLevelProcess) process));
    }

    @Test
    public void shouldDeleteProcess() {
        process = new FirstLevelProcess();

        flProcessControler.delete((FirstLevelProcess) process);

        verify(processService).delete(process);
    }
}
