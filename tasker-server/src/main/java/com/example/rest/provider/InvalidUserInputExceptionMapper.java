package com.example.rest.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidUserInputExceptionMapper implements ExceptionMapper<InvalidUserInputException> {
    public Response toResponse(InvalidUserInputException exception) {
        return Response.status(exception.getCode())
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}