package com.catalogar.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return true;

        String FILE_TYPE_REGEX = "^(WEBP|JPG|PNG|SVG)$";

        return value.matches(FILE_TYPE_REGEX);
    }
}
