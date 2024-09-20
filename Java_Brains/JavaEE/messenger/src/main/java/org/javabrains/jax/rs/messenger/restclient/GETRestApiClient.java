package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.javabrains.jax.rs.messenger.model.Message;

public class GETRestApiClient {

    private static final String BASE_URL = "http://localhost:8080/messenger/webapi";

    private static String demonstrateRestClientStepByStep(Client client, String clientUrl) {
        WebTarget target = client.target(clientUrl);
        Builder builder = target.request(); // This builder can be used to make all HTTP requests (get, post, put, delete, patch)
        Response response = builder.get();

        Message message = response.readEntity(Message.class);
        return message.getMessage();
    }

    private static String restCallFluentAPIStyle(Client client, String clientUrl) {
        Response getResponse = client.target(clientUrl)
                .request(MediaType.APPLICATION_JSON) // tells the server that the client is expecting a JSON response.
                .get();// handy for debugging

        if (getResponse.getStatus() != 200) {
            System.err.println("GET Request Failed");
            return null;
        }
        return getResponse.readEntity(String.class); // String-ify-ing handy for debugging
    }

    private static Message getSingleMessageResponseObject(WebTarget webtarget, int messageId) {
        Response getResponse = webtarget
                .resolveTemplate("messageId", messageId) // resolves the pathVariable with the argument messageId
                .request(MediaType.APPLICATION_JSON) // tells the server that the client is expecting a JSON response.
                .get();

        if (getResponse.getStatus() != 200) {
            System.err.println("GET Request Failed");
            return null;
        }

        return getResponse.readEntity(Message.class);
    }

    public static void main(String[] args) {
        Client client = WebTargetUtil.getClient(); // with this client instance you can make multiple Rest-API calls
        String clientUrl = BASE_URL + "/messages/1";
        //System.out.println(demonstrateRestClientStepByStep(client, clientUrl));
        String jsonResponse = restCallFluentAPIStyle(client, clientUrl);
        //System.out.println(jsonResponse);

        // Best Practices
        WebTarget messagesTarget = WebTargetUtil.getMessagesWebTarget();
        WebTarget singleMessageTarget = messagesTarget.path("/{messageId}");
        Message message1 = getSingleMessageResponseObject(singleMessageTarget, 1);
        System.out.println(message1.getMessage());
        Message message2 = getSingleMessageResponseObject(singleMessageTarget, 2);
        System.out.println(message2.getMessage());
    }
}
