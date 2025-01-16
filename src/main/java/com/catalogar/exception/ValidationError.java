package com.catalogar.exception;

public record ValidationError(
        String field,
        String message
) {
}
