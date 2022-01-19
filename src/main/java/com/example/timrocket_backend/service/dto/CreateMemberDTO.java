
package com.example.timrocket_backend.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateMemberDTO(@NotBlank(message = "first name should be provided")
                              @Size(max = 25, message = "first name can only be 25 characters long") String firstName,
                              @NotBlank(message = "last name should be provided")
                              @Size(max = 25, message = "last name can only be 25 characters long") String lastName,
                              @NotBlank(message = "email should be provided")
                              @Size(max = 50, message = "email can only be 50 characters long")
                              @Email(message = "email should be valid (contain @ and .") String email,
                              @PasswordConstraint(message = "A password should be provided, contain at least 8 characters with minimum one capital letter and one number") String password,
                              @Size(max = 25, message = "company can only be 25 characters long") String company) {
}