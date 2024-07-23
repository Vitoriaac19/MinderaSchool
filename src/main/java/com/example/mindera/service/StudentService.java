package com.example.mindera.service;

import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.mapper.StudentConverter;
import com.example.mindera.mapper.StudentMapper;
import com.example.mindera.model.Course;
import com.example.mindera.model.Student;
import com.example.mindera.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private StudentMapper studentMapper;
    private StudentConverter studentConverter;
    private CourseService courseService;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentConverter studentConverter, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.studentConverter = studentConverter;
        this.courseService = courseService;
    }

    public StudentCreationDto addStudent(StudentCreationDto studentCreationDto) {
        Student student = studentMapper.INSTANCE.toModel(studentCreationDto);
        studentRepository.save(student);
        return studentMapper.INSTANCE.toCreationDto(student);
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentMapper.INSTANCE.studentsToStudentDtos(students);
    }

    public StudentDto getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return studentMapper.INSTANCE.toDto(student.get());
    }

    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id).get();
        Student newStudent = studentMapper.INSTANCE.toModel(studentDto);
        ;
        newStudent.setId(student.getId());
        newStudent.setName(student.getName());
        studentRepository.save(newStudent);
        return studentMapper.INSTANCE.toDto(newStudent);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.delete(student);
    }

    public StudentDto addCourse(Long id, Long courseId) {
        Student student = studentRepository.findById(id).get();
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
        studentRepository.save(student);
        return studentMapper.INSTANCE.toDto(student);
    }
}
