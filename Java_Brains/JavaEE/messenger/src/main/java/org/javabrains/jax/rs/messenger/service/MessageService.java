package org.javabrains.jax.rs.messenger.service;

import org.javabrains.jax.rs.messenger.database.DatabaseClass;
import org.javabrains.jax.rs.messenger.exception.DataNotFoundException;
import org.javabrains.jax.rs.messenger.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageService {
    private static Map<Long, Message> messages;

    public MessageService() {
        messages = DatabaseClass.getMessages();

        // Adding dummy messages for test
        Message m1 = new Message(1L, "Hello World!", "Animesh");
        Message m2 = new Message(2L, "Hello Jersey!", "Swagat");
        Message m3 = new Message(3L, "Hello JAX RS!", "Praveen");

        final var customerID1 = Optional.of("T0000000732");
        m1.setCustomerSid(customerID1);

        final var customerID2 = Optional.of("T0000000731");
        m2.setCustomerSid(customerID2);

        messages.put(m1.getId(), m1);
        messages.put(m2.getId(), m2);
        messages.put(m3.getId(), m3);

    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessagesForYear(int year) {
        final Calendar calendar = Calendar.getInstance();
        return messages.values()
                .stream()
                .filter(message -> {
                    Date dateCreated = message.getCreated();
                    calendar.setTime(dateCreated);
                    return calendar.get(Calendar.YEAR) == year;
                })
                .collect(Collectors.toList());
    }

    public List<Message> getAllMessagesPaginated(int start, int size) {
        List<Message> messagesList = new ArrayList<>(messages.values());
        return messagesList.subList(start, start + size);
    }

    public Message getMessage(long id) {
        Message message = messages.get(id);
        if (message == null) throw new DataNotFoundException("Message with ID: " + id + " not found");
        // this gets bubbled up to MessageResource and then to JAX.RS which looks at all exception mapper using @Provided Annotation
        // and finds a mapper with this type of exception
        return message;
    }

    public Message addMessage(Message message) {
        long messageId = messages.size() + 1;
        message.setId(messageId);
        setMessageTimestamp(message);
        messages.put(messageId, message);
        return message;
    }

    private void setMessageTimestamp(Message message) {
        message.setCreated(new Date());
    }

    public Message updateMessage(Message message) {
        if (message.getId() < 1) return null;
        messages.put(message.getId(), message);
        return message;
    }

    public Message deleteMessage(long id) {
        return messages.remove(id);
    }
}
