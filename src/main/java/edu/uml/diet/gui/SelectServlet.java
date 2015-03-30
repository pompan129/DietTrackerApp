package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * handles selecting searched portions
 *
 * Created by adil on 3/15/15.
 */
public class SelectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        //get selected portionIDs and sizes from previous page
        String[] portionIDs = request.getParameterValues("portionID");
        String[] portionSizes = request.getParameterValues("portionSize");

        //parse portionSizes to remove empty entries
        List<String> portionSizesParsed = new ArrayList<>(Arrays.asList(portionSizes));
        portionSizesParsed.removeAll(Arrays.asList("", null));

        //get foodService from session
        FoodService foodService = (FoodService) session.getAttribute("foodService");
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");

        //make sure user actually selected some portions
        if(portionIDs != null) {
            setSelectedPortions(request, response, session, portionIDs, portionSizesParsed, foodService, userPortionList);
        }
        else {
            request.setAttribute("error", "Error: nothing selected.");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //so we can check if the post has happened yet
        request.setAttribute("posted", true);

        //get session
        HttpSession session = request.getSession(false);

        //variable messages to JSP
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        Day day = getAndUpdateDay(request, session);


        FoodService foodService = (FoodService) session.getAttribute("foodService");

        //update the database with the new information
        try {
            foodService.addOrUpdateDay(day);
        } catch (FoodServiceException e) {
            throw new ServletException("Could not add or update day", e);
        }

        //set the session variable DAY to the new, updated DAY
        session.setAttribute("day", day);
        session.setAttribute("userPortionList", new ArrayList<Portion>());

        //refresh page
        request.getRequestDispatcher("/WEB-INF/select.jsp").forward(request, response);
    }

    /**
     * Get selected portions from previous page
     * Get portions from the database
     * Store portions in user's day
     * Refresh page with updated info
     * @param request               HTTP request variable
     * @param response              HTTP response variable
     * @param session               HTTP session variable
     * @param portionIDs            user selected portionIDs
     * @param portionSizesParsed    parsed list of user-selected portionSizes
     * @param foodService           service to interact with food database
     * @param userPortionList       user portion list for session and day updating
     * @throws ServletException
     * @throws IOException
     */
    private void setSelectedPortions(HttpServletRequest request, HttpServletResponse response, HttpSession session, String[] portionIDs, List<String> portionSizesParsed, FoodService foodService, ArrayList<Portion> userPortionList) throws ServletException, IOException {
        double portionSize;
        String portionID;
        Portion portion;
        for (int i = 0; i<portionSizesParsed.size(); i++) {
            //set portionSize and portionID
            portionSize = Integer.parseInt(portionSizesParsed.get(i));
            portionID = portionIDs[i];

            //get actual portion from database
            try {
                portion = foodService.foodSearch(portionID);
            } catch (FoodServiceException e) {
                throw new ServletException("Error searching for single food: ", e);
            }

            //set portion size
            portion.setPortionSize(portionSize);
            //add to user's portion list
            userPortionList.add(portion);
        }
        session.setAttribute("userPortionList", userPortionList);
        //get current day from session
        Day day = (Day) session.getAttribute("day");
        request.setAttribute("meals", day.getMeals());
        request.getRequestDispatcher("/WEB-INF/select.jsp").forward(request, response);
    }

    /**
     * get day from user input from previous page
     * update the session day with the new information
     * @param request       HTTP request variable
     * @param session       HTTP session variable
     * @return Day
     */
    private Day getAndUpdateDay(HttpServletRequest request, HttpSession session) {
        //get selected mealID
        int mealID = Integer.parseInt(request.getParameter("mealID"));

        //decrement to prevent fencepost error
        mealID--;
        ArrayList<Portion> userPortionList = (ArrayList<Portion>) session.getAttribute("userPortionList");

        //get the right day
        Day day = (Day) session.getAttribute("day");
        //get meals list from day
        ArrayList<Meal> meals = new ArrayList<>(day.getMeals());
        //get the particular meal the user selected
        Meal userMeal = meals.get(mealID);

        //set selected portions to the selected meal
        userMeal.setPortions(userPortionList);

        //put the updated meal back in the meal list
        meals.set(mealID, userMeal);

        //put the updated meal list back in the day
        day.setMeals(meals);
        request.setAttribute("userMeal", userMeal);
        request.setAttribute("userPortionList", userPortionList);
        return day;
    }
}


