package com.catalogar.common.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<ApiError>(
                apiError,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handle(
            BadRequestException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<>(
                apiError,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handle(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        List<ValidationError> errors = e.getFieldErrors().stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Ops! Alguns dados não foram aceitos",
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                errors
        );

        return new ResponseEntity<ApiError>(
                apiError,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handle(
            ConstraintViolationException e,
            HttpServletRequest request
    ) {
        List<ValidationError> errors = e.getConstraintViolations().stream()
                .map(error -> new ValidationError(
                        error.getPropertyPath().toString(),
                        error.getMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Ops! Alguns dados não foram aceitos",
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                errors
        );

        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueFieldConflictException.class)
    public ResponseEntity<ApiError> handle(
            UniqueFieldConflictException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<ApiError>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handle(
            AuthenticationException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "E-mail e/ou senha incorretos",
                HttpStatus.UNAUTHORIZED.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<ApiError> handle(
            JwtException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Sessão inválida ou expirada. Por favor, realize um novo login",
                HttpStatus.UNAUTHORIZED.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(
            Exception e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now(),
                List.of()
        );

        return new ResponseEntity<ApiError>(
                apiError,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
