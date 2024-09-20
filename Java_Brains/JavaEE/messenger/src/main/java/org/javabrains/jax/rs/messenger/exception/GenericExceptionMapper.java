package org.javabrains.jax.rs.messenger.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.javabrains.jax.rs.messenger.model.ErrorMessage;

// @Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> { // blanket catch for any kind of exception
    /**
     * Map an exception to a {@link Response}. Returning {@code null} results in a
     * {@link Response.Status#NO_CONTENT} response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(Throwable exception) { // this will catch everything. like a finally block
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "https://javabrains.koushik.org");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}
