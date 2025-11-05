package org.wildfly.examples;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

//@Singleton
@Dependent
public class GreetingJdbcJndiManualLookupRepo {

  public int sqlInsertJndi(String message) throws NamingException {
    Context ctx = new InitialContext();
    javax.sql.DataSource dataSource = (javax.sql.DataSource) ctx.lookup("java:/jdbc/jeeDS");

    String sql = "insert into greeting(message,createdat) values (?,?)";
    try (Connection c = dataSource.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, message);
      ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to insert greeting", e);
    }
  }

}
