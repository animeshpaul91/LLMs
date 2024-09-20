package org.animesh.javabrains.rest.messagebodywriter;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

@Provider
@Produces(MediaType.TEXT_PLAIN)
// this message body writer needs to be used when Date needs to be converted to TEXT_PLAIN in the response.
public class DateMessageBodyWriter implements MessageBodyWriter<Date> {
    @Override
    public boolean isWriteable(Class<?> classType, Type type, Annotation[] annotations, MediaType mediaType) {
        return Date.class.isAssignableFrom(classType); // returns true for the java.util.Date class - return type of controller method
    }

    @Override
    public void writeTo(Date date, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        byte[] byteArray = date.toString().getBytes();
        outputStream.write(byteArray);
    }
}
