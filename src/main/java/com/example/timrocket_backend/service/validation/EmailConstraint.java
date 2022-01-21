package com.example.timrocket_backend.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Invalid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
