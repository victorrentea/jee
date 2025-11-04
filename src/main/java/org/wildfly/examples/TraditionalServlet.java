package org.wildfly.examples;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A traditional Servlet registered via web.xml which injects an EJB using CDI @Inject.
 */
public class TraditionalServlet extends HttpServlet {

  @Inject
  GreetingEJB greetingEJB;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/plain;charset=UTF-8");
    try (PrintWriter out = resp.getWriter()) {
      String who = req.getParameter("name");
      if (who == null || who.isBlank()) {
        who = "web.xml";
      }
      String msg = greetingEJB.hello(who);
      out.println(msg);
    }
  }
}
