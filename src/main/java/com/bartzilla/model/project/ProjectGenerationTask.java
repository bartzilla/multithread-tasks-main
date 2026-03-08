package com.bartzilla.model.project;

import com.bartzilla.model.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

/**
 * Concrete class to represent ProjectGenerationTask objects.
 *
 * @author Cipriano Sanchez
 */

@Entity
public class ProjectGenerationTask extends Task {

    @JsonIgnore
    private String storageLocation;

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

}
