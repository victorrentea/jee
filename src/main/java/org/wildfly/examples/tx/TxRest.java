package org.wildfly.examples.tx;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class TxRest {
  @Inject
  TxExceptionsEJB txExceptionsEJB;
  @Inject
  TxLocalEJB txLocalEJB;

  @GET
  @Path("/tx/ex/checked")
  public String checked() throws Exception {
    txExceptionsEJB.checkedExceptionCommits();
    return "checked✅";
  }
  @GET
  @Path("/tx/ex/runtime")
  public String runtime() throws Exception {
    txExceptionsEJB.runtimeExceptionRollsback();
    return "runtime✅";
  }
  @GET
  @Path("/tx/ex/sql")
  public String sql() throws Exception {
    txExceptionsEJB.sqlExceptionRollsback();
    return "runtime✅";
  }

  @GET
  @Path("/tx/local/outer")
  public String outer() throws Exception {
    txLocalEJB.outer();
    return "outer✅";
  }

  @GET
  @Path("/tx/local/inner")
  public String inner() throws Exception {
    txLocalEJB.inner();
    return "inner✅";
  }
}
