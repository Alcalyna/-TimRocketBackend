package com.example.timrocket_backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(String memberName) {
        super("Member" + memberName + " already exists in the system!!!");
    }
}
