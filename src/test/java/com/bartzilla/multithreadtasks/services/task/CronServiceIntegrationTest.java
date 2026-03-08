package com.bartzilla.multithreadtasks.services.task;

import com.bartzilla.model.cron.CronTask;
import com.bartzilla.services.task.CronService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

/**
 * Integration Test for CronService
 *
 * @author Cipriano Sanchez class
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CronServiceIntegrationTest {

    @Autowired
    CronService cronTaskServiceImpl;

    @Test
    public void testCreateCronTask() {
        CronTask task = new CronTask("123", "cron-project", 0, 10, new Date(), false);

        CronTask savedTask = cronTaskServiceImpl.createCronTask(task);

        assertEquals(savedTask, task);
    }

    @Test
    public void testExecuteCronTask() {
        final int timeout = 5;
        final CronTask task = new CronTask("1", "cron-project", 1, 10, new Date(), false);

        final CronTask createdTask = cronTaskServiceImpl.createCronTask(task);

        cronTaskServiceImpl.executeCronTask(createdTask);
        final String running = "Counter with ID " + createdTask.getId() + " is running. Current value: " + timeout;
        final String stop = "Counter with ID " + createdTask.getId() + " stopped";
        try {

            TimeUnit.SECONDS.sleep(timeout);

            final String cronProgress = cronTaskServiceImpl.getCronProgress(createdTask.getId());

            // Check the task reports that the task is running and has the same timelapse
            assertEquals(cronProgress, running);

        } catch (InterruptedException ie) {
            // When timeout is trigger stop cron task
            final String notification = cronTaskServiceImpl.stopCronTask(createdTask.getId());
            assertEquals(stop, notification);
        }
    }

    @Test
    public void testStopCronTask() {
        CronTask task = new CronTask("123", "cron-project", 0, 10, new Date(), false);

        CronTask savedTask = cronTaskServiceImpl.createCronTask(task);

        assertEquals(savedTask, task);
    }
}
