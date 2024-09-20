package org.animesh.javabrains.rest.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class PoweredByResponseFilter implements ContainerResponseFilter { // this is a response filter
    // JAX RS will call this filter method when the response is going to be sent
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("X-Powered-By", "Java-Brains");
    }
}
