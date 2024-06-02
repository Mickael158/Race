package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int idUser;
    private String nom;
    private String prenom;
    private String email;
    private  String pswd;

    public User checkAdmin(String email , String pswd, Connection connection) throws SQLException, ClassNotFoundException {
        User userAdmin = new User();
        if (connection != null) {
            String selectQuery = "SELECT * FROM UserAdmin WHERE email=? and pswd=?; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1 , email);
                preparedStatement.setString(2 , pswd);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    userAdmin.setIdUser(resultSet.getInt("iduser"));
                    userAdmin.setNom(resultSet.getString("nom"));
                    userAdmin.setPrenom(resultSet.getString("prenom"));
                    userAdmin.setEmail(resultSet.getString("email"));
                    userAdmin.setPswd(resultSet.getString("pswd"));
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return userAdmin;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
