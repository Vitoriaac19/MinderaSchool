package com.example.mindera.mapper;

import com.example.mindera.dto.TeacherDto;
import com.example.mindera.model.Teacher;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);

    Teacher toModel(TeacherDto teacherDto);
}
