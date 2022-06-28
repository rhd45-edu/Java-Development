package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student roger = new Student(
                    1L,
                    "Roger",
                    LocalDate.of(1990, Month.MAY, 5),
                    "roger23@gmail.com"
            );

            Student alex = new Student(
                    "Alex",
                    LocalDate.of(1998, Month.MAY, 7),
                    "alex@gmail.com"
            );

            repository.saveAll(
                    List.of(roger, alex)
            );
        };
    }
}
