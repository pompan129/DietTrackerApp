package edu.uml.diet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by adil on 2/26/15.
 */
public class User2 extends HttpServlet {

    public void User2() {};
    public User2() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("TEST");
    }
}