package com.bartzilla.services.task.impl;

import com.bartzilla.exceptions.InternalException;
import com.bartzilla.exceptions.NotFoundException;
import com.bartzilla.exceptions.NotSuitableTaskException;
import com.bartzilla.model.Task;
import com.bartzilla.model.project.ProjectGenerationTask;
import com.bartzilla.model.project.ProjectGenerationTaskRepository;
import com.bartzilla.services.file.FileService;
import com.bartzilla.services.task.ProjectGenerationService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.Optional;

/**
 * This class represents a concrete implementation of the {@link ProjectGenerationService} interface.
 *
 * @author Cipriano Sanchez
 */

@Service
public class ProjectGenerationTaskServiceImpl implements ProjectGenerationService {
    private final ProjectGenerationTaskRepository projectGenerationTaskRepository;
    private final FileService fileService;

    public ProjectGenerationTaskServiceImpl(ProjectGenerationTaskRepository projectGenerationTaskRepository, FileService fileService) {
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
        this.fileService = fileService;
    }


    @Override
    public ProjectGenerationTask createProjectTask(ProjectGenerationTask projectGenerationTask) {
        projectGenerationTask.setId(null);
        projectGenerationTask.setCreationDate(new Date());
        return projectGenerationTaskRepository.save(projectGenerationTask);
    }

    @Override
    public ProjectGenerationTask updateProjectTask(ProjectGenerationTask existing, ProjectGenerationTask task) {
        existing.setName(task.getName());
        return projectGenerationTaskRepository.save(existing);
    }

    @Override
    public void executeProjectTask(ProjectGenerationTask task) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("MultithreadTasks.zip");
        if (url == null) {
            throw new InternalException("Zip file not found");
        }
        try {
            fileService.storeResult(task, url);
            task.setExecuted(true);
            projectGenerationTaskRepository.save(task);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    @Override
    public ResponseEntity<FileSystemResource> getProjectResult(String taskId) {
        final Task task = get(taskId);

        if (!(task instanceof ProjectGenerationTask)) {
            throw new NotSuitableTaskException();
        }

        return fileService.getTaskResult((ProjectGenerationTask) task);
    }

    private Task get(String taskId) {
        Optional<Task> task = projectGenerationTaskRepository.findById(taskId);
        return task.orElseThrow(NotFoundException::new);
    }

}
