package com.bartzilla.schedule;

import com.bartzilla.model.cron.CronTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counter object to run Cron tasks against. This object contains information about the current executing Crons
 *
 * @author Cipriano Sanchez
 */

public class Counter {
    private final AtomicInteger currentValue;
    private final AtomicInteger targetValue;
    private final AtomicBoolean running;
    private final CronTask counterTask;
    private Thread counterThread;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Counter(CronTask cronTask) {
        this.currentValue = new AtomicInteger(cronTask.getX());
        this.targetValue = new AtomicInteger(cronTask.getY());
        this.running = new AtomicBoolean(false);
        this.counterTask = cronTask;
    }

    public void start() {
        running.set(true);

        counterThread = new Thread(() -> {
            while (running.get() && currentValue.get() <= targetValue.get()) {
                try {
                    Thread.sleep(1000);
                    log.debug(counterTask.getName() + " -> " + currentValue.get());
                    currentValue.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running.set(false);
                }
            }
            running.set(false);
        });
        counterThread.start();
    }

    public void stop() {
        running.set(false);
        if (counterThread != null) {
            counterThread.interrupt();
        }
    }

    public int getCurrentValue() {
        return currentValue.get();
    }

    public boolean isRunning() {
        return running.get();
    }

}

