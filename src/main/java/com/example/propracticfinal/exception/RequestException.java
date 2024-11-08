package com.example.propracticfinal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public RequestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST ;
        this.message = message;
    }

    public RequestException(HttpStatus type, String message) {
        super(message);
        this.httpStatus = type;
        this.message = message;
    }
}

