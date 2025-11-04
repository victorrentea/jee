package org.wildfly.examples;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//@Singleton
@Dependent
public class GreetingJpaRepo {

  @PersistenceContext(unitName = "primaryPU")
  EntityManager em;

  public Long jpaPersist(String message) {
    Greeting g = new Greeting(message);
    em.persist(g);
    em.flush();
    return g.getId();
  }

}
