package edu.uml.diet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet for handling user login attempts
 *
 * Created by adil on 3/1/15.
 */
public class LoginServlet extends HttpServlet {

    private String email = "";
    private String password = "";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
                "        \"http://www.w3.org/TR/html4/loose.dtd\">");
        email = request.getParameter("email");
        password = request.getParameter("password");

        BasicUserService basicUserService = new BasicUserService(new DbUserServices());
        boolean authenticated = false;
        try {
            authenticated = basicUserService.verifyUser(email, password);
        } catch (PersistanceUserServicesException e) {
            e.printStackTrace();
        }
        //TODO: Proper formatting/HTML output
        if (authenticated) {
            out.println("<p>HI</p>");
        } else {
            out.println("<p>Username not found. Do you want to <a href = \"register.html\"> register</a>?</p>");
        }
    }
}
