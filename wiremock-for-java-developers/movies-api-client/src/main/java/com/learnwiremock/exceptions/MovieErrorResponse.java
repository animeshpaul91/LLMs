package com.learnwiremock.exceptions;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class MovieErrorResponse extends RuntimeException {
    public MovieErrorResponse(String statusText, WebClientResponseException ex) {
        super(statusText, ex);
    }

    public MovieErrorResponse(final Throwable ex) {
        super(ex);
    }
}
