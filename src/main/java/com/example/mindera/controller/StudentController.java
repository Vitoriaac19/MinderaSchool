package com.example.mindera.controller;

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

    @PostMapping("/add")
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    /*
     @PostMapping
    public CarDto addNewCar(@RequestBody CarDto carDto) {
        return carService.addNewCar(carDto);
    }
     */

    @GetMapping("/getAll")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }


}
