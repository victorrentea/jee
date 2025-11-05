package org.wildfly.examples;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

//@Singleton (ejb)
@Dependent
//@Singleton// (cdi) // TODO victorrentea 2025-11-04: why doesn't work
public class GreetingJdbc2Repo {

  @Resource(lookup = "java:/jdbc/jeeDS")
  DataSource dataSource;

  public int sqlInsert(String message) throws SQLException {
    System.out.println("Using: " + dataSource.getClass());
    //language=sql
    String sql = "insert into greeting(message,createdat) values (?,?)";
    try (Connection c = dataSource.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, message);
      ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
      return ps.executeUpdate();
//    } catch (SQLException e) {
//      throw new RuntimeException("Failed to insert greeting", e);
    }
  }
}
