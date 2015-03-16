package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Created by adil on 3/15/15.
 */
public class SelectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String[] portionIDs = request.getParameterValues("portionID");

        double portionSize = 1.0;
        String portionID = "testFood";
        FoodService foodService;
        Portion portion;
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");

        try {
            foodService = ServiceFactory.getFoodServiceInstance();
        } catch (FoodServiceException e) {
            throw new ServletException("SearchServlet Error when creating foodService: ", e);
        }

        if(portionIDs != null) {
            for (String selected : portionIDs) {
                portionSize = Integer.parseInt(request.getParameter("portionSize"));
                portionID = selected;
                try {
                    portion = foodService.foodSearch(portionID);
                } catch (FoodServiceException e) {
                    throw new ServletException("Error searching for single food: ", e);
                }
                portion.setPortionSize(portionSize);
                userPortionList.add(portion);
            }
            session.setAttribute("userPortionList", userPortionList);
            request.setAttribute("userPortionList", userPortionList);
            request.getRequestDispatcher("/WEB-INF/select.jsp").forward(request, response);
        }
        else {
            out.println("error: nothing selected");
        }
    }
}
