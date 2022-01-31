package com.example.timrocket_backend.exception;

public class EditNotAllowedException extends RuntimeException {
    public EditNotAllowedException() {
        super("You are not allowed to edit this profile!");
    }
}
