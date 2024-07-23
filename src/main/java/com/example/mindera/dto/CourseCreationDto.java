package com.example.mindera.dto;

import lombok.Data;

@Data
public class CourseCreationDto {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
