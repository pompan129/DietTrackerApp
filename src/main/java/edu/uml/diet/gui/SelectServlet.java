package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Created by adil on 3/15/15.
 */
public class SelectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String[] portionIDs = request.getParameterValues("portionID");
        String[] portionSizes = request.getParameterValues("portionSize");

        //parse portionSizes to remove empties
        List<String> portionSizesParsed = new ArrayList<String>(Arrays.asList(portionSizes));
        portionSizesParsed.removeAll(Arrays.asList("", null));

        double portionSize;
        String portionID;
        FoodService foodService = (FoodService) session.getAttribute("foodService");
        Portion portion;
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");

        if(portionIDs != null) {
            for (int i = 0; i<portionSizesParsed.size(); i++) {
                //out.println(i);
                //out.println(portionSizesParsed.get(i));
                portionSize = Integer.parseInt(portionSizesParsed.get(i));
                portionID = portionIDs[i];
                try {
                    portion = foodService.foodSearch(portionID);
                } catch (FoodServiceException e) {
                    throw new ServletException("Error searching for single food: ", e);
                }
                portion.setPortionSize(portionSize);
                userPortionList.add(portion);
            }
            session.setAttribute("userPortionList", userPortionList);
            //request.setAttribute("userPortionList", userPortionList);
            Day day = (Day) session.getAttribute("day");
            request.setAttribute("meals", day.getMeals());
            request.getRequestDispatcher("/WEB-INF/select.jsp").forward(request, response);
        }
        else {
            //out.println("error: nothing selected");
            //Enumeration<String> sessionStuff = session.getAttributeNames();
            request.setAttribute("error", "Error: nothing selected.");
           /* while(sessionStuff.hasMoreElements()) {
                String sessionVar = sessionStuff.nextElement();
                out.println(sessionVar);
            } */
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //so we can check if the post has happened yet
        boolean posted = true;
        request.setAttribute("posted", posted);

        //variable messages to JSP
        Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);

        PrintWriter out = response.getWriter();
        int mealID = Integer.parseInt((String) request.getParameter("mealID"));
        mealID--;
        HttpSession session = request.getSession(false);
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");

        //get the right day
        Day day = (Day) session.getAttribute("day");
        ArrayList<Meal> meals = new ArrayList<Meal>(day.getMeals());
        Meal userMeal = meals.get(mealID);
        userMeal.setPortions(userPortionList);
        meals.set(mealID, userMeal);
        day.setMeals(meals);
        request.setAttribute("userMeal", userMeal);
        request.setAttribute("userPortionList", userPortionList);

        FoodService foodService = (FoodService) session.getAttribute("foodService");
        try {
            foodService.addOrUpdateDay(day);
        } catch (FoodServiceException e) {
            throw new ServletException("Could not add or update day", e);
        }
        session.setAttribute("day", day);
        session.setAttribute("userPortionList", new ArrayList<Portion>());
        
        request.getRequestDispatcher("/WEB-INF/select.jsp").forward(request, response);
    }
}
