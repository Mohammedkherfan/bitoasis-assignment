package com.bitoasis.assignment;

import com.bitoasis.assignment.documentation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger
@SpringBootApplication
public class AssignmentApplication {

    public static void main(String str[]) {
        SpringApplication.run(AssignmentApplication.class, str);
    }
}
