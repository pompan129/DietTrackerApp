package edu.uml.diet.gui;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by adil on 3/15/15.
 */
public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //send the user straight on to the welcome page
        HttpSession session = request.getSession();
        request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
    }
}
