package com.example.race;

import Model.Categorie;
import Model.Classement;
import Model.ConnexionBDD;
import Model.Equipe;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ClassementEquipe_a", value = "/ClassementEquipe_a")
public class ClassementEquipe_a extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Equipe equipe = new Equipe();
        Categorie categorie = new Categorie();
        Classement classement = new Classement();
        ConnexionBDD connexionBDD = new ConnexionBDD();
        try {
            Connection connection = connexionBDD.connect();
            classement.reinitialisation_Classement(connection);
            List<Categorie> categories = categorie.AllCategorie(connection);
            List<Equipe> equipes = equipe.AllEquipe(connection);
            List<Classement> classements = classement.classements_group_by_equipe_by_categorie(0 , connection);
            request.setAttribute("categories" , categories);
            request.setAttribute("equipes" , equipes);
            request.setAttribute("classements" , classements);
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("classementEquipe_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Equipe equipe = new Equipe();
        Categorie categorie = new Categorie();
        Classement classement = new Classement();
        if ( request.getParameter("idCategorie") != null){
            int idCategorie = Integer.parseInt(request.getParameter("idCategorie"));
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                List<Categorie> categories = categorie.AllCategorie(connection);
                List<Equipe> equipes = equipe.AllEquipe(connection);
                List<Classement> classements = classement.classements_group_by_equipe_by_categorie( idCategorie, connection);
                request.setAttribute("categories" , categories);
                request.setAttribute("equipes" , equipes);
                request.setAttribute("classements" , classements);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("classementEquipe_a.jsp").forward(request, response);
    }
}
