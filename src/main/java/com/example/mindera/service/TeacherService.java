package com.example.mindera.service;

import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
import com.example.mindera.exception.CourseException;
import com.example.mindera.exception.TeacherException;
import com.example.mindera.mapper.TeacherMapper;
import com.example.mindera.model.Course;
import com.example.mindera.model.Teacher;
import com.example.mindera.repository.CourseRepository;
import com.example.mindera.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;
    CourseRepository courseRepository;
    CourseService courseService;
    TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, CourseService courseService) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    private static void validateGetTeacherById(Long id) {
        if (id == null) {
            throw new TeacherException("Teacher id cannot be null");
        }
        if (id <= 0) {
            throw new TeacherException("Teacher id must be greater than 0");
        }
    }

    private static void validateUpdateTeacher(Long id, TeacherDto teacherDto) {
        if (id == null) {
            throw new TeacherException("Teacher id cannot be null");
        }
        if (id <= 0) {
            throw new TeacherException("Teacher id must be greater than 0");
        }
        if (teacherDto.getName() == null || teacherDto.getName().isEmpty()) {
            throw new TeacherException("Teacher name cannot be empty");
        }
    }

    private static void validateAddCourseToTeacher(Long id, Long courseId) {
        if (id == null) {
            throw new TeacherException("Teacher id cannot be null");
        }
        if (id <= 0) {
            throw new TeacherException("Teacher id must be greater than 0");
        }
        if (courseId == null) {
            throw new CourseException("Course id cannot be null");
        }
        if (courseId <= 0) {
            throw new CourseException("Course id must be greater than 0");
        }
    }

    private static void validateDeleteTeacher(Long id) {
        if (id == null) {
            throw new TeacherException("Teacher id cannot be null");
        }
        if (id <= 0) {
            throw new TeacherException("Teacher id must be greater than 0");
        }
    }

    public TeacherDto createTeacher(TeacherCreationDto teacherCreationDto) {
        if (teacherCreationDto.getName() == null || teacherCreationDto.getName().isEmpty()) {
            throw new TeacherException("Teacher name cannot be empty");
        }
        Teacher teacher = teacherMapper.INSTANCE.toModel(teacherCreationDto);
        teacherRepository.save(teacher);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public TeacherDto getTeacherById(Long id) {
        validateGetTeacherById(id);
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacherMapper.INSTANCE.toDto(teacher.get());
    }

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherMapper.INSTANCE.teachersToTeacherDtos(teacherList);
    }

    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        validateUpdateTeacher(id, teacherDto);
        Teacher teacher = teacherRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Teacher newTeacher = teacherMapper.INSTANCE.toModel(teacherDto);
        teacher.setName(newTeacher.getName());
        teacherRepository.save(teacher);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public TeacherDto addCourseToTeacher(Long id, Long courseId) {
        validateAddCourseToTeacher(id, courseId);
        Teacher teacher = teacherRepository.findById(id).get();
        Course course = courseService.getCourseById(courseId);
        teacher.getCourses().add(course);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public void deleteTeacher(Long id) {
        validateDeleteTeacher(id);
        teacherRepository.deleteById(id);
    }

}
