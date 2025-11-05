package org.wildfly.examples;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import lombok.extern.java.Log;

import java.sql.SQLException;

@Stateless
@Log
public class GreetingFriendEJB {
  @Inject
  GreetingJdbc2Repo greetingJdbc2Repo;
  @Inject
  GreetingEJB greetingCycle;

  public void callingOneRepo() throws SQLException {
    greetingCycle.backCall();
    greetingJdbc2Repo.sqlInsert("updated message2");
  }
}