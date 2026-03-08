package com.bartzilla.model.project;

import com.bartzilla.model.TaskRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface inherits all the jpa repository's basic CRUD methods for ProjectGenerationTaskRepository.
 * Define custom ones for the ProjectGenerationTaskRepository system here.
 *
 * @author Cipriano Sanchez
 */

@Repository
public interface ProjectGenerationTaskRepository extends TaskRepository {
}
