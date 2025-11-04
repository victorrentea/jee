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

@Path("/")
@Log
public class GreetingRestApi {

  @Inject
  private GreetingEJB service;

  public void on(@Observes GreetingEJB.GreetingEvent event) {
    log.info("Observed: " + event);
  }

  @GET
  @Path("/{name}")
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

  @GET
  @Path("/tx")
  @Produces(MediaType.TEXT_PLAIN)
  public Response tx() {
    service.callingTwoRepos();

    return Response.ok("Done").build();
  }
}
