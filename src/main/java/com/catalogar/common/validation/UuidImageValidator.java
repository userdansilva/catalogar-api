package com.catalogar.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UuidImageValidator implements ConstraintValidator<UuidImage, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) return true;

        String UUID_IMAGE_REGEX = "^[0-9a-fA-F]{8}-" + // First 8 hex characters
                "[0-9a-fA-F]{4}-" + // Next 4 hex characters
                "[0-9a-fA-F]{4}-" + // Next 4 hex characters
                "[0-9a-fA-F]{4}-" + // Next 4 hex characters
                "[0-9a-fA-F]{12}\\." + // Last 12 hex characters + dot
                "(jpg|jpeg|webp|svg)$"; // Allowed image extensions

        return s.matches(UUID_IMAGE_REGEX);
    }
}
