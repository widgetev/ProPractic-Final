package com.example.propracticfinal.controller;

import com.example.propracticfinal.dto.ErrorResponse;
import com.example.propracticfinal.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerRequestException(RequestException exception) {
        return new ErrorResponse(exception.getHttpStatus().name(), exception.getMessage());
    }
}
