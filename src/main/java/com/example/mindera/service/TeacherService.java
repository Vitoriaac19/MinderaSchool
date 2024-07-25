package com.example.mindera.service;

import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
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


    public TeacherDto createTeacher(TeacherCreationDto teacherCreationDto) {
        Teacher teacher = teacherMapper.INSTANCE.toModel(teacherCreationDto);
        teacherRepository.save(teacher);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public TeacherDto getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacherMapper.INSTANCE.toDto(teacher.get());
    }

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherMapper.INSTANCE.teachersToTeacherDtos(teacherList);
    }

    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Teacher newTeacher = teacherMapper.INSTANCE.toModel(teacherDto);
        teacher.setName(newTeacher.getName());
        teacherRepository.save(teacher);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public TeacherDto addCourseToTeacher(Long id, Long courseId) {
        Teacher teacher = teacherRepository.findById(id).get();
        Course course = courseService.getCourseById(courseId);
        teacher.getCourses().add(course);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

}
