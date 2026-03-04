package com.example.implementation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImplementationNotFoundException extends RuntimeException {

    public ImplementationNotFoundException(String message) {
        super(message);
    }
}
