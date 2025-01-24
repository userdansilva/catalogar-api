package com.catalogar.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<Slug, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return true;

        String SLUG_REGEX = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
        return value.matches(SLUG_REGEX);
    }
}
