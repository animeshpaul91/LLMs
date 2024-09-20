package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.core.MediaType;
import org.javabrains.jax.rs.messenger.model.Message;

import java.util.List;

public class GenericDemo {
    private static List<Message> invokeCollectionsURI(int year) {
        GenericTypeMessageCollection genericTypeMessageCollection = new GenericTypeMessageCollection();
        return WebTargetUtil.getMessagesWebTarget()
                .queryParam("year", year)
                .request(MediaType.APPLICATION_JSON)
                .get(genericTypeMessageCollection);
    }

    public static void main(String[] args) {
        List<Message> allMessagesForYear = invokeCollectionsURI(2022);
        System.out.println(allMessagesForYear);
    }
}
