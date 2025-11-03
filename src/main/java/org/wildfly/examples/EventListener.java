package org.wildfly.examples;


import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import lombok.extern.java.Log;
import org.wildfly.examples.MyEJB.HelloEvent;

import java.util.logging.Logger;

import static jakarta.enterprise.event.TransactionPhase.AFTER_SUCCESS;

@Singleton
@Log
public class EventListener {

//  @Asynchronous
  public void on(@Observes(during = AFTER_SUCCESS) HelloEvent event) {
    log.info("Observed: " + event);
  }
}
