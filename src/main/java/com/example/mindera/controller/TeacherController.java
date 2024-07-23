package com.example.mindera.controller;

import com.example.mindera.dto.TeacherDto;
import com.example.mindera.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mindera/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        return teacherService.createTeacher(teacherDto);
    }

    @GetMapping("/get/{id}")
    public TeacherDto getTeacherById(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/getAll")
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/update/{id}")
    public TeacherDto updateTeacher(@PathVariable("id") Long id, @RequestBody TeacherDto teacherDto) {
        return teacherService.updateTeacher(id, teacherDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}
