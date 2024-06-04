package Model;

import Traitement.Donne;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Etape {
    private int idEtape;
    private int rang;
    private int nbr_Coureur_Equipe;
    private Timestamp depart;
    private  String lieu;
    private double longueur;

    public void insertEtape(Etape etape, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into etape ( rang, nbr_Coureur_Equipe, depart, lieu, longueur) VALUES (?,?,?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, etape.getRang());
                preparedStatement.setInt(2, etape.getNbr_Coureur_Equipe());
                preparedStatement.setTimestamp(3, etape.getDepart());
                preparedStatement.setString(4, etape.getLieu());
                preparedStatement.setDouble(5, etape.getLongueur());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public static List<String[]> getDonnee(String file){
        List<String[]> valiny  = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                valiny.add(nextLine);
            }
            return valiny;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public void delet_Composition( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from Composition_etape";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Composition Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void delet_Etape( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from etape;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Etape Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public String convertNumberFormat(String inputNumber) {
        // Remplacer la virgule par un point
        String formattedNumber = inputNumber.replace(',', '.');
        return formattedNumber;
    }
    public List<Etape> findAll(String file) throws IOException, ParseException {
        List<Etape> etapes = new ArrayList<>();
        Etape etape;
        String[] data_temp;
        List<String[]> str_data = getDonnee(Donne.data+file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");


        for (int i=0; i<str_data.size(); i++) {
            etape = new Etape();
            etape.setLieu(str_data.get(i)[0]);
            etape.setLongueur(Double.parseDouble(convertNumberFormat(str_data.get(i)[1])));
            etape.setNbr_Coureur_Equipe(Integer.parseInt(str_data.get(i)[2]));
            etape.setRang(Integer.parseInt(str_data.get(i)[3]));
            etape.setDepart(ensemblage_date_time(str_data.get(i)[4] , str_data.get(i)[5]));
            etapes.add(etape);
        }
        return etapes;
    }
    public void insertAll_etape_import(String file , Connection connection) throws SQLException, ClassNotFoundException, IOException, ParseException {
        List<Etape> etapes = findAll(file);
        for (int i = 0 ; i < etapes.size() ; i++){
            insertEtape(etapes.get(i) , connection);
            System.out.println("Inserer :"+i);
        }
    }
    public Timestamp ensemblage_date_time(String dateStr , String timeStr){
        String dateTimeStr = dateStr + " " + timeStr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime localDateTime =  LocalDateTime.parse(dateTimeStr , formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Timestamp.from(instant);
    }
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

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
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
