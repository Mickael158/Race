package com.example.race;

import Model.ConnexionBDD;
import Model.Etape;
import Model.Resultat_import;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "Import_etape_resultat", value = "/Import_etape_resultat")
public class Import_etape_resultat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("importEtapeResultat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("etape") != null && request.getParameter("resultat") != null){
            String etape = request.getParameter("etape");
            String resultat = request.getParameter("resultat");
            Resultat_import resultat_import = new Resultat_import();
            Etape et = new Etape();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                connection.setAutoCommit(false);
                et.insertAll_etape_import(etape , connection);
                resultat_import.insertAll_resultat_import(resultat , connection);
                resultat_import.insertEquipe_import(connection);
                resultat_import.insertCoureur_import(connection);
                resultat_import.insertComposition_etape_import(connection);
                resultat_import.insertResultat_officile_etape_import(connection);
                connection.commit();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("importEtapeResultat.jsp").forward(request, response);
    }
}
