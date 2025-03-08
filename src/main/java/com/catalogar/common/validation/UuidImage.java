package com.catalogar.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UuidImageValidator.class)
public @interface UuidImage {
    String message() default "nome e/ou tipo de imagem inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
