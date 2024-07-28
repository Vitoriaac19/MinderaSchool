package com.example.mindera.service;

import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.exception.StudentException;
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
    private CourseService courseService;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    private static void validateGetById(Long id) {
        if (id == null) {
            throw new StudentException("Student id is required");
        }
        if (id <= 0) {
            throw new StudentException("Student id must be greater than 0");
        }
    }

    private static void validateUpdateStudentName(Long id, StudentDto studentDto) {
        if (id == null) {
            throw new StudentException("Student id is required");
        }
        if (id <= 0) {
            throw new StudentException("Student id must be greater than 0");
        }
        if (studentDto.getName() == null || studentDto.getName().isEmpty()) {
            throw new StudentException("Name is required");
        }
    }

    private static void validateAddCourse(Long id, Long courseId) {
        if (id == null) {
            throw new StudentException("Student id is required");
        }
        if (id <= 0) {
            throw new StudentException("Student id must be greater than 0");
        }
        if (courseId == null) {
            throw new StudentException("Course id is required");
        }
        if (courseId <= 0) {
            throw new StudentException("Course id must be greater than 0");
        }
    }

    private static void validateDeleteStudent(Long id) {
        if (id == null) {
            throw new StudentException("Student id is required");
        }
        if (id <= 0) {
            throw new StudentException("Student id must be greater than 0");
        }
    }

    public StudentDto addStudent(StudentCreationDto studentCreationDto) {
        if (studentCreationDto.getName() == null || studentCreationDto.getName().isEmpty()) {
            throw new StudentException("Name is required");
        }
        Student student = studentMapper.INSTANCE.toModel(studentCreationDto);
        studentRepository.save(student);
        return studentMapper.INSTANCE.toDto(student);
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentMapper.INSTANCE.studentsToStudentDtos(students);
    }

    public StudentDto getById(Long id) {
        validateGetById(id);
        Optional<Student> student = studentRepository.findById(id);
        return studentMapper.INSTANCE.toDto(student.get());
    }

    public StudentDto updateStudentName(Long id, StudentDto studentDto) {
        validateUpdateStudentName(id, studentDto);
        Student student = studentRepository.findById(id).get();
        Student newStudent = StudentMapper.INSTANCE.toModel(studentDto);
        student.setName(newStudent.getName());
        studentRepository.save(student);
        return StudentMapper.INSTANCE.toDto(student);
    }

    public StudentDto addCourse(Long id, Long courseId) {
        validateAddCourse(id, courseId);
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentException("Student not found"));
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
        studentRepository.save(student);
        return studentMapper.INSTANCE.toDto(student);
    }

    public void deleteStudent(Long id) {
        validateDeleteStudent(id);
        studentRepository.deleteById(id);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}
