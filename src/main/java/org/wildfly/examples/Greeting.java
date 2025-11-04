package org.wildfly.examples;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;

import static lombok.AccessLevel.NONE;

@Entity
@Data
public class Greeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String message;

  @Setter(NONE)
  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  protected Greeting() { /* JPA */ }

  public Greeting(String message) {
    this.message = message;
  }

}
