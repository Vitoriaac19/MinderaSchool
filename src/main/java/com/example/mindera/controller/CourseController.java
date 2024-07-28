package com.example.mindera.controller;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseCreationDto courseCreationDto) {
        return new ResponseEntity<>(courseService.createCourse(courseCreationDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.getCourseDtoById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto courseDto) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
