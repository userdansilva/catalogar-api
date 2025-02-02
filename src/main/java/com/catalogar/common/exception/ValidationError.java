package com.catalogar.common.exception;

public record ValidationError(
        String field,
        String message
) {
}
