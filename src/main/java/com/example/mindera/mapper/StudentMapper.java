package com.example.mindera.mapper;

import com.example.mindera.dto.StudentDto;
import com.example.mindera.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDto toDto(Student student);

    Student toModel(StudentDto studentDto);

    List<StudentDto> studentsToStudentDtos(List<Student> students);

    List<Student> studentDtosToStudents(List<StudentDto> studentsDto);
}