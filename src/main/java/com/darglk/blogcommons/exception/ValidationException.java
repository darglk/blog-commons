package com.darglk.blogcommons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends CustomException {
    private final Errors errors;
    public ValidationException(Errors errors) {
        super("validation error", HttpStatus.BAD_REQUEST.value());
        this.errors = errors;
    }

    @Override
    public List<ErrorResponse> serializeErrors() {
        return errors.getFieldErrors().stream()
                .map(error -> new ErrorResponse(error.getDefaultMessage(), error.getField()))
                .collect(Collectors.toList());
    }
}
