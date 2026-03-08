package com.bartzilla.schedule;

import com.bartzilla.model.Task;
import com.bartzilla.model.cron.CronTask;
import com.bartzilla.services.task.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

/**
 * Integration Test for Cleanup component class
 *
 *  @author Cipriano Sanchez
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CleanupIntegrationTest {

    @Autowired
    Cleanup cleanup;

    @Autowired
    private TaskService taskService;

    @Test
    public void testDeleteOldCronTask() {
        // Create a task which will not be executed and will be 3 seconds old,
        // so it can be marked to delete
        CronTask task = new CronTask("123","cron-project", 100, 100, new Date(), false);

        // The new task is persisted, so it can be checked for successful retrieval
        final Task createdTask = taskService.createTask(task);
        List<Task> allTasks = taskService.listTasks();

        // Check the task was created and successfully retrieved
        assertTrue(allTasks.contains(createdTask));

        // Call delete expired function when the task has not expired
        cleanup.deleteExpiredTasksCron();

        try {

            TimeUnit.SECONDS.sleep(5);
            // Call delete expired function when task has expired
            cleanup.deleteExpiredTasksCron();

            // Check that the task is no longer in the db
            assertTrue(taskService.listTasks().isEmpty());

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
