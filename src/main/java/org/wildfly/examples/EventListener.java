package org.wildfly.examples;

import jakarta.ejb.Singleton;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.wildfly.examples.GreetingEJB.GreetingEvent;

import static jakarta.enterprise.event.TransactionPhase.IN_PROGRESS;

@Log
@Singleton
public class EventListener {

  @Inject
  private GreetingJpaRepo greetingRepo;

  public void on(@Observes(during = IN_PROGRESS) GreetingEvent event) {
    log.info("Observed event: " + event);
    greetingRepo.jpaPersist(event.message());
  }
}
