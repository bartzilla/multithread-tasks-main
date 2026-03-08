package com.bartzilla.model.cron;


import com.bartzilla.model.TaskRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface inherits all the jpa repository's basic CRUD methods for CronTaskRepository.
 * Define custom ones for the CronTaskRepository system here.
 *
 * @author Cipriano Sanchez
 */

@Repository
public interface CronTaskRepository extends TaskRepository {
}
