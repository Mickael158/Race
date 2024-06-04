package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Equipe extends Etape{
    private int idEquipe;
    private String nomEquipe;
    private String email;
    private String pswd;

    public Equipe checkEquipe(String email , String pswd, Connection connection) throws SQLException, ClassNotFoundException {
        Equipe equipe = new Equipe();
        if (connection != null) {
            String selectQuery = "select * from equipe WHERE email=? and pswd=?; ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1 , email);
                preparedStatement.setString(2 , pswd);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    equipe.setIdEquipe(resultSet.getInt("idequipe"));
                    equipe.setNomEquipe(resultSet.getString("nom"));
                    equipe.setEmail(resultSet.getString("email"));
                    equipe.setPswd(resultSet.getString("pswd"));
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return equipe;
    }
    public void delet_Equipe( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from equipe";
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
    public List<Equipe> AllEquipe(Connection connection) throws SQLException, ClassNotFoundException {
        List<Equipe> equipes = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select * from equipe;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Equipe equipe = new Equipe();
                    equipe.setIdEquipe(resultSet.getInt("idequipe"));
                    equipe.setNomEquipe(resultSet.getString("nom"));
                    equipe.setEmail(resultSet.getString("email"));
                    equipe.setPswd(resultSet.getString("pswd"));
                    equipes.add(equipe);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return equipes;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
