package com.catalogar.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HexadecimalValidator.class)
public @interface Hexadecimal {
    String message() default "deve ser hexadecimal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
