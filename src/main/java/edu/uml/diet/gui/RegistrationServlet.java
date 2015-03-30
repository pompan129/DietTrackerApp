package edu.uml.diet.gui;

import edu.uml.diet.logic.DuplicateUserException;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.logic.UserServiceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for handling registration (creating a new user)
 *
 * Created by adil on 3/1/15.
 */
public class RegistrationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //create session if one does not exist
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedIn", false);

        //nothing else to do, send user to registration page
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get email and password from user input
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //get user service instance
        UserService userService = null;
        try {
            userService = ServiceFactory.getUserServiceInstance();
        } catch (UserServiceException e) {
            throw new ServletException("Error creating userService: ", e);
        }

        //create user
        try {
            userService.createUser(email, password);
        } catch (UserServiceException e) {
            throw new ServletException("Error creating user: ", e);
        } catch (DuplicateUserException e) {
            request.setAttribute("error", "Username already found. Please try again with a different username.");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }

        //redirect user to login page
        response.sendRedirect("login");
    }
}
