package org.wildfly.examples.tx;

import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.wildfly.examples.GreetingJdbcRepo;

import static jakarta.ejb.TransactionAttributeType.REQUIRES_NEW;

@Singleton
@Log
public class TxLocalEJB {
  @Inject
  GreetingJdbcRepo repo;

  public void outer() {
    log.info("START");
    inner(); // local call
    repo.sqlInsert("step3");
    throw new RuntimeException("Expected to only rollback step3, as 1+2 should've already commited ❌");
  }

  @TransactionAttribute(REQUIRES_NEW)
  public void inner() {
    repo.sqlInsert("step1");
    repo.sqlInsert("step2");
  }
}
