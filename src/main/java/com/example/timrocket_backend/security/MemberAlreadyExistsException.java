package com.example.timrocket_backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(String email) {
        super("The email " + email + " is already used in the system!");
    }
}
