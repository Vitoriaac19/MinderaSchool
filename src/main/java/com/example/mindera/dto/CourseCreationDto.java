package com.example.mindera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseCreationDto {
    @NotNull
    @NotBlank
    private String name;

    public CourseCreationDto(String name) {
        this.name = name;
    }

    public CourseCreationDto() {
    }
}
