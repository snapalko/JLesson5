package ru.inno.task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.inno.task5")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
        System.out.println("Go to http://localhost:8080/corporate-settlement-account/create");
        System.out.println("for create Product Registry");
        System.out.println("or http://localhost:8080/corporate-settlement-instance/create");
        System.out.println("for create Product and/or Agreements");
    }
}
