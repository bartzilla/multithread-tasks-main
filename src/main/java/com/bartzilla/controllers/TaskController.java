package com.bartzilla.controllers;

import com.bartzilla.model.Task;
import com.bartzilla.services.task.CronService;
import com.bartzilla.services.task.ProjectGenerationService;
import com.bartzilla.services.task.TaskService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller in charge of routing Task endpoints.
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CronService cronService;
    private final ProjectGenerationService projectGenerationService;

    public TaskController(TaskService taskService, CronService cronService, ProjectGenerationService projectGenerationService) {
        this.taskService = taskService;
        this.cronService = cronService;
        this.projectGenerationService = projectGenerationService;
    }

    @GetMapping("/")
    public List<Task> listTasks() {
        return taskService.listTasks();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody @Valid Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@PathVariable String taskId, @RequestBody @Valid Task task) {
        return taskService.update(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        taskService.delete(taskId);
    }

    @PostMapping("/{taskId}/execute")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void executeTask(@PathVariable String taskId) {
        taskService.executeTask(taskId);
    }

    @GetMapping("/{taskId}/result")
    public ResponseEntity<FileSystemResource> getResult(@PathVariable String taskId) {
        return projectGenerationService.getProjectResult(taskId);
    }

    @PostMapping("/{taskId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String stopCounter(@PathVariable String taskId) {
        return cronService.stopCronTask(taskId);
    }

    @GetMapping("/{taskId}/progress")
    public String getProgress(@PathVariable String taskId) {
        return cronService.getCronProgress(taskId);
    }
}
