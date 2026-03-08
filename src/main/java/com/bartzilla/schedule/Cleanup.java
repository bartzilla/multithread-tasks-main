package com.bartzilla.schedule;

import com.bartzilla.model.Task;
import com.bartzilla.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Cleanup object to run periodically to delete tasks marked as expired.
 * The cleanup schedule can be configured via app properties
 *
 * @author Cipriano Sanchez
 */

@Component
public class Cleanup {
    private final TaskRepository taskRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${time.unit}")
    private String timeUnit;

    @Value("${time.unit.duration}")
    private int duration;

    public Cleanup(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(cron = "${cron.cleanup.schedule}")
    public void deleteExpiredTasksCron() {

        log.debug("Checking for expired tasks");
        final List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {

            // task will expire on time defined in app properties
            final Date expires = new Date(task.getCreationDate().getTime()
                    + TimeUnit.valueOf(getTimeUnit()).toMillis(getDuration()));

            if (!task.isExecuted() && isTaskExpired(expires)) {
                log.info("Deleting task: " + task.getName());
                taskRepository.delete(task);
            }
        }
    }

    private boolean isTaskExpired(Date expires) {
        return new Date().getTime() > expires.getTime();
    }

    public int getDuration() {
        return duration;
    }

    public String getTimeUnit() {
        return timeUnit;
    }
}
