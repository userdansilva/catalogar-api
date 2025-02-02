package com.catalogar.common.exception;

public class UniqueFieldConflictException extends RuntimeException {
    public UniqueFieldConflictException(String message) {
        super(message);
    }

}
