package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.logic.UserServiceException;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Portion;
import org.joda.time.DateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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

        //SESSION INITIALIZATION
        session.setAttribute("loggedIn", false);
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

        //attempt to authenticate user
        boolean authenticated = authenticate(session, email, password);

        //if user authenticates
        //get today and store in session
        //also stores user email in session
        if (authenticated) {
            getInfo(response, session, email);
        }

        //if not authenticated, show error, direct user to registration page
        else {
            request.setAttribute("error", "Username not found. Do you want to <a href = \"register\"> register</a>?");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    /**
     * method to authenticate user. takes inputted email and password
     * and checks it against existing database entries
     * also correctly sets session variable
     * @param session
     * @param email
     * @param password
     * @return authenticated
     * @throws ServletException
     */
    private boolean authenticate(HttpSession session, String email, String password) throws ServletException {
        //create service to authenticate user
        UserService userService;
        try {
            userService = ServiceFactory.getUserServiceInstance();
        } catch (UserServiceException e) {
            throw new ServletException("Error creating userService", e);
        }
        boolean authenticated;

        //attempt to authenticate user
        try {
            authenticated = userService.verifyUser(email, password);
            session.setAttribute("loggedIn", authenticated);
        } catch (UserServiceException e) {
            throw new ServletException("Error authenticating", e);
        }
        return authenticated;
    }

    /**
     * gets required database info for authenticated user
     * sets session variables (email, foodService, day)
     * directs user to the next page
     * @param response
     * @param session
     * @param email
     * @throws ServletException
     * @throws IOException
     */
    private void getInfo(HttpServletResponse response, HttpSession session, String email) throws ServletException, IOException {
        FoodService foodService;
        try {
            foodService = ServiceFactory.getFoodServiceInstance();
        } catch (FoodServiceException e) {
            throw new ServletException("Could not create foodService ", e);
        }
        //getting day here
        Day day;
        try {
            day = foodService.getDay(email, DateTime.now());
        } catch (FoodServiceException e) {
            throw new ServletException("Could not get day ", e);
        }

        //store info we'll need later in session
        session.setAttribute("email", email);
        session.setAttribute("foodService", foodService);
        session.setAttribute("day", day);
        response.sendRedirect("welcome");
    }
}
