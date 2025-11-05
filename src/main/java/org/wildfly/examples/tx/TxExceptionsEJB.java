package org.wildfly.examples.tx;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.wildfly.examples.GreetingJdbcRepo;

import java.sql.SQLException;

@Stateless
@Log
public class TxExceptionsEJB {
  @Inject
  GreetingJdbcRepo repo;

  public void checkedExceptionCommits() throws Exception {
    log.info("START tx method");
    repo.sqlInsert("step1a"); // commited
    if (true) throw new MyCheckedException("CHECKED🐞ModulabException");
    repo.sqlInsert("step2a"); // never reached
    log.info("Normal END");
  }

  public void runtimeExceptionRollsback() {
    log.info("START tx method");
    repo.sqlInsert("step1"); // rollbacked
    if (true) throw new RuntimeException("RUNTIME🐞");
    repo.sqlInsert("step2"); // never reached
    log.info("Normal END");
  }

  public void sqlExceptionRollsback() throws SQLException {
    log.info("START tx method");
    repo.sqlInsertThrowingSql("step1"); // rollbacked
    repo.sqlInsertThrowingSql("step2".repeat(1000)); // blows>rollbacks, despite it's a checked ex
    log.info("Normal END");
  }

  public static class MyCheckedException extends Exception {
    public MyCheckedException(String s) {
      super(s);
    }
  }

}
