package com.example.mindera.mapper;

import com.example.mindera.dto.CourseDto;
import com.example.mindera.dto.StudentDto;
import com.example.mindera.model.Course;
import com.example.mindera.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentConverter {


    public Student fromDtoToModel(StudentDto studentDto, List<Course> courses) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setCourses(courses);
        return student;
    }


    public StudentDto fromModelToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        List<Course> courses = student.getCourses();
        List<CourseDto> coursesDtos = courses.stream()
                .map(course -> {
                    CourseDto courseDto = new CourseDto();
                    courseDto.setId(course.getId());
                    courseDto.setName(course.getName());
                    return courseDto;
                }).toList();
        studentDto.setCourses(coursesDtos);
        return studentDto;
    }
}
