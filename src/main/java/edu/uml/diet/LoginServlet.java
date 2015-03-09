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
        //create session
        //if no session exists, create one
        HttpSession session = request.getSession(true);

        //if session is not new, skip to welcome page
        if(!session.isNew()) {
            response.sendRedirect("./welcome.jsp");
        }

        //create doctype string
        String doctype = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
                "        \"http://www.w3.org/TR/html4/loose.dtd\">";
        //title string
        String title = "<head><title>Login Page</title></head><body>";

        email = request.getParameter("email");
        password = request.getParameter("password");

        UserService userService = ServiceFactory.getUserServiceInstance();
        boolean authenticated = false;
        try {
            authenticated = userService.verifyUser(email, password);
            session.setAttribute("email", email);
        } catch (UserServiceException e) {
            System.err.println("User service error occurred");
            e.printStackTrace();
        }

        //create printwriter for output
        PrintWriter out = response.getWriter();
        //output header information/doctype information to page
        response.setContentType("text/html");
        out.println(doctype);
        out.println(title);

        if (authenticated) {
            out.println("<p>Welcome to the Diet Tracker App</p><p>Redirecting...</p>");
            response.sendRedirect("./welcome.jsp");
        } else {
            out.println("<p>Username not found. Do you want to <a href = \"register.html\"> register</a>?</p>");
        }
        out.println("</body></html>");
    }
}
