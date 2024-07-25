package com.example.mindera.handler;

import com.example.mindera.dto.ErrorDto;
import com.example.mindera.exception.CourseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CourseExceptionHandler {
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    @ExceptionHandler(CourseException.class)
    public ErrorDto handleException(CourseException e) {
        return new ErrorDto(e.getMessage());
    }
}
