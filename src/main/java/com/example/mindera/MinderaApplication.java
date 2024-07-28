package com.example.mindera;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Mindera API", version = "1", description = "API build to manage students and teachers from Mindera School"))
public class MinderaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinderaApplication.class, args);
    }

}
