package com.example.race;

import Model.ConnexionBDD;
import Model.Equipe;
import Model.Etape;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@WebServlet(name = "Penalite", value = "/Penalite")
public class Penalite extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etape etape = new Etape();
        Equipe equipe = new Equipe();
        ConnexionBDD connexionBDD = new ConnexionBDD();
        try {
            Connection connection = connexionBDD.connect();
            List<Etape> etapes = etape.AllEtape(connection);
            List<Equipe> equipes = equipe.AllEquipe(connection);
            List<Equipe> penalite = equipe.AllPenalite(connection);
            request.setAttribute("penalite" , penalite);
            request.setAttribute("etapes" , etapes);
            request.setAttribute("equipes" , equipes);
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("penalite.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Etape etape = new Etape();
        Equipe equipe = new Equipe();
        if (request.getParameter("idEtape") != null && request.getParameter("idEquipe") != null && request.getParameter("temps") != null && request.getParameter("seconde") != null){
            int idEtape = Integer.parseInt(request.getParameter("idEtape"));
            int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
            Time time = Time.valueOf(request.getParameter("temps")+":"+request.getParameter("seconde"));
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                equipe.setIdEquipe(idEquipe);
                equipe.setIdEtape(idEtape);
                equipe.setPenalite(time);
                equipe.insert_Penalite_equipe(equipe , connection);
                List<Etape> etapes = etape.AllEtape(connection);
                List<Equipe> equipes = equipe.AllEquipe(connection);
                List<Equipe> penalite = equipe.AllPenalite(connection);
                request.setAttribute("penalite" , penalite);
                request.setAttribute("etapes" , etapes);
                request.setAttribute("equipes" , equipes);
                connection.close();
                request.getRequestDispatcher("penalite.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("penalite.jsp").forward(request, response);
        }
        if (request.getParameter("idPenalite") != null ){
            int idPenalite = Integer.parseInt(request.getParameter("idPenalite"));
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                equipe.setIdPenalite(idPenalite);
                equipe.delet_Penalite_equipe(equipe , connection);
                List<Etape> etapes = etape.AllEtape(connection);
                List<Equipe> equipes = equipe.AllEquipe(connection);
                List<Equipe> penalite = equipe.AllPenalite(connection);
                request.setAttribute("penalite" , penalite);
                request.setAttribute("etapes" , etapes);
                request.setAttribute("equipes" , equipes);
                connection.close();
                request.getRequestDispatcher("penalite.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("penalite.jsp").forward(request, response);
        }
    }
}
