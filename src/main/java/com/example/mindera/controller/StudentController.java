package com.example.mindera.controller;

import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mindera/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentDto addStudent(@RequestBody StudentCreationDto studentCreationDto) {
        return studentService.addStudent(studentCreationDto);
    }

    @GetMapping("/all")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getByIg(@PathVariable("id") Long id) {
        return studentService.getById(id);
    }

    @PatchMapping("/update-student-name/{id}")
    public StudentDto updateStudentName(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        return studentService.updateStudentName(id, studentDto);
    }

    @PutMapping("/{id}/course/{courseId}")
    public StudentDto addCourse(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return studentService.addCourse(id, courseId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }


}
