package edu.uml.diet.gui;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.logic.UserServiceException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet for handling user login attempts
 *
 * Created by adil on 3/1/15.
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //create session
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedIn", false);

        //nothing else to do, send user to login page
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get session
        //if no session exists, create one
        HttpSession session = request.getSession(true);

        //get user inputted info
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = null;
        try {
            userService = ServiceFactory.getUserServiceInstance();
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        boolean authenticated = false;
        try {
            authenticated = userService.verifyUser(email, password);
            session.setAttribute("loggedIn", authenticated);
        } catch (UserServiceException e) {
            System.err.println("User service error occurred");
            e.printStackTrace();
        }

        if (authenticated) {
            request.getRequestDispatcher("welcome.jsp").forward(request,response);
        } else {
            request.setAttribute("error", "Username not found. Do you want to <a href = \"register.html\"> register</a>?");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
