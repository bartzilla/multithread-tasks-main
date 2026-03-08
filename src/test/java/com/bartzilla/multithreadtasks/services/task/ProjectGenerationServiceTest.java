package com.bartzilla.multithreadtasks.services.task;

import com.bartzilla.model.project.ProjectGenerationTask;
import com.bartzilla.model.project.ProjectGenerationTaskRepository;
import com.bartzilla.services.task.impl.ProjectGenerationTaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit Test for ProjectGenerationService
 *
 * @author Cipriano Sanchez
 */

@RunWith(MockitoJUnitRunner.class)
public class ProjectGenerationServiceTest {

    @Mock
    ProjectGenerationTaskRepository projectGenerationRepository;

    @InjectMocks
    ProjectGenerationTaskServiceImpl projectGenerationService;

    @Test
    public void testCreateProject() {
        ProjectGenerationTask task = new ProjectGenerationTask();
        Mockito.when(projectGenerationRepository.save(task)).thenReturn(task);

        task.setName("test-project");
        task.setExecuted(false);
        final ProjectGenerationTask savedTask = projectGenerationService.createProjectTask(task);

        assertEquals(savedTask, task);

    }
}
