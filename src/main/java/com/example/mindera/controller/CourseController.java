package com.example.mindera.controller;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mindera/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseCreationDto createCourse(@RequestBody CourseCreationDto courseCreationDto) {
        return courseService.createCourse(courseCreationDto);
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseDyoById(id);
    }

    @GetMapping("/getAll")
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PutMapping("/update/{id}")
    public CourseDto updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto courseDto) {
        return courseService.updateCourse(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
    }

}
