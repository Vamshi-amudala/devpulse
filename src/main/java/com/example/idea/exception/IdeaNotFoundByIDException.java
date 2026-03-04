package com.example.idea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdeaNotFoundByIDException extends RuntimeException {

    public IdeaNotFoundByIDException(String message) {
        super(message);
    }

    public IdeaNotFoundByIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
