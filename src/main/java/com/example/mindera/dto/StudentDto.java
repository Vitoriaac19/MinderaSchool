package com.example.mindera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDto {
    private Long id;
    private String name;
    private List<CourseDto> courses;

    public StudentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentDto() {
    }
}
