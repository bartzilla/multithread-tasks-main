package com.bartzilla.model.cron;

import com.bartzilla.model.Task;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Concrete class to represent CronTask objects.
 *
 * @author Cipriano Sanchez
 */

@Entity
public class CronTask extends Task {

    private int x;
    private int y;

    public CronTask() {
    }

    public CronTask(String id, String name, int x, int y, Date creationDate, boolean executed) {
        super(id, name, creationDate, executed);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
