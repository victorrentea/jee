package org.wildfly.examples;

import jakarta.ejb.*;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import lombok.extern.java.Log;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Stateful // TERRIBLE
//@MessageDriven // JMS


@Stateless // one instance per .war/app
//@Stateless // = pool of X(7) instances, = the best thing in 2005-2010
// during a method call in one instance, that instance is not invoked again,
      // WHY?!?!? ONLY NEEDED WHEN...
@Interceptors(LoggerInterceptor.class)
@Log
public class GreetingEJB {
  @Inject
  GreetingFriendEJB friend;
  @Inject
  GreetingJpaRepo greetingRepo;
  @Inject
  GreetingJdbcRepo greetingJdbcRepo;
  @Inject
  GreetingJdbc2Repo greetingJdbc2Repo;

  List<String> errorsInTheCurrentRequest = new ArrayList<>(); // works in @Stateless, not in @Singleton(race) MADNESS, 2005' style:

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

  public void backCall() {
    log.info("I can have cyclic dependencies between EJBs!!");
  }

  public record GreetingEvent(String message){}



  public void callingTwoRepos() throws SQLException, NamingException {
    log.info("START");
    greetingJdbcRepo.sqlInsert("updated message1");
    friend.callingOneRepo();
//    greetingJdbc2Repo.sqlInsert("updated message2");
    if (true) throw new RuntimeException("RUNTIME causing rollback🐞");
    log.info("END");
  }
}