package edu.uml.diet.gui;

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

        //if logged in, allow user to go to search page
        if(loggedIn)
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        //if not logged in, go to login page
        else
            response.sendRedirect("login");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get user search query
        String query = request.getParameter("query");
        HttpSession session = request.getSession(false);

        //after user has searched, process their request
        FoodService foodService = (FoodService) session.getAttribute("foodService");
        List<Portion> portionList = null;

        //get search results as portionList
        if(foodService != null) {
            try {
                portionList = foodService.foodListSearch(query);
            } catch (FoodServiceException e) {
                throw new ServletException("SearchServlet Error when creating foodList: ", e);
            }
        }

        //if search results are empty, show user an error
        if(portionList.isEmpty()) {
            request.setAttribute("error", "ERROR: Query not found/ListReturned empty");
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }

        //if search results not empty, set search result in session
        //and display the search results for selection
        else {
            request.setAttribute("portionList", portionList);
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }
    }
}
