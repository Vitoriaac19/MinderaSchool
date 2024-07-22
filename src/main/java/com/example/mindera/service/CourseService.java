package com.example.mindera.service;

import com.example.mindera.dto.CourseDto;
import com.example.mindera.mapper.CourseMapper;
import com.example.mindera.model.Course;
import com.example.mindera.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public String createCourse(CourseDto courseDto) {
        Course course = courseMapper.INSTANCE.toModel(courseDto);
        courseRepository.save(course);
        return courseMapper.INSTANCE.toDto(course);

    }
}
