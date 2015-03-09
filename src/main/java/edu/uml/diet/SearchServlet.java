package edu.uml.diet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by adil on 3/8/15.
 */
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //nothing has happened yet, go through to JSP
        request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //after user has searched, process their request
        FoodService foodService = ServiceFactory.getFoodServiceInstance();
        List<Portion> foodList = foodService.foodListSearch("query");
        request.setAttribute("foodList", foodList);
        request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
    }

}
