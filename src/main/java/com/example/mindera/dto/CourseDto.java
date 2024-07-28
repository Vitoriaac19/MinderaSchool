package com.example.mindera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDto {
    private Long id;
    private String name;

    public CourseDto(String name) {
        this.name = name;
    }

    public CourseDto() {
    }
}
