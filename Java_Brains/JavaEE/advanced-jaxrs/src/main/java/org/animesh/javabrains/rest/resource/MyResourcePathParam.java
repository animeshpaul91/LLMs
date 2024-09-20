package org.animesh.javabrains.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/{pathParam}/test")
// @Singleton This annotation creates the instance of the class before the request is made hence this endpoint will cause the app to break
// Without this, every new request creates an instance of this class
public class MyResourcePathParam {

    // field injection
    @PathParam("pathParam")
    private String pathParamField;
    @QueryParam("query")
    private String queryParamField;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testMethod() {
        return "Path Param Used: " + pathParamField + " and Query Param Field: " + queryParamField;
    }
}
