package com.example.race;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Enregistrement", value = "/Enregistrement")
public class Enregistrement extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("idetape") != null ){
            int idEtape = Integer.parseInt(request.getParameter("idetape"));

        }
    }
}
