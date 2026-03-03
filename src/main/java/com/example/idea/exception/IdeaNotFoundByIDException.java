package com.example.idea.exception;

public class IdeaNotFoundByIDException extends RuntimeException {

    public IdeaNotFoundByIDException(String message) {
        super(message);
    }

    public IdeaNotFoundByIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
