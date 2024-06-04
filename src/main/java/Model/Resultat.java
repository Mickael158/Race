package Model;

import Traitement.Donne;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Resultat extends Coureur{
    private int idResultat;
    private int idCoureur;
    private int idEtape;
    private Timestamp fin;
    private String diff;

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
    public void delet_Resultat_etape( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from resultat_etape";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Resultat etape Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public List<Resultat> temps_coureur_by_equipe(int idEquipe, Connection connection) throws SQLException, ClassNotFoundException {
        List<Resultat> resultats = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select coureur.idCoureur , coureur.nom , resultat_etape.idetape , resultat_etape.fin , EXTRACT(EPOCH FROM (resultat_etape.fin - etape.depart)) as diff\n" +
                    "    from resultat_etape\n" +
                    "        join coureur on coureur.idCoureur = resultat_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "        join etape on etape.idEtape=resultat_etape.idetape\n" +
                    "    where equipe.idEquipe=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEquipe);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Resultat resultat = new Resultat();
                    resultat.setIdCoureur(resultSet.getInt("idcoureur"));
                    resultat.setNom(resultSet.getString("nom"));
                    resultat.setIdEtape(resultSet.getInt("idetape"));
                    resultat.setFin(resultSet.getTimestamp("fin"));
                    resultat.setDiff(convertSecondToTime(Double.parseDouble(resultSet.getString("diff"))));
                    resultats.add(resultat);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return resultats;
    }
    public String convertSecondToTime(double seconde){
        int hours = (int) (seconde/3600);
        int minute =  ((int)(seconde % 3600) / 60);
        double new_seconde = seconde % 60;
        return String.format("%02d:%02d:%06.3f" , hours, minute , new_seconde);
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

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }
}
