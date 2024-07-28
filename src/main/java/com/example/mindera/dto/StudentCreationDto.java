package com.example.mindera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentCreationDto {
    @NotNull
    @NotBlank
    private String name;

    public StudentCreationDto() {
    }

    public StudentCreationDto(String name) {
        this.name = name;
    }
}
