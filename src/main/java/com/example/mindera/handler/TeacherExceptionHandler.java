package com.example.mindera.handler;

import com.example.mindera.dto.ErrorDto;
import com.example.mindera.exception.TeacherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeacherExceptionHandler {
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    @ExceptionHandler(TeacherException.class)
    public ErrorDto handleException(TeacherException e) {
        return new ErrorDto(e.getMessage());
    }
}
