package com.catalogar.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<Url, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) return true;

        String URL_REGEX = "^((https?|ftp)://)" + // Scheme (http, https, ftp)
                "([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})" + // Domain
                "(/[^\\s?#]*)?" + // Path (optional)
                "(\\?[^\\s#]*)?" + // Query (optional)
                "(#\\S*)?"; // Fragment (optional)

        return s.matches(URL_REGEX);
    }
}
