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

@WebServlet(name = "VoireEtape_e", value = "/VoireEtape_e")
public class VoireEtape_e extends HttpServlet {
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
        if (request.getParameter("idEtape") != null){
            System.out.println(request.getParameter("idEtape"));
        }
        request.getRequestDispatcher("voireEtape_e.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("idEtape") != null && request.getParameter("idEquipe") != null){
            int idEtape = Integer.parseInt(request.getParameter("idEtape"));
            int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
            Coureur coureur = new Coureur();
            Etape et = new Etape();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                List<Coureur> coureurList_Non_Composer = coureur.coureurs_non_Composer_by_equipe_by_Etape(idEquipe , idEtape , connection);
                List<Coureur> coureurList_Composer = coureur.coureurs_Composer_by_equipe_by_Etape(idEquipe , idEtape , connection);
                Etape etape = et.etape_byId(idEtape ,connection);
                request.setAttribute("etape" , etape);
                request.setAttribute("coureurList_Non_Composer" , coureurList_Non_Composer);
                request.setAttribute("coureurList_Composer" , coureurList_Composer);
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("composition.jsp").forward(request, response);
    }
}
