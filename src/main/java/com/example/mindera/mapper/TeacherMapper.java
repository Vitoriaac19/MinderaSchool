package com.example.mindera.mapper;

import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
import com.example.mindera.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDto toDto(Teacher teacher);

    TeacherCreationDto toCreationDto(Teacher teacher);

    Teacher toModel(TeacherDto teacherDto);

    Teacher toModel(TeacherCreationDto teacherCreationDto);

    List<TeacherDto> teachersToTeacherDtos(List<Teacher> teacherList);

    List<Teacher> toModel(List<TeacherDto> teacherDtoList);
}
