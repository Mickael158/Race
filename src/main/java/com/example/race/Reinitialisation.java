package com.example.race;

import Model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "Reinitialisation", value = "/Reinitialisation")
public class Reinitialisation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reinitialisation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Classement classement = new Classement();
        Resultat_import resultat_import = new Resultat_import();
        Resultat resultat = new Resultat();
        Point point = new Point();
        Etape etape = new Etape();
        Coureur coureur = new Coureur();
        Equipe equipe = new Equipe();
        ConnexionBDD connexionBDD = new ConnexionBDD();
        try {
            Connection connection = connexionBDD.connect();
            connection.setAutoCommit(false);
            if ( request.getParameter("reinitialisation") !=null){
                classement.delete_Classement(connection);
                resultat_import.delet_Resultat_import(connection);
                resultat.delet_Resultat_etape(connection);
                point.delet_Point(connection);
                etape.delet_Composition(connection);
                etape.delet_Etape(connection);
                coureur.delet_Categories_coureur(connection);
                coureur.delet_Coureur(connection);
                equipe.delet_Equipe(connection);
                System.out.println("Reinitialisation OK");
            }
            if ( request.getParameter("generer") !=null){
                Categorie categorie = new Categorie();
                categorie.generet_AllCategorie_Coureur(connection);
                System.out.println("Generer OK");
            }
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("reinitialisation.jsp").forward(request, response);
    }
}
