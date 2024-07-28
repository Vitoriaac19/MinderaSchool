package com.example.mindera.controller;

import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.service.StudentService;
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
@RequestMapping(path = "/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Create new Student", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody StudentCreationDto studentCreationDto) {
        return new ResponseEntity<>(studentService.addStudent(studentCreationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Students", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/all", consumes = "application/json")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @Operation(summary = "Get Student by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<StudentDto> getByIg(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update Students Name", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping(path = "/update-student-name/{id}", produces = "application/json")
    public ResponseEntity<StudentDto> updateStudentName(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(studentService.updateStudentName(id, studentDto), HttpStatus.OK);
    }

    @Operation(summary = "Add Course to Student", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(path = "/{id}/course/{courseId}", produces = "application/json")
    public ResponseEntity<StudentDto> addCourse(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(studentService.addCourse(id, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Student", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
