package com.example.mindera.service;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.exception.CourseException;
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

    private static void validateGetCourseDtoById(Long id) {
        if (id == null) {
            throw new CourseException("Course id is required");
        }
        if (id <= 0) {
            throw new CourseException("Course id must be greater than 0");
        }
    }

    private static void validateUpdateCourse(Long id, CourseDto courseDto) {
        if (id == null) {
            throw new CourseException("Course id is required");
        }
        if (id <= 0) {
            throw new CourseException("Course id must be greater than 0");
        }
        if (courseDto.getName() == null || courseDto.getName().isEmpty()) {
            throw new CourseException("Name is required");
        }
    }

    private static void validateDeleteCourse(Long id) {
        if (id == null) {
            throw new CourseException("Course id is required");
        }
        if (id <= 0) {
            throw new CourseException("Course id must be greater than 0");
        }
    }

    public CourseDto createCourse(CourseCreationDto courseCreationDto) {
        if (courseCreationDto.getName() == null || courseCreationDto.getName().isEmpty()) {
            throw new CourseException("Name is required");
        }
        Course course = CourseMapper.INSTANCE.toModel(courseCreationDto);
        courseRepository.save(course);
        return courseMapper.INSTANCE.toDto(course);
    }

    public CourseDto getCourseDtoById(Long id) {
        validateGetCourseDtoById(id);
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
        validateUpdateCourse(id, courseDto);
        Course course = courseRepository.findById(id).get();
        Course newCourse = CourseMapper.INSTANCE.toModel(courseDto);

        course.setName(newCourse.getName());
        courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);
    }

    public void deleteCourse(Long id) {
        validateDeleteCourse(id);
        courseRepository.deleteById(id);
    }

    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}
