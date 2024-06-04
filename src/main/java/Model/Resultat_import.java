package Model;

import Traitement.Donne;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Resultat_import {
    private int idri;
    private int rang;
    private  String numero;
    private String nom;
    private String genre;
    private Date dtn;
    private String equipe;
    private Timestamp arriver;

    public void insertResultat_import(Resultat_import resultat_import , Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into resultat_import ( rang, numero, nom, genre, dtn, equipe, arriver) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, resultat_import.getRang());
                preparedStatement.setString(2, resultat_import.getNumero());
                preparedStatement.setString(3, resultat_import.getNom());
                preparedStatement.setString(4, resultat_import.getGenre());
                preparedStatement.setDate(5, resultat_import.getDtn());
                preparedStatement.setString(6, resultat_import.getEquipe());
                preparedStatement.setTimestamp(7, resultat_import.getArriver());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void insertEquipe_import( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into equipe ( nom, email, pswd) (SELECT DISTINCT equipe, CONCAT('equipe', equipe, '@donne.com') AS equipe_email, CONCAT('azerty' , equipe) as pwd FROM resultat_import)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Equipe Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void insertCoureur_import( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into coureur ( numero, nom, dtn, idGenre, idEquipe) (SELECT DISTINCT resultat_import.numero, resultat_import.nom, resultat_import.dtn, genre.idGenre,  equipe.idEquipe FROM resultat_import JOIN genre ON resultat_import.genre = genre.anotation JOIN equipe ON resultat_import.equipe = equipe.nom);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Coureur Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void insertComposition_etape_import( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into Composition_etape ( idEtape, idEquipe, idCoureur) (select distinct etape.idEtape , coureur.idEquipe , coureur.idCoureur from resultat_import join etape on etape.rang=resultat_import.rang join coureur on coureur.nom=resultat_import.nom)";
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
    public void insertResultat_officile_etape_import( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into resultat_etape ( idCoureur, idetape, fin) (select distinct coureur.idCoureur , etape.idEtape , resultat_import.arriver from resultat_import join coureur on coureur.nom=resultat_import.nom join etape on etape.rang=resultat_import.rang)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Resultat Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void delet_Resultat_import( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from resultat_import";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Resultat import Ok");
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
    public List<Resultat_import> findAll(String file) throws IOException, ParseException {
        List<Resultat_import> resultat_imports = new ArrayList<>();
        Resultat_import resultat_import;
        String[] data_temp;
        List<String[]> str_data = getDonnee(Donne.data+file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");


        for (int i=0; i<str_data.size(); i++) {
            resultat_import = new Resultat_import();
            resultat_import.setRang(Integer.parseInt(str_data.get(i)[0]));
            resultat_import.setNumero(str_data.get(i)[1]);
            resultat_import.setNom(str_data.get(i)[2]);
            resultat_import.setGenre(str_data.get(i)[3]);
            resultat_import.setDtn(Date.valueOf(convertDateFormat(str_data.get(i)[4])));
            resultat_import.setEquipe(str_data.get(i)[5]);
            resultat_import.setArriver(Timestamp.valueOf(convertDateTime(str_data.get(i)[6])));
            resultat_imports.add(resultat_import);
        }
        return resultat_imports;
    }
    public String convertDateFormat(String inputDate) {
        // Créer un formateur de date pour le format d'entrée
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Parse la date d'entrée en tant qu'objet LocalDate
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        // Créer un formateur de date pour le format de sortie
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Formater la date en utilisant le format de sortie
        return date.format(outputFormatter);
    }

    public static String convertDateTime(String dateTime) {
        // Définir le format d'entrée
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // Définir le format de sortie
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            // Analyser la chaîne de date et heure en utilisant le format d'entrée
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, inputFormatter);
            // Formater la date et heure dans le format de sortie
            return parsedDateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // Gérer les erreurs de parsing
            System.out.println("Erreur de parsing de la date et heure : " + e.getMessage());
            return null;
        }
    }
    public void insertAll_resultat_import(String file , Connection connection) throws SQLException, ClassNotFoundException, IOException, ParseException {
        List<Resultat_import> resultat_imports = findAll(file);
        for (int i = 0 ; i < resultat_imports.size() ; i++){
            insertResultat_import(resultat_imports.get(i) , connection);
            System.out.println("Inserer :"+i);
        }
    }
    public int getIdri() {
        return idri;
    }

    public void setIdri(int idri) {
        this.idri = idri;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public Timestamp getArriver() {
        return arriver;
    }

    public void setArriver(Timestamp arriver) {
        this.arriver = arriver;
    }
}
