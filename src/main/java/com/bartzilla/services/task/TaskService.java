package com.bartzilla.services.task;

import com.bartzilla.exceptions.NotFoundException;
import com.bartzilla.model.cron.CronTask;
import com.bartzilla.model.project.ProjectGenerationTask;
import com.bartzilla.model.Task;
import com.bartzilla.model.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class provides services for concrete implementations of {@link Task} types.
 */

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectGenerationService projectGenerationService;
    private final CronService cronService;

    public TaskService(TaskRepository taskRepository, ProjectGenerationService projectGenerationService, CronService cronService) {
        this.taskRepository = taskRepository;
        this.projectGenerationService = projectGenerationService;
        this.cronService = cronService;
    }

    public void delete(String taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(String taskId) {
        return this.get(taskId);
    }

    public Task createTask(Task task) {

        if (task instanceof CronTask) {
            return cronService.createCronTask((CronTask) task);
        }

        return projectGenerationService.createProjectTask((ProjectGenerationTask) task);
    }

    public Task update(String taskId, Task task) {
        final Task existing = this.get(taskId);

        if (task instanceof CronTask) {
            return cronService.updateCronTask((CronTask) existing, (CronTask) task);
        }

        return projectGenerationService.updateProjectTask((ProjectGenerationTask) existing,(ProjectGenerationTask) task);
    }

    public void executeTask(String taskId) {
        final Task task = this.get(taskId);

        if (task instanceof CronTask) {
            cronService.executeCronTask((CronTask) task);
        } else {
            projectGenerationService.executeProjectTask((ProjectGenerationTask) task);
        }
    }

    private Task get(String taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElseThrow(NotFoundException::new);
    }

}