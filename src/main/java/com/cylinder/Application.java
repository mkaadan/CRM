package com.cylinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.cylinder"})
@SpringBootApplication
public class Application {

    // Entry point to the application. Scans all of com.cylinder for springframework
// annotations and maps the logic. 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
