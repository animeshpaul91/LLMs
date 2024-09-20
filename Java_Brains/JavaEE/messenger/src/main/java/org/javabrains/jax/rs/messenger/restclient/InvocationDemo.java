package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class InvocationDemo {

    private static Invocation prepareRequestForMessagesByYear(int year) {
        return WebTargetUtil.getMessagesWebTarget()
                .queryParam("year", year)
                .request(MediaType.APPLICATION_JSON)
                .buildGet();
    }

    public static void main(String[] args) {
        Invocation invocation = prepareRequestForMessagesByYear(2022);
        Response response = invocation.invoke();
        System.out.println(response.getStatus());
    }
}
