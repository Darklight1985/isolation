package ru.kolesnev.controller;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import ru.kolesnev.exception.CustomException;

@Provider
@RequiredArgsConstructor
@Slf4j
public class ControllerAdvice implements ExceptionMapper<GenericJDBCException> {

    @Override
    public Response toResponse(GenericJDBCException exception) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new CustomException(exception.getLocalizedMessage()))
                    .build();
    }
}
