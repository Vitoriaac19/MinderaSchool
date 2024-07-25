package com.example.mindera.service;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.mapper.CourseMapper;
import com.example.mindera.model.Course;
import com.example.mindera.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public CourseDto createCourse(CourseCreationDto courseCreationDto) {
        Course course = CourseMapper.INSTANCE.toModel(courseCreationDto);
        courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);
    }

    public CourseDto getCourseDyoById(Long id) {
        Course course = courseRepository.findById(id).get();
        return CourseMapper.INSTANCE.toDto(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public List<CourseDto> getAllCourses() {
        List<CourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            courseDtos.add(CourseMapper.INSTANCE.toDto(course));
        }
        return courseDtos;
    }

    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).get();
        Course newCourse = CourseMapper.INSTANCE.toModel(courseDto);

        course.setName(newCourse.getName());
        courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
