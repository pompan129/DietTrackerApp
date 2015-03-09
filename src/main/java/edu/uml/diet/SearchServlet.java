package edu.uml.diet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by adil on 3/8/15.
 */
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.getRequestDispatcher("login.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: remove
        PrintWriter out = response.getWriter();
        //after user has searched, process their request
        FoodService foodService = null;
        try {
            foodService = ServiceFactory.getFoodServiceInstance();
        } catch (FoodServiceException e) {
            System.err.println(e.getCause() + e.getMessage());
        }
        List<Portion> foodList = null;
        if(foodService != null) {
            try {
                foodList = foodService.foodListSearch(request.getParameter("query"));
            } catch (FoodServiceException e) {
                e.printStackTrace();
            }
        }
        //request.setAttribute("foodList", foodList);
        //request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        //TODO: remove

        for(int i = 0; i<foodList.size(); i++) {
            out.println(i);
            out.println(foodList.get(i).getFood().getName());
            out.println(foodList.get(i).getFood().getId());
        }
        //END TODO
    }

}
