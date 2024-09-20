package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class WebTargetUtil {
    private static final String BASE_URL = "http://localhost:8080/messenger/webapi";

    public static Client getClient() {
        return ClientBuilder.newClient(); // with this client instance you can make multiple Rest-API calls
    }

    public static WebTarget getMessagesWebTarget() {
        Client client = getClient();
        WebTarget baseTarget = client.target(BASE_URL);
        return baseTarget.path("/messages");
    }

    public static WebTarget getProfilesWebTarget() {
        Client client = getClient();
        WebTarget baseTarget = client.target(BASE_URL);
        return baseTarget.path("/profiles");
    }
}
