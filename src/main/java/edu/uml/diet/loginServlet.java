package edu.uml.diet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by adil on 3/1/15.
 */
public class loginServlet extends HttpServlet {

    private String email = "";
    private String password = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        email = request.getParameter("email");
        password = request.getParameter("password");
        out.println(email + '\n' + password);



    }
}
