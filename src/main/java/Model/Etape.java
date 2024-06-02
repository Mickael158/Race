package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Etape {
    private int idEtape;
    private int nbr_Coureur_Equipe;
    private Timestamp depart;
    private  String lieu;
    private double longueur;

    public List<Etape> AllEtape( Connection connection) throws SQLException, ClassNotFoundException {
        List<Etape> etapes = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select * from etape; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Etape etape = new Etape();
                    etape.setIdEtape(resultSet.getInt("idetape"));
                    etape.setNbr_Coureur_Equipe(resultSet.getInt("nbr_coureur_equipe"));
                    etape.setDepart(resultSet.getTimestamp("depart"));
                    etape.setLieu(resultSet.getString("lieu"));
                    etape.setLongueur(resultSet.getDouble("longueur"));
                    etapes.add(etape);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return etapes;
    }
    public Etape etape_byId(int idetape, Connection connection) throws SQLException, ClassNotFoundException {
        Etape etape = new Etape();
        if (connection != null) {
            String selectQuery = "select * from etape where idetape=?; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1,idetape);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    etape.setIdEtape(resultSet.getInt("idetape"));
                    etape.setNbr_Coureur_Equipe(resultSet.getInt("nbr_coureur_equipe"));
                    etape.setDepart(resultSet.getTimestamp("depart"));
                    etape.setLieu(resultSet.getString("lieu"));
                    etape.setLongueur(resultSet.getDouble("longueur"));
                }
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return etape;
    }

    public int getIdEtape() {
        return idEtape;
    }

    public void setIdEtape(int idEtape) {
        this.idEtape = idEtape;
    }

    public int getNbr_Coureur_Equipe() {
        return nbr_Coureur_Equipe;
    }

    public void setNbr_Coureur_Equipe(int nbr_Coureur_Equipe) {
        this.nbr_Coureur_Equipe = nbr_Coureur_Equipe;
    }

    public Timestamp getDepart() {
        return depart;
    }

    public void setDepart(Timestamp depart) {
        this.depart = depart;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }
}
