package org.javabrains.jax.rs.messenger.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.javabrains.jax.rs.messenger.model.ErrorMessage;

public class WebApplicationExceptionUtil {
    public static void throwWebApplicationExceptionWithNotFoundStatus() {
        ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "https://javabrains.koushik.org");
        Response response = Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
        throw new WebApplicationException(response);
    }

    public static void throwNotFoundException() {
        ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "https://javabrains.koushik.org");
        Response response = Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
        throw new NotFoundException(response);
    }
}
