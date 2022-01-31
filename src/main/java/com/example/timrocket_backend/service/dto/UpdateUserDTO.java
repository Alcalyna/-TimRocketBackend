package com.example.timrocket_backend.service.dto;

import com.example.timrocket_backend.service.validation.DuplicateEmailConstraint;
import com.example.timrocket_backend.service.validation.EmailConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public record UpdateUserDTO(
        @NotBlank(message = "first name should be provided")
        @Size(max = 25, message = "first name can only be 25 characters long") String firstName,
        @NotBlank(message = "last name should be provided")
        @Size(max = 25, message = "last name can only be 25 characters long") String lastName,
        @EmailConstraint(message = "email should be provided, contain @ and . and be maximum 50 characters long")
        @DuplicateEmailConstraint(message = "this email address is already in use") String email,
        String role,
        String pictureUrl
        ) {

}
