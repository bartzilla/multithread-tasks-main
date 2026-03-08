package com.bartzilla.services.task;

import com.bartzilla.model.project.ProjectGenerationTask;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

/**
 * This interface represents the contract to follow for all classes that
 * want to implement functionality related to Project Generation Service.
 *
 * @author Cipriano Sanchez
 */

public interface ProjectGenerationService {

    /**
     * Create a <code>ProjectGenerationTask</code>
     *
     * @param projectGenerationTask The <code>ProjectGenerationTask</code> object to be created.
     * @return <code>ProjectGenerationTask</code> if success the new created object is returned.
     */
    ProjectGenerationTask createProjectTask(ProjectGenerationTask projectGenerationTask);

    /**
     * Update a particular <code>ProjectGenerationTask</code> with a new given <code>ProjectGenerationTask</code>'s project.
     *
     * @param existing The existing the <code>ProjectGenerationTask</code> to be updated.
     * @param task     The new <code>ProjectGenerationTask</code> to update with.
     * @return <code>ProjectGenerationTask</code> object which was updated.
     */
    ProjectGenerationTask updateProjectTask(ProjectGenerationTask existing, ProjectGenerationTask task);

    /**
     * Execute and stored <code>ProjectGenerationTask</code>
     *
     * @param task The <code>ProjectGenerationTask</code> object to be executed.
     */
    void executeProjectTask(ProjectGenerationTask task);

    /**
     * Returns a particular <code>FileSystemResource</code> for a given <code>ProjectGenerationTask</code>'s id.
     *
     * @param taskId The id the <code>ProjectGenerationTask</code> to get the result of.
     * @return <code>ProjectGenerationTask</code> object if found.
     */
    ResponseEntity<FileSystemResource> getProjectResult(String taskId);

}
