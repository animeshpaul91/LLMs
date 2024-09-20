package org.animesh.javabrains.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.animesh.javabrains.rest.model.MyDate;

@Path("/date/{dateString}")
public class DateResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getRequestedDate(@PathParam("dateString") MyDate dateString) {
        return "Got: " + dateString.toString();
    }
}
