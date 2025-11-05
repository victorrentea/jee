package org.wildfly.examples;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

//@Singleton
@Dependent
public class GreetingJdbcRepo {

  @Resource(lookup = "java:/jdbc/jeeDS")
  DataSource dataSource;

  public int sqlInsert(String message) {
    try {
    return sqlInsertThrowingSql(message);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to insert greeting", e);
    }
  }

  public int sqlInsertThrowingSql(String message) throws SQLException {
    String sql = "insert into greeting(message,createdat) values (?,?)";
    try (Connection c = dataSource.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, message);
      ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
      return ps.executeUpdate();
    }
  }
}
