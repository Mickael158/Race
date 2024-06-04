package com.example.race;

import Model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Login", value = {"", "/Login"})
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("email") != null && request.getParameter("pswd") != null){
            String email = request.getParameter("email");
            String pwsd = request.getParameter("pswd");
            User user = new User();
            Equipe eq = new Equipe();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                Equipe equipe = eq.checkEquipe(email , pwsd , connection);
                User admin = user.checkAdmin(email , pwsd , connection);
                if (equipe.getIdEquipe() != 0){
                    request.getSession().setAttribute("equipe", equipe);
                    Etape etape = new Etape();
                    Classement classement = new Classement();
                    classement.reinitialisation_Classement(connection);
                    Resultat resultat = new Resultat();
                    Coureur coureur = new Coureur();
                    List<Etape> etapes = etape.AllEtape(connection);
                    List<Resultat> resultats = resultat.temps_coureur_by_equipe(equipe.getIdEquipe(), connection);
                    List<Coureur> coureurs = coureur.coureurs_Composer_by_equipe(equipe.getIdEquipe(), connection);
                    request.setAttribute("etapes" , etapes);
                    request.setAttribute("coureurs" , coureurs);
                    request.setAttribute("resultats" , resultats);
                    request.getRequestDispatcher("indexEquipe.jsp").forward(request, response);
                }
                else if (admin.getIdUser() != 0){
                    request.getSession().setAttribute("admin", admin);
                    response.sendRedirect("indexAdmin.jsp");
                }
                else {
                    response.sendRedirect("login.jsp");
                }
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
