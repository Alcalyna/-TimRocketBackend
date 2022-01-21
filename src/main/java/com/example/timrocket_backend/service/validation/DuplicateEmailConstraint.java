package com.example.timrocket_backend.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = DuplicateEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateEmailConstraint {
    String message() default "Duplicate email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
