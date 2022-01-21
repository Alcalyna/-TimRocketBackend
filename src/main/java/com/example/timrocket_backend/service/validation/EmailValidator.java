package com.example.timrocket_backend.service.validation;

import com.example.timrocket_backend.repository.MemberRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {


    @Override
    public void initialize(EmailConstraint email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return email !=null &&
                email.trim().length() !=0 &&
                email.length() <= 50 &&
                email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
}
