package org.animesh.javabrains.rest.resource;

import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.animesh.javabrains.rest.media.CustomMediaType;

import java.util.Calendar;
import java.util.Date;

@Path("/test")
@Singleton
// this annotation will only create a single instance of MyResource class for every instance of a running application
public class MyResource {

    private int count;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testMethod() {
        count++;
        return "It Works! This method was called: " + count + " times";
    }

    @GET
    @Path("/date")
    @Produces(value = {MediaType.TEXT_PLAIN, CustomMediaType.SHORT_DATE})
    public Date getDate() {
        return Calendar.getInstance().getTime();
    }

    @GET
    @Path("/shortdate")
    @Produces(value = {MediaType.TEXT_PLAIN, CustomMediaType.SHORT_DATE})
    // jersey determines what media type the client specified in the "accept" header of the request and accordingly converts the date
    // using the appropriate MessageBodyWriter
    public Date getShortDate() {
        return Calendar.getInstance().getTime();
    }

}
