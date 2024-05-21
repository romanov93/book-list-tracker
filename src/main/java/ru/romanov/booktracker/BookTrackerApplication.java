package ru.romanov.booktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookTrackerApplication.class, args);
    }

}
