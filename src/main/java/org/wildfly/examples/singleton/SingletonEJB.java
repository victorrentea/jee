package org.wildfly.examples.singleton;

import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import lombok.extern.java.Log;

@Log
@Singleton
@Lock(LockType.READ)
public class SingletonEJB {
  public void method() {
    log.info("START");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    log.info("END");
  }
}
