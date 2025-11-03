package org.wildfly.examples;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import lombok.extern.java.Log;

@Stateless
@Interceptors(Interceptor.class)
@Log
public class MyEJB {

  @Inject
  @Any
  Event<HelloEvent> helloEvent;

  public String hello(String name) {
    helloEvent.fire(new HelloEvent("HelloEvent " + name));
    return String.format("Hello '%s' EJB.", name);
  }

  @Asynchronous
  public void async() {
    log.info("Async");
  }

  record HelloEvent(String message){}
}