package com.darglk.blogcommons.exception;

import com.darglk.ticketingcommons.model.UserLoginRequestModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class InvalidCredentialsException extends AuthenticationException {
    private int statusCode = HttpStatus.BAD_REQUEST.value();
    private Set<ConstraintViolation<UserLoginRequestModel>> validationResult;
    public InvalidCredentialsException(String msg, Set<ConstraintViolation<UserLoginRequestModel>> validationResult) {
        super(msg);
        this.validationResult = validationResult;
    }

    public List<ErrorResponse> serializeErrors() {
        return this.validationResult.stream()
                .map(result -> new ErrorResponse(result.getMessage(), result.getPropertyPath().toString()))
                .collect(Collectors.toList());
    }
}
