package edu.uml.diet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import edu.uml.diet.*;


/**
 * Created by adil on 3/1/15.
 */
public class LoginServlet extends HttpServlet {

    private String email = "";
    private String password = "";
    private PersistanceUserServices persistanceUserService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        email = request.getParameter("email");
        password = request.getParameter("password");

        BasicUserService basicUserService = new BasicUserService(persistanceUserService);
        boolean authentication = basicUserService.verifyUser(email, password);
        if(authentication){
            out.println("HI");
        }
        else {
            out.println(email + '\n' + password);
        }



    }
}
