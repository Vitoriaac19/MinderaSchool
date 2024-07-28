package com.example.mindera.controller;

import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
import com.example.mindera.service.TeacherService;
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

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@Valid @RequestBody TeacherCreationDto teacherCreationDto) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherCreationDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getTeacherById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

    @PutMapping("/{id}/course/{courseId}")
    public ResponseEntity<TeacherDto> addCourseToTeacher(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(teacherService.addCourseToTeacher(id, courseId), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable("id") Long id, @RequestBody TeacherDto teacherDto) {
        return new ResponseEntity<>(teacherService.updateTeacher(id, teacherDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
