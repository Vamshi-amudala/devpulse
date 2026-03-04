package com.example.vote.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfVoteException extends RuntimeException {

    public SelfVoteException(String message) {
        super(message);
    }
}
