package org.javabrains.jax.rs.messenger.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/injectdemo") // All controller methods in this class will have this prefix
@Consumes(MediaType.TEXT_PLAIN) // This represents the body of the request
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This is the return type to the HTTP reque
public class InjectDemoResource { // This class is just placed to demonstrate a few Param Annotations

    // Matrix Params - send from client using ; instead of ?
    @GET
    @Path("annotations")
    public String getParamUsingAnnotations(@MatrixParam("param") String matrixParam,
                                           @HeaderParam("customHeaderValue") String header,
                                           @CookieParam("name") String cookie) {
        return "Matrix Param: " + matrixParam + " and Header Param: " + header + " Cookie: " + cookie;
    }

    @GET
    @Path("context")
    public String getParamUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        String path = uriInfo.getAbsolutePath().toString();
        String headerValue = headers.getHeaderString("headerKey");
        return "Path: " + path + " Header Value: " + headerValue;
    }
}
