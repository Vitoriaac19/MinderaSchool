package com.example.mindera.service;

import com.example.mindera.dto.StudentDto;
import com.example.mindera.mapper.StudentMapper;
import com.example.mindera.model.Student;
import com.example.mindera.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto addStudent(StudentDto studentDto) {
        Student student = studentMapper.INSTANCE.toModel(studentDto);
        studentRepository.save(student);
        return studentMapper.INSTANCE.toDto(student);
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.INSTANCE.studentsToStudentDtos(students);
    }
}
