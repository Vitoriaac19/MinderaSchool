package com.example.mindera.service;

import com.example.mindera.dto.CourseDto;
import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.mapper.StudentMapper;
import com.example.mindera.model.Student;
import com.example.mindera.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    List<CourseDto> courses = List.of(new CourseDto("Java"), new CourseDto("C++"));
    List<StudentDto> studentDtos = List.of(new StudentDto(1L, "john"), new StudentDto(2L, "Jane"));

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private StudentService studentService;


    @Test
    void addStudent_shouldSucceed() {
        StudentCreationDto studentDto = new StudentCreationDto("John");
        studentService.addStudent(studentDto);
        Student student = StudentMapper.INSTANCE.toModel(studentDto);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getAllStudents_shouldSucceed() {
        List<Student> students = StudentMapper.INSTANCE.studentDtosToStudents(studentDtos);
        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> result = studentService.getAllStudents();
        assertEquals(studentDtos, result);
    }

    @Test
    void getById() {
        StudentDto studentDto = new StudentDto(1L, "John");
        Student student = StudentMapper.INSTANCE.toModel(studentDto);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDto result = studentService.getById(1L);
        assertEquals(studentDto, result);

    }

    @Test
    void updateStudentName() {


    }

 /*   @Test
    void addCourse_shouldSucceed() {
        Student student = new Student("John");

        Course course = new Course("Java");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseService.getCourseById(1L)).thenReturn(course);

        StudentDto expected = studentService.addCourse(1L, 1L);

        verify(studentRepository, times(1)).save(student);
        assertTrue(student.getCourses().contains(course));

    }

    @Test
    void delete_shouldSucceed() {
        Student student = new Student(1L, "John");

        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }*/
}