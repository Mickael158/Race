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

@WebServlet(name = "AddCoureur", value = "/AddCoureur")
public class AddCoureur extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("idEquipe") != null && request.getParameter("idEtape") != null && request.getParameter("idCoureur[]") != null){
            int idEtape = Integer.parseInt(request.getParameter("idEtape"));
            int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
            String[] idCoureur = request.getParameterValues("idCoureur[]");
            Coureur coureur = new Coureur();
            Etape et = new Etape();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                for (int i = 0 ; i < idCoureur.length ; i++){
                    coureur.setIdCoureur(Integer.parseInt(idCoureur[i]));
                    System.out.println(idCoureur[i]);
                    coureur.setIdEtape(idEtape);
                    coureur.setIdEquipe(idEquipe);
                    Etape etape = et.etape_byId(idEtape ,connection);
                    List<Etape> etapes = et.AllEtape(connection);
                    List<Coureur> coureurList_Composer = coureur.coureurs_Composer_by_equipe_by_Etape(idEquipe , idEtape , connection);
                    List<Coureur> coureurList_Non_Composer = coureur.coureurs_non_Composer_by_equipe_by_Etape(idEquipe , idEtape , connection);
                    if (coureurList_Composer.size()+idCoureur.length <= etape.getNbr_Coureur_Equipe()){
                        coureur.insertComposition_etape(coureur , connection);
                    }
                    request.setAttribute("etapes" , etapes);
                    request.setAttribute("coureurList_Non_Composer" , coureurList_Non_Composer);
                    request.setAttribute("coureurList_Composer" , coureurList_Composer);
                }
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("voireEtape_e.jsp").forward(request, response);
    }
}
