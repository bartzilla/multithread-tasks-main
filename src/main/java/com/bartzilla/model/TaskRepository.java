package com.bartzilla.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface inherits all the jpa repository's basic CRUD methods for TaskRepository.
 *
 * @author Cipriano Sanchez
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}
