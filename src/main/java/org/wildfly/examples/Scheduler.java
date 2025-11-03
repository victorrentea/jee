package org.wildfly.examples;

import jakarta.ejb.Schedule;
import lombok.extern.java.Log;

import java.util.logging.Logger;

//@Singleton
@Log
public class Scheduler {

  @Schedule(second = "*/5", hour = "*", minute = "*")
  public void poll() {
    log.info("Sched");
  }
}
