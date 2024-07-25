package com.example.mindera.controller;

import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentCreationDto studentCreationDto) {
        return new ResponseEntity<>(studentService.addStudent(studentCreationDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getByIg(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
    }

    @PatchMapping("/update-student-name/{id}")
    public ResponseEntity<StudentDto> updateStudentName(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(studentService.updateStudentName(id, studentDto), HttpStatus.OK);
    }

    @PutMapping("/{id}/course/{courseId}")
    public ResponseEntity<StudentDto> addCourse(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(studentService.addCourse(id, courseId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
