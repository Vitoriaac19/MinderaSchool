package com.example.mindera.controller;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/courses")
@Tag(name = "Mindera API")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Create new Course", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseCreationDto courseCreationDto) {
        return new ResponseEntity<>(courseService.createCourse(courseCreationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Course by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.getCourseDtoById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all Courses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/all", consumes = "application/json")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @Operation(summary = "Update Course", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto courseDto) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete Course", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
