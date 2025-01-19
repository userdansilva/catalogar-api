package com.catalogar.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexadecimalValidator implements ConstraintValidator<Hexadecimal, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.isEmpty()) return true;

        String HEXADECIMAL_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        return value.matches(HEXADECIMAL_REGEX);
    }
}
