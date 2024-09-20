package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.javabrains.jax.rs.messenger.model.Message;

public class POSTRestApiClient {

    private static Message createMessageObject(long id, String message, String author) {
        return new Message(id, message, author);
    }

    private static void createPOSTRequest(WebTarget webTarget, Message message) {
        Response postResponse = webTarget.request()
                .post(Entity.json(message));

        if (postResponse.getStatus() != 201) {
            System.err.println("POST Request Failed");
            return;
        }

        Message createdMessage = postResponse.readEntity(Message.class);
        System.out.println(createdMessage);
    }

    private static void createPUTRequest(WebTarget webTarget, int messageId, Message updatedMessage) {
        Response updatedResponse = webTarget.path("/{messageId}")
                .resolveTemplate("messageId", messageId)
                .request()
                .put(Entity.json(updatedMessage));

        if (updatedResponse.getStatus() != 200) {
            System.err.println("PUT Request Failed");
            return;
        }

        Message messageAfterUpdate = updatedResponse.readEntity(Message.class);
        System.out.println(messageAfterUpdate);
    }

    public static void main(String[] args) {
        WebTarget messagesTarget = WebTargetUtil.getMessagesWebTarget();
        Message message1 = createMessageObject(3, "A hundred years to becoming a Superpower!", "Michael Godfrey");
        createPOSTRequest(messagesTarget, message1);
        Message message2 = createMessageObject(4, "Effective Java", "Joshua Bloch");
        createPOSTRequest(messagesTarget, message2);

        Message updatedMessage = createMessageObject(3, "A hundred years to becoming a Superpower!", "Michael Sifuentes");
        createPUTRequest(messagesTarget, 3, updatedMessage);
    }
}
