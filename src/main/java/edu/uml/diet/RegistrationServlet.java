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

        BasicUserService basicUserService = new BasicUserService(new DbUserServices());
        //TODO: remove
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

        //TODO: End remove

        try {
            basicUserService.createUser(email, password);
            out.println("successfully created. You will now be directed back to login");
        } catch (PersistanceUserServicesException e) {
            e.printStackTrace(out);
        } catch (DuplicateUserException e) {
            e.printStackTrace(out);
        }


    }


}
