package org.wildfly.examples.jobs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@Singleton
@Startup
public class JobRegistry {
  @Inject
  Instance<Job> allJobs;

  @PostConstruct
  public void listAll() {
    System.out.println("List:" + allJobs.stream().toList());
  }
}
