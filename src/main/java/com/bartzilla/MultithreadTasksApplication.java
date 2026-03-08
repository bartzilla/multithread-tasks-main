package com.bartzilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *  Application starter for Spring
 *
 *  @author Cipriano Sanchez
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MultithreadTasksApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultithreadTasksApplication.class, args);
    }
}
