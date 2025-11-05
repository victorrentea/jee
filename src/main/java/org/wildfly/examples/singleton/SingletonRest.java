package org.wildfly.examples.singleton;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class SingletonRest {
  @Inject
  SingletonEJB singletonEJB;
  @GET
  @Path("/singleton")
  public String checked() throws Exception {
    singletonEJB.method();
    return "done✅";
  }
}
