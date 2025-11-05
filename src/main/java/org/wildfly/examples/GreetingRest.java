package org.wildfly.examples;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.sql.SQLException;

@Path("/")
@Log
public class GreetingRest {

  @Inject
  private GreetingEJB service;

  public void on(@Observes GreetingEJB.GreetingEvent event) {
    log.info("Observed: " + event);
  }

  @GET
  @Path("/to/{name}")
  @Produces(MediaType.TEXT_PLAIN)
  public Response sayHello(final @PathParam("name") String name) {
    String response = service.hello(name);

    return Response.ok(response).build();
  }

  @GET
  @Path("/async")
  @Produces(MediaType.TEXT_PLAIN)
  public Response async() {
    service.async();

    return Response.ok("Done").build();
  }

}
