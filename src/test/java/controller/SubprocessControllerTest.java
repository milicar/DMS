package controller;

import com.mr.controller.SubprocessController;
import com.mr.domain.Activity;
import com.mr.domain.FirstLevelProcess;
import com.mr.domain.Subprocess;
import com.mr.domain.Process;
import com.mr.service.ActivityService;
import com.mr.service.ProcessService;
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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SubprocessControllerTest {

    @Mock
    ProcessService processService;
    @Mock
    ActivityService activityService;
    @InjectMocks @Spy
    SubprocessController subprocessController;
    
    private Subprocess subprocess;
    private Process parent;
    private List<Process> subprocessList;
    private List<Process> processChildren;
    private List<Activity> activityChildren;
    
    @Test
    public void shouldCreateNewSubprocessForParentFlProcess() {
        parent = new FirstLevelProcess();
        
        subprocessController.createNewFor(parent);
        
        assertEquals(parent, subprocessController.getParent());
        assertNotNull(subprocessController.getSubprocess());
        assertEquals("subprocess_form", subprocessController.createNewFor(parent));
    }
    
    @Test
    public void shouldCreateNewForParentNotFirstLevelProcess() {
        parent = new Subprocess();
        
        subprocessController.createNewFor(parent);
        
        assertEquals(parent, subprocessController.getParent());
        assertNotNull(subprocessController.getSubprocess());
        assertEquals("subprocess_form", subprocessController.createNewFor(parent));
    }
    
    @Test
    public void shouldShowAllSubprocessesForFlParent() {
        parent = new FirstLevelProcess();
        subprocess = new Subprocess();
        subprocessList = new ArrayList<>();
        subprocessList.add(subprocess);
        when(processService.findAllFor(parent)).thenReturn(subprocessList);
        
        subprocessController.showAllFor(parent);
        
        verify(processService).findAllFor(parent);
        assertEquals(parent, subprocessController.getParent());
        assertEquals(subprocessList, subprocessController.getSubprocessList());
        assertEquals("list_subprocesses", subprocessController.showAllFor(parent));  
    }
    
    @Test
    public void shouldShowAlSubprocessesForNotFirstLevelProcess() {
        parent = new Subprocess();
        subprocess = new Subprocess();
        subprocessList = new ArrayList<>();
        subprocessList.add(subprocess);
        when(processService.findAllFor(parent)).thenReturn(subprocessList);

        subprocessController.showAllFor(parent);

        verify(processService).findAllFor(parent);
        assertEquals(parent, subprocessController.getParent());
        assertEquals(subprocessList, subprocessController.getSubprocessList());
        assertEquals("list_subprocesses", subprocessController.showAllFor(parent));
    }
    
    @Test
    public void shouldShowOneSubprocessWithItsSubprocesses() {
        subprocess = new Subprocess();
        parent = new Subprocess();
        subprocess.setParent(parent);
        processChildren = new ArrayList<>();
        processChildren.add(new Subprocess());
        when(processService.findAllFor(subprocess)).thenReturn(processChildren);
        
        subprocessController.show(subprocess);
        
        verify(processService).findAllFor(subprocess);
        verify(activityService).findAllFor(subprocess);
        assertEquals(parent, subprocessController.getParent());
        assertEquals(subprocess, subprocessController.getSubprocess());
        assertEquals(processChildren, subprocessController.getProcessChildren());
        assertTrue(subprocessController.getActivityChildren().isEmpty());
        assertEquals("subprocess", subprocessController.show(subprocess));   
    }
    
    @Test
    public void shouldShowOneSubprocessWithItsActivities() {
        subprocess = new Subprocess();
        parent = new Subprocess();
        subprocess.setParent(parent);
        activityChildren = new ArrayList<>();
        activityChildren.add(new Activity());
        when(activityService.findAllFor(subprocess)).thenReturn(activityChildren);
        
        subprocessController.show(subprocess);
        
        verify(processService).findAllFor(subprocess);
        verify(activityService).findAllFor(subprocess);
        assertEquals(parent, subprocessController.getParent());
        assertEquals(subprocess, subprocessController.getSubprocess());
        assertTrue(subprocessController.getProcessChildren().isEmpty());
        assertEquals(activityChildren, subprocessController.getActivityChildren());
        assertEquals("subprocess", subprocessController.show(subprocess));
    }
    
    @Test
    public void shouldSaveSubprocess() {
        subprocess = new Subprocess();
        subprocessController.setSubprocess(subprocess);
        parent = new Subprocess();
        subprocessController.setParent(parent);
        
        subprocessController.save();
        
        verify(processService).save(subprocess);
        assertEquals(parent, subprocessController.getParent());
        assertEquals(subprocess, subprocessController.getSubprocess());  
        verify(subprocessController).showAllFor(parent);
    }
    
    @Test
    public void shouldEditSubprocess() {
        subprocess = new Subprocess();
        
        subprocessController.edit(subprocess);
        
        assertEquals(subprocess, subprocessController.getSubprocess());
        assertEquals("subprocess_form", subprocessController.edit(subprocess));
    }
    
    @Test
    public void shouldDeleteSubprocess() {
        subprocess = new Subprocess();
        parent = new Subprocess();
        subprocessController.setParent(parent);
        
        subprocessController.delete(subprocess);
        
        verify(processService).delete(subprocess);
        verify(subprocessController).showAllFor(parent);
    }
}
