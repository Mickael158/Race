package com.example.race;

import Model.ConnexionBDD;
import Model.Coureur;
import Model.Resultat;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(name = "AddTepms", value = "/AddTepms")
public class AddTemps extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("numero") != null && request.getParameter("idEtape") != null && request.getParameter("fin") != null && request.getParameter("seconde") != null){
            Resultat resultat = new Resultat();
            Coureur coureur = new Coureur();
            String numero = request.getParameter("numero");
            int idEtape = Integer.parseInt(request.getParameter("idEtape"));
            Timestamp finProvisoire = resultat.convert_to_timetamps(request.getParameter("fin"));
            int seconde = Integer.parseInt(request.getParameter("seconde"));
            ConnexionBDD connexionBDD= new ConnexionBDD();
            Timestamp fin = resultat.addSecond(finProvisoire , seconde);
            try {
                Connection connection = connexionBDD.connect();
                resultat.setIdCoureur(coureur.coureurs_by_numero(numero , connection).getIdCoureur());
                resultat.setIdEtape(idEtape);
                resultat.setFin(fin);
                resultat.insertResultat_etape(resultat , connection);
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
