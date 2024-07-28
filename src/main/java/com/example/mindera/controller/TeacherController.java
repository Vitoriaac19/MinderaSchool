package com.example.mindera.controller;

import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
import com.example.mindera.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Create a new teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<TeacherDto> createTeacher(@Valid @RequestBody TeacherCreationDto teacherCreationDto) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherCreationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a teacher by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getTeacherById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/all", consumes = "application/json")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

    @Operation(summary = "Add a course to a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(path = "/{id}/course/{courseId}", produces = "application/json")
    public ResponseEntity<TeacherDto> addCourseToTeacher(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(teacherService.addCourseToTeacher(id, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Update teachers name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable("id") Long id, @RequestBody TeacherDto teacherDto) {
        return new ResponseEntity<>(teacherService.updateTeacher(id, teacherDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete a teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
