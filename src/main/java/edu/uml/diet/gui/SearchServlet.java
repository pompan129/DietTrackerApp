package edu.uml.diet.gui;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.model.Portion;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by adil on 3/8/15.
 */
public class SearchServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //login check
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if(session != null) {
            loggedIn = (boolean) session.getAttribute("loggedIn");
        }

        //check if user is logged in and send them to search page
        if(loggedIn)
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        //if not logged in, go to login page
        else
            response.sendRedirect("login");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        //after user has searched, process their request
        FoodService foodService;
        try {
            foodService = ServiceFactory.getFoodServiceInstance();
        } catch (FoodServiceException e) {
            throw new ServletException("SearchServlet Error when creating foodService: ", e);
        }
        List<Portion> foodList = null;
        if(foodService != null) {
            try {
                foodList = foodService.foodListSearch(query);
            } catch (FoodServiceException e) {
                throw new ServletException("SearchServlet Error when creating foodList: ", e);
            }
        }
        if(foodList.isEmpty()) {
            request.setAttribute("error", "ERROR: Query not found/ListReturned empty");
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }
        else {
            request.setAttribute("foodList", foodList);
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }
    }

}
