package com.example.mindera.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "courses",
            cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "courses",
            cascade = CascadeType.ALL)
    private List<Teacher> teachers = new ArrayList<>();


}
