package com.example.mindera.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CourseDto {
    private Long id;
    private String name;
}
