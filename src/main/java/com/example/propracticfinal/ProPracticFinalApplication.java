package com.example.propracticfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProPracticFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProPracticFinalApplication.class, args);
    }

}
