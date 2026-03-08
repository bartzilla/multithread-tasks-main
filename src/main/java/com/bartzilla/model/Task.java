package com.bartzilla.model;

import com.bartzilla.model.cron.CronTask;
import com.bartzilla.model.project.ProjectGenerationTask;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Abstract class to represent a Task.
 *
 * @author Cipriano Sanchez
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CronTask.class, name = "cron"),
        @JsonSubTypes.Type(value = ProjectGenerationTask.class, name = "project")
})
public abstract class Task {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private Date creationDate;

    private boolean executed;

    public Task() {
    }

    public Task(String id, String name, Date creationDate, boolean executed) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.executed = executed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

}
