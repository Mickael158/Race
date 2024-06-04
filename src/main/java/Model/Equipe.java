package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Equipe extends Etape{
    private int idEquipe;
    private String nomEquipe;
    private String email;
    private String pswd;
    private Time penalite;
    private int idPenalite;
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
    public void insert_Penalite_equipe(Equipe equipe, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into penalite ( idEtape, idEquipe, temps) VALUES (?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1 , equipe.getIdEtape());
                preparedStatement.setInt(2 , equipe.getIdEquipe());
                preparedStatement.setTime(3 , equipe.getPenalite());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void delet_Penalite_equipe(Equipe equipe, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from penalite where idPenalite=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1 , equipe.getIdPenalite());
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
    public List<Equipe> AllPenalite(Connection connection) throws SQLException, ClassNotFoundException {
        List<Equipe> equipes = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select *\n" +
                    "    from penalite\n" +
                    "        JOIN equipe ON penalite.idEquipe = equipe.idEquipe\n" +
                    "        join etape e on penalite.idEtape = e.idEtape;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Equipe equipe = new Equipe();
                    equipe.setIdPenalite(resultSet.getInt("idpenalite"));
                    equipe.setIdEquipe(resultSet.getInt("idequipe"));
                    equipe.setNomEquipe(resultSet.getString("nom"));
                    equipe.setIdEtape(resultSet.getInt("idetape"));
                    equipe.setLieu(resultSet.getString("lieu"));
                    equipe.setPenalite(resultSet.getTime("temps"));
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
    public Equipe penalite_by_equipe_by_etape(int idEquipe , int idEtape , Connection connection) throws SQLException, ClassNotFoundException {
        Equipe equipe = new Equipe();
        if (connection != null) {
            String selectQuery = "select idEtape , idEquipe , SUM(temps)\n" +
                    "    from penalite\n" +
                    "        where idEtape=? and idEquipe=?\n" +
                    "    group by idEtape, idEquipe;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEtape);
                preparedStatement.setInt(2 , idEquipe);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    equipe.setIdEtape(resultSet.getInt("idEtape"));
                    equipe.setIdEquipe(resultSet.getInt("idEquipe"));
                    equipe.setPenalite(resultSet.getTime("temps"));
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

    public int getIdPenalite() {
        return idPenalite;
    }

    public void setIdPenalite(int idPenalite) {
        this.idPenalite = idPenalite;
    }

    public Time getPenalite() {
        return penalite;
    }

    public void setPenalite(Time penalite) {
        this.penalite = penalite;
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
