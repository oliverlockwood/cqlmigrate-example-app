package com.oliverlockwood.cqlmigrate.example.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/example")
public class ExampleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEncryptionKeys() throws SecurityException, IOException {
        Response.ResponseBuilder response = Response.status(Response.Status.OK).entity("{\"blah\":\"blah2\"}");
        return response.build();
    }
}