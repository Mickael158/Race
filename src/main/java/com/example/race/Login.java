package com.example.race;

import Model.ConnexionBDD;
import Model.Equipe;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
                    response.sendRedirect("indexEquipe.jsp");
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
