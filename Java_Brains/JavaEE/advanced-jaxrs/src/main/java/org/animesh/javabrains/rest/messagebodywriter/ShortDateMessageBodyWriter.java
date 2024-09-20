package org.animesh.javabrains.rest.messagebodywriter;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import org.animesh.javabrains.rest.media.CustomMediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

@Provider
@Produces(CustomMediaType.SHORT_DATE)
// this message body writer needs to be used when Date needs to be converted to CustomMediaType.SHORT_DATE in the response.
public class ShortDateMessageBodyWriter implements MessageBodyWriter<Date> {
    @Override
    public boolean isWriteable(Class<?> classType, Type type, Annotation[] annotations, MediaType mediaType) {
        return Date.class.isAssignableFrom(classType); // returns true for the java.util.Date class - return type of controller method
    }

    @Override
    public void writeTo(Date date, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String shortDate = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
        byte[] byteArray = shortDate.getBytes();
        outputStream.write(byteArray);
    }
}
