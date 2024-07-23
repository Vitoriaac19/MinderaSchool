package com.example.mindera.service;

import com.example.mindera.dto.TeacherDto;
import com.example.mindera.mapper.TeacherMapper;
import com.example.mindera.model.Teacher;
import com.example.mindera.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;
    TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    public TeacherDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.INSTANCE.toModel(teacherDto);
        teacherRepository.save(teacher);
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        return teacherMapper.INSTANCE.toDto(teacher);
    }

    public List<TeacherDto> getAllTeachers() {
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        List<Teacher> teacherList = teacherRepository.findAll();
        for (Teacher teacher : teacherList) {
            teacherDtoList.add(teacherMapper.INSTANCE.toDto(teacher));
        }
        return teacherDtoList;
    }

    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(id).get();
        Teacher newTeacher = new Teacher();
        newTeacher.setId(teacher.getId());
        newTeacher.setName(teacherDto.getName());
        return teacherMapper.INSTANCE.toDto(newTeacher);
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        teacherRepository.delete(teacher);
    }
}
