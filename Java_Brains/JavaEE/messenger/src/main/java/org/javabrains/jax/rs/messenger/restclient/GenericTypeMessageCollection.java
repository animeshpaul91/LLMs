package org.javabrains.jax.rs.messenger.restclient;

import jakarta.ws.rs.core.GenericType;
import org.javabrains.jax.rs.messenger.model.Message;

import java.util.List;

public class GenericTypeMessageCollection extends GenericType<List<Message>> {
}
