package com.example.race;

import Model.ConnexionBDD;
import Model.Coureur;
import Model.Etape;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "VoireEtape_a", value = "/VoireEtape_a")
public class VoireEtape_a extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etape etape = new Etape();
        ConnexionBDD connexionBDD = new ConnexionBDD();
        try {
            Connection connection = connexionBDD.connect();
            List<Etape> etapes = etape.AllEtape(connection);
            request.setAttribute("etapes" , etapes);
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("voireEtape_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("idetape") != null ){
            int idEtape = Integer.parseInt(request.getParameter("idetape"));
            Coureur coureur = new Coureur();
            Etape et = new Etape();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                List<Coureur> coureurList_Composer = coureur.coureurs_Composer( idEtape , connection);
                Etape etape = et.etape_byId(idEtape ,connection);
                request.setAttribute("etape" , etape);
                request.setAttribute("coureurList_Composer" , coureurList_Composer);
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("temps.jsp").forward(request, response);
    }
}
