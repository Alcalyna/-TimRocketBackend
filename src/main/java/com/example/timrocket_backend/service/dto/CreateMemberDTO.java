package com.example.timrocket_backend.service.dto;

import com.example.timrocket_backend.service.validation.DuplicateEmailConstraint;
import com.example.timrocket_backend.service.validation.EmailConstraint;
import com.example.timrocket_backend.service.validation.PasswordConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateMemberDTO(@NotBlank(message = "first name should be provided")
                              @Size(max = 25, message = "first name can only be 25 characters long") String firstName,
                              @NotBlank(message = "last name should be provided")
                              @Size(max = 25, message = "last name can only be 25 characters long") String lastName,
                              @EmailConstraint(message = "email should be provided, contain @ and . and be maximum 50 characters long")
                              @DuplicateEmailConstraint(message = "this email address is already in use") String email,
                              @PasswordConstraint(message = "A password should be provided, contain at least 8 characters with minimum one capital letter and one number") String password,
                              @Size(max = 25, message = "company can only be 25 characters long") String company) {
}
