package com.example.race;

import Model.ConnexionBDD;
import Model.Etape;
import Model.Point;
import Model.Resultat_import;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "Import_Point", value = "/Import_Point")
public class Import_Point extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("importPoint.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("point") != null ){
            String p = request.getParameter("point");
            Point point = new Point();
            ConnexionBDD connexionBDD = new ConnexionBDD();
            try {
                Connection connection = connexionBDD.connect();
                connection.setAutoCommit(false);
                point.insertAll_point(p , connection);
                connection.commit();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        request.getRequestDispatcher("importPoint.jsp").forward(request, response);
    }
}
