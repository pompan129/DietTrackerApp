package edu.uml.diet.gui;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.logic.UserServiceException;
import edu.uml.diet.model.Portion;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        //TODO may take this out
        ArrayList<Portion> userPortionList =  new ArrayList<>();
        session.setAttribute("userPortionList", userPortionList);

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
            throw new ServletException("Error creating userService", e);
        }
        boolean authenticated = false;
        try {
            authenticated = userService.verifyUser(email, password);
            session.setAttribute("loggedIn", authenticated);
        } catch (UserServiceException e) {
            throw new ServletException("Error authenticating", e);
        }

        if (authenticated) {
            request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request,response);
        } else {
            request.setAttribute("error", "Username not found. Do you want to <a href = \"register.html\"> register</a>?");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
