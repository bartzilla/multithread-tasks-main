package com.bartzilla.services.task;

import com.bartzilla.model.cron.CronTask;

/**
 * This interface represents the contract to follow for all classes that
 * want to implement functionality related to Cron Task Service.
 *
 * @author Cipriano Sanchez
 */

public interface CronService {

    /**
     * Stops the execution of the given <code>CronTask</code>'s id.
     *
     * @param taskId The <code>CronTask</code>'s id to be stopped.
     * @return <code>String</code> A status notification message.
     */
    String stopCronTask(String taskId);

    /**
     * Create a <code>CronTask</code>
     *
     * @param counterTask The <code>CronTask</code> object to be created.
     * @return <code>CronTask</code> if success the new created object is returned.
     */
    CronTask createCronTask(CronTask counterTask);

    /**
     * Update a particular <code>CronTask</code> with a new given <code>CronTask</code>'s project.
     *
     * @param existing The existing the <code>CronTask</code> to be updated.
     * @param cronTask The new <code>CronTask</code> to update with.
     * @return <code>CronTask</code> object which was updated.
     */
    CronTask updateCronTask(CronTask existing, CronTask cronTask);

    /**
     * Returns the progress of an executing <code>CronTask</code>.
     *
     * @param taskId The id the <code>ProjectGenerationTask</code> to get the result of.
     * @return <code>String</code> The current progress notification message.
     */
    String getCronProgress(String taskId);

    /**
     * Execute an already existing <code>CronTask</code>.
     *
     * @param cronTask The <code>CronTask</code> object to be executed.
     */
    void executeCronTask(CronTask cronTask);
}
