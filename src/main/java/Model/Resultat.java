package Model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Resultat {
    private int idResultat;
    private int idCoureur;
    private int idEtape;
    private Timestamp fin;

    public void insertResultat_etape(Resultat resultat , Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into resultat_etape ( idCoureur, idetape, fin) VALUES (?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, resultat.getIdCoureur());
                preparedStatement.setInt(2, resultat.getIdEtape());
                preparedStatement.setTimestamp(3, resultat.getFin());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public Timestamp addSecond(Timestamp timestamp , int seconde){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.add(Calendar.SECOND ,seconde);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public Timestamp convert_to_timetamps(String datetimelocale){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(datetimelocale , formatter);
        return Timestamp.valueOf(localDateTime);
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public int getIdCoureur() {
        return idCoureur;
    }

    public void setIdCoureur(int idCoureur) {
        this.idCoureur = idCoureur;
    }

    public int getIdEtape() {
        return idEtape;
    }

    public void setIdEtape(int idEtape) {
        this.idEtape = idEtape;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }
}
