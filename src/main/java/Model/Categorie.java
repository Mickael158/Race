package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Categorie extends Coureur {
    private int idCategorie;
    private String nom_Categorie;
    private int age;

    public void insertCategorie_Coureur(Categorie categorie, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into coureurs_categories ( idCoureur, idCategories) VALUES (?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, categorie.getIdCoureur());
                preparedStatement.setInt(2, categorie.getIdCategorie());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public List<Categorie> AllType_Coureur(Connection connection) throws SQLException, ClassNotFoundException {
        List<Categorie> categories = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select idCoureur , EXTRACT(YEAR FROM age(CURRENT_DATE, dtn)) AS age , genre.idGenre\n" +
                    "    from coureur\n" +
                    "        join genre on genre.idGenre=coureur.idGenre; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Categorie categorie = new Categorie();
                    categorie.setIdCoureur(resultSet.getInt("idCoureur"));
                    categorie.setAge(resultSet.getInt("age"));
                    categorie.setIdGenre(resultSet.getInt("idGenre"));
                    categories.add(categorie);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return categories;
    }
    public List<Categorie> AllCategorie(Connection connection) throws SQLException, ClassNotFoundException {
        List<Categorie> categories = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select * from categories; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Categorie categorie = new Categorie();
                    categorie.setIdCategorie(resultSet.getInt("idcategorie"));
                    categorie.setNom_Categorie(resultSet.getString("nom"));
                    categories.add(categorie);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return categories;
    }
    public void generet_AllCategorie_Coureur(Connection connection) throws SQLException, ClassNotFoundException {
        List<Categorie> categories = AllType_Coureur(connection);
        for (Categorie categorie : categories){
            if (categorie.getAge() < 18){
                Categorie cat = new Categorie();
                cat.setIdCoureur(categorie.getIdCoureur());
                cat.setIdCategorie(3);
                insertCategorie_Coureur(cat , connection);
            }
            if (categorie.getIdGenre() == 1){
                Categorie cat = new Categorie();
                cat.setIdCoureur(categorie.getIdCoureur());
                cat.setIdCategorie(1);
                insertCategorie_Coureur(cat , connection);
            }
            if (categorie.getIdGenre() == 2){
                Categorie cat = new Categorie();
                cat.setIdCoureur(categorie.getIdCoureur());
                cat.setIdCategorie(2);
                insertCategorie_Coureur(cat , connection);
            }
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom_Categorie() {
        return nom_Categorie;
    }

    public void setNom_Categorie(String nom_Categorie) {
        this.nom_Categorie = nom_Categorie;
    }
}
