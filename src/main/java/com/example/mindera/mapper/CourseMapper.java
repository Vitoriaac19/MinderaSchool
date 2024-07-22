package com.example.mindera.mapper;

import com.example.mindera.dto.CourseDto;
import com.example.mindera.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto toDto(Course course);

    Course toModel(CourseDto courseDto);

    List<CourseDto> coursesToCourseDtos(List<Course> courses);

    List<Course> courseDtosToCourses(List<CourseDto> coursesDto);
}
