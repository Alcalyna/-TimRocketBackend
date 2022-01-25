package com.example.timrocket_backend.service.validation;

import com.example.timrocket_backend.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicateEmailValidator implements ConstraintValidator<DuplicateEmailConstraint, String> {
    private final UserRepository userRepository;

    public DuplicateEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(DuplicateEmailConstraint email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return userRepository.findByEmail(email) == null;
    }
}
