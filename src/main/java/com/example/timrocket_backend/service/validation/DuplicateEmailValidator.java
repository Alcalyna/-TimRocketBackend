package com.example.timrocket_backend.service.validation;

import com.example.timrocket_backend.repository.MemberRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicateEmailValidator implements ConstraintValidator<DuplicateEmailConstraint, String> {
    private final MemberRepository memberRepository;

    public DuplicateEmailValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void initialize(DuplicateEmailConstraint email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return memberRepository.findByEmail(email) == null;
    }
}
