package com.catalogar.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private List<String> errorMessages;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, List<ObjectError> errors) {
        this(message);

        this.errorMessages = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
