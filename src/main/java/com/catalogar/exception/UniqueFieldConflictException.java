package com.catalogar.exception;

public class UniqueFieldConflictException extends RuntimeException {
    public UniqueFieldConflictException(String message) {
        super(message);
    }

}
