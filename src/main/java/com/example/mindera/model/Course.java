package com.example.mindera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
