package edu.uml.diet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet for handling registration (creating a new user)
 *
 * Created by adil on 3/1/15.
 */
public class RegistrationServlet extends HttpServlet {

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

        UserService userService = ServiceFactory.getUserServiceInstance();
       /*
        DatabaseBuilder builder = new DatabaseBuilder(new DatabaseConnector(), "DietTracker");
        try {
            if(!builder.CheckIfDbExists()) {
                builder.CreateDatabase();
            }
        } catch (DatabaseConnectorException e) {
            e.printStackTrace();
        }
        try {
            if(!builder.CheckIfTableExists("USERS")) {
                builder.CreateUserTable();
            }
        } catch (DatabaseConnectorException e) {
            e.printStackTrace();
        }

         */
        try {
            userService.createUser(email, password);
        } catch (UserServiceException e) {
            System.err.println("Error creating user");
            e.printStackTrace();
        }
        out.println("successfully created. You will now be directed back to login");
        response.setHeader("Location", "./login.html");
    }


}