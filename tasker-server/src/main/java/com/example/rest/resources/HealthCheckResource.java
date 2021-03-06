package com.example.rest.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource {

    public HealthCheckResource() {
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHealth() {
        return Response.ok("Healthy\n").build();
    }

}