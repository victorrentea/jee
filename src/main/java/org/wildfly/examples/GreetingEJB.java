package org.wildfly.examples;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import lombok.extern.java.Log;

@Stateless
@Interceptors(LoggerInterceptor.class)
@Log
public class GreetingEJB {

  @Inject
  GreetingJpaRepo greetingRepo;

  @Inject
  GreetingJdbcRepo greetingRepo2;

  @Inject
  @Any
  Event<GreetingEvent> greetingEvent;

  public String hello(String name) {
    // proxies work by creating a dynamic subclass of your EJB class ?
    // don;t work for static, final method, final classes or local method calls
    greetingEvent.fire(new GreetingEvent("HelloEvent " + name));
    return String.format("Hello '%s' EJB.", name);
  }

  public void localCalling() {
    hello("jdoe"); // not proxied
  }

  @Asynchronous
  public void async() {
    log.info("Async");
  }

  public record GreetingEvent(String message){}

  public void callingTwoRepos() {
    log.info("START");
    greetingRepo2.sqlInsert("updated message");
    greetingRepo.jpaPersist("new message");
    if (true) throw new RuntimeException("BUG🐞");
    log.info("END");
  }
}