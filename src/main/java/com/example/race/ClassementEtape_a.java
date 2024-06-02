package com.example.race;

import Model.Classement;
import Model.ConnexionBDD;
import Model.Etape;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ClassementEtape_a", value = "/ClassementEtape_a")
public class ClassementEtape_a extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etape etape = new Etape();
        Classement classement = new Classement();
        ConnexionBDD connexionBDD = new ConnexionBDD();
        try {
            Connection connection = connexionBDD.connect();
            classement.reinitialisation_Classement(connection);
            List<Etape> etapeList = etape.AllEtape(connection);
            List<Classement> classements = classement.classements_by_etape(etapeList.get(0).getIdEtape() , connection);
            request.setAttribute("etapeList" , etapeList);
            request.setAttribute("classements" , classements);
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("classementEtape_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etape etape = new Etape();
        Classement classement = new Classement();
        if (request.getParameter("idEtape") != null ){
            int idEtape = Integer.parseInt(request.getParameter("idEtape"));
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                List<Etape> etapeList = etape.AllEtape(connection);
                List<Classement> classements = classement.classements_by_etape(idEtape , connection);
                request.setAttribute("etapeList" , etapeList);
                request.setAttribute("classements" , classements);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("classementEtape_a.jsp").forward(request, response);
    }
}
