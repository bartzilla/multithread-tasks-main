package com.bartzilla.services.file;

import com.bartzilla.exceptions.InternalException;
import com.bartzilla.model.project.ProjectGenerationTask;
import com.bartzilla.model.project.ProjectGenerationTaskRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

/**
 * This class provides services for concrete implementations for file generation functionality.
 */

@Component
public class FileService {

    private final ProjectGenerationTaskRepository projectGenerationTaskRepository;

    public FileService(ProjectGenerationTaskRepository projectGenerationTaskRepository) {
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
    }

    public ResponseEntity<FileSystemResource> getTaskResult(ProjectGenerationTask projectGenerationTask) {
        File inputFile = new File(projectGenerationTask.getStorageLocation());

        if (!inputFile.exists()) {
            throw new InternalException("File not generated yet");
        }

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("attachment", "MultithreadTasks.zip");

        return new ResponseEntity<>(new FileSystemResource(inputFile), respHeaders, HttpStatus.OK);
    }

    public void storeResult(ProjectGenerationTask projectGenerationTask, URL url) throws IOException {
        File outputFile = File.createTempFile(projectGenerationTask.getId(), ".zip");
        outputFile.deleteOnExit();
        projectGenerationTask.setStorageLocation(outputFile.getAbsolutePath());
        projectGenerationTaskRepository.save(projectGenerationTask);
        try (InputStream is = url.openStream();
             OutputStream os = new FileOutputStream(outputFile)) {
            IOUtils.copy(is, os);
        }
    }
}
