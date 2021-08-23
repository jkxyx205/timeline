package com.rick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Rick
 * @createdAt 2021-08-17 16:46:00
 */
@SpringBootApplication
@EnableScheduling
public class TimelineApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimelineApplication.class, args);
    }
}
