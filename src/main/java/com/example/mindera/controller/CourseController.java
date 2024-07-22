package com.example.mindera.controller;

import com.example.mindera.dto.CourseDto;
import com.example.mindera.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/mindera/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

}
