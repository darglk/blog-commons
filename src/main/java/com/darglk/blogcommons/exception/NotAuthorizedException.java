package com.darglk.blogcommons.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NotAuthorizedException extends CustomException {
    public NotAuthorizedException() {
        super("Unauthorized", HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public List<ErrorResponse> serializeErrors() {
        return List.of(new ErrorResponse("Not authorized", null));
    }
}
