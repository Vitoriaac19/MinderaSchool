package com.example.mindera.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TeacherDto {
    private Long id;
    private String name;
    private List<CourseDto> courses;
}
