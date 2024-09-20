package io.javabrains.springbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Tells Spring Boot that this class (CourseApiApp) is the starting point for our SpringBoot Application
public class CourseApiApp {

    // entry point for our Entire Application
    public static void main(String[] args) {
        /*
            1. Start this Application.
            2. Create a Servlet Container.
            3. Host this Application to the created Servlet Container.
            4. Make the Application Available

            The next line of code does all of that heavy lifting. run is a static method
            You need to provide the class which has the main method and annotated with @SpringBootApplication

            What is does?
            1. Sets up default configuration (80% Use Case).
            2. Starts Spring Application Context (Container for Application, Controllers, Services, DAO objects).
            3. Performs a class path scan. (Spring scans all classes with annotations (@Controller, @Service, @Configuration etc)
            4. Starts Tomcat Server (Inbuilt with SpringBoot)
         */

        SpringApplication.run(CourseApiApp.class, args);
    }
}
