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
 *
 * handles user searches for food portions
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
        //so we can check if the post has happened yet
        request.setAttribute("posted", true);

        //get user search query
        String query = request.getParameter("query");
        HttpSession session = request.getSession(false);

        //after user has searched, process their request
        FoodService foodService = (FoodService) session.getAttribute("foodService");
        List<Portion> portionList;

        //get search results as portionList
        portionList = getPortions(query, foodService);

        //process search results
        //if results are good, shows user results
        processSearchResults(request, response, portionList);


    }

    /**
     * get list of portions fro user search query
     * @param query         user search query
     * @param foodService   service to retrieve portion information
     * @return portionList
     * @throws ServletException
     */
    protected List<Portion> getPortions(String query, FoodService foodService) throws ServletException {
        List<Portion> portionList = null;
        if(foodService != null) {
            try {
                portionList = foodService.foodListSearch(query);
            } catch (FoodServiceException e) {
                throw new ServletException("SearchServlet Error when creating foodList: ", e);
            }
        }
        return portionList;
    }

    /**
     * process search results from user query
     * throw error if user query is empty
     * otherwise, show results
     * @param request       HTTP request variable
     * @param response      HTTP response variable
     * @param portionList   List of user portions
     * @throws ServletException
     * @throws IOException
     */
    protected void processSearchResults(HttpServletRequest request, HttpServletResponse response, List<Portion> portionList) throws ServletException, IOException {
        //if search results are empty, show user an error
        if(portionList.isEmpty()) {
            request.setAttribute("error", "ERROR: Query not found/ListReturned empty");
            request.setAttribute("portionList", portionList); //so tests pass
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
