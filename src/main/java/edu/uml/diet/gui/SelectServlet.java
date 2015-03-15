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
import java.util.Enumeration;
import java.util.List;

/**
 * Created by adil on 3/15/15.
 */
public class SelectServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        }
        else {
            out.println("error: nothing selected");
        }

        for(int i = 0; i<userPortionList.size(); i++) {
            out.println(userPortionList.get(i).getFood().getName());
            out.println(userPortionList.get(i).getCalories());
        }

        //Enumeration<String> parameterNames = request.getParameterNames();
        //String parameterName = parameterNames.nextElement();
        //out.println(request.getParameter("selected"));
        /*
        while(parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if(parameterName.equals("selected") && request.getParameter("selected") != null) {
                if (request.getParameter(parameterName) != null && parameterName.equals("portionSize"))
                    portionSize = Integer.parseInt(request.getParameter("portionSize"));
                if (request.getParameter(parameterName) != null && parameterName.equals("portionID"))
                    portionID = request.getParameter("portionID");
            }
        }
        */
/*
        FoodService foodService;
        try {
            foodService = ServiceFactory.getFoodServiceInstance();
        } catch (FoodServiceException e) {
            throw new ServletException("SearchServlet Error when creating foodService: ", e);
        }
        Portion portion;
        try {
            portion = foodService.foodSearch(portionID);
        } catch (FoodServiceException e) {
            throw new ServletException("Error searching for single food: ", e);
        }

        portion.setPortionSize(portionSize);
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");
        userPortionList.add(portion);

        for(int i = 0; i<userPortionList.size(); i++) {
            out.println(userPortionList.get(i).getFood().getName());
            out.println(userPortionList.get(i).getCalories());
        }
        session.setAttribute("userPortionList", userPortionList);

        out.println(test.get(0)); */
    }
}
