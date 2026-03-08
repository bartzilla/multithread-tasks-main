package com.bartzilla.services.task.impl;

import com.bartzilla.model.cron.CronTask;
import com.bartzilla.model.cron.CronTaskRepository;
import com.bartzilla.schedule.Counter;
import com.bartzilla.services.task.CronService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a concrete implementation of the {@link CronService} interface.
 *
 * @author Cipriano Sanchez
 */

@Service
public class CronTaskServiceImpl implements CronService {
    private final CronTaskRepository cronTaskRepository;
    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    public CronTaskServiceImpl(CronTaskRepository cronTaskRepository) {
        this.cronTaskRepository = cronTaskRepository;
    }

    @Override
    public String stopCronTask(String taskId) {
        Counter counter = counters.get(taskId);

        if (counter != null) {
            counter.stop();
            counters.remove(taskId);
        }
        return "Counter with ID " + taskId + " stopped";
    }

    @Override
    public CronTask createCronTask(CronTask cronTask) {
        cronTask.setId(null);
        cronTask.setCreationDate(new Date());
        cronTask.setExecuted(false);
        cronTask.setX(cronTask.getX());
        cronTask.setY(cronTask.getY());

        return cronTaskRepository.save(cronTask);
    }

    @Override
    public CronTask updateCronTask(CronTask existing, CronTask cronTask) {
        // creationDate should not modifiable

        existing.setName(cronTask.getName());
        existing.setX(cronTask.getX());
        existing.setY(cronTask.getY());

        return cronTaskRepository.save(existing);
    }

    @Override
    public void executeCronTask(CronTask cronTask) {
        final String taskId = cronTask.getId();

        if (counters.containsKey(taskId)) {
            throw new IllegalStateException("Counter with ID " + taskId + " is already running.");
        }

        Counter counter = new Counter(cronTask);
        counters.put(taskId, counter);
        counter.start();
        cronTask.setExecuted(true);
        cronTaskRepository.save(cronTask);
    }

    public String getCronProgress(String taskId) {

        Counter counter = counters.get(taskId);

        if (counter != null) {
            if (counter.isRunning()) {
                return "Counter with ID " + taskId + " is running. Current value: " + counter.getCurrentValue();
            } else {
                return "Counter with ID " + taskId + " is not running. Last value: " + (counter.getCurrentValue() - 1);
            }
        } else {
            return "No task counter with ID:" + taskId + " found running";
        }

    }
}
