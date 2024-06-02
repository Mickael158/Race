package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Coureur extends Genre{
    private int idCoureur;
    private String numero;
    private String nom;
    private Date dtn;
    private int Genre;
    private int idEquipe;

    public void insertComposition_etape(Coureur coureur, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into Composition_etape ( idEtape, idEquipe, idCoureur) VALUES (?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, coureur.getIdEtape());
                preparedStatement.setInt(2, coureur.getIdEquipe());
                preparedStatement.setInt(3, coureur.getIdCoureur());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public List<Coureur> coureurs_non_Composer_by_equipe(int idEquipe,int idEtape, Connection connection) throws SQLException, ClassNotFoundException {
        List<Coureur> coureurs = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe\n" +
                    "    from coureur\n" +
                    "         join genre on genre.idGenre=coureur.idGenre\n" +
                    "                where idEquipe=? and idCoureur not in (\n" +
                    "                    select Composition_etape.idCoureur\n" +
                    "                        from Composition_etape\n" +
                    "                                 join coureur on coureur.idCoureur=Composition_etape.idCoureur\n" +
                    "                                 join equipe on equipe.idEquipe=Composition_etape.idEquipe\n" +
                    "                                 join etape on etape.idEtape=Composition_etape.idEtape\n" +
                    "                        where etape.idEtape=? and equipe.idEquipe=?\n" +
                    "                                                        );";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEquipe);
                preparedStatement.setInt(2 , idEtape);
                preparedStatement.setInt(3 , idEquipe);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Coureur coureur = new Coureur();
                    coureur.setIdCoureur(resultSet.getInt("idcoureur"));
                    coureur.setNumero(resultSet.getString("numero"));
                    coureur.setNom(resultSet.getString("nom"));
                    coureur.setDtn(resultSet.getDate("dtn"));
                    coureur.setNomGenre(resultSet.getString(5));
                    coureur.setIdEquipe(resultSet.getInt("idequipe"));
                    coureurs.add(coureur);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return coureurs;
    }
    public List<Coureur> coureurs_Composer_by_equipe(int idEquipe,int idEtape, Connection connection) throws SQLException, ClassNotFoundException {
        List<Coureur> coureurs = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe\n" +
                    "    from Composition_etape\n" +
                    "        join coureur on coureur.idCoureur=Composition_etape.idCoureur\n" +
                    "        join genre on genre.idGenre=coureur.idGenre\n" +
                    "        join equipe on equipe.idEquipe=Composition_etape.idEquipe\n" +
                    "        join etape on etape.idEtape=Composition_etape.idEtape\n" +
                    "where etape.idEtape=? and equipe.idEquipe=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEtape);
                preparedStatement.setInt(2 , idEquipe);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Coureur coureur = new Coureur();
                    coureur.setIdCoureur(resultSet.getInt("idcoureur"));
                    coureur.setNumero(resultSet.getString("numero"));
                    coureur.setNom(resultSet.getString("nom"));
                    coureur.setDtn(resultSet.getDate("dtn"));
                    coureur.setNomGenre(resultSet.getString(5));
                    coureur.setIdEquipe(resultSet.getInt("idequipe"));
                    coureurs.add(coureur);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return coureurs;
    }
    public List<Coureur> coureurs_Composer(int idEtape, Connection connection) throws SQLException, ClassNotFoundException {
        List<Coureur> coureurs = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe\n" +
                    "    from Composition_etape\n" +
                    "        join coureur on coureur.idCoureur=Composition_etape.idCoureur\n" +
                    "        join genre on genre.idGenre=coureur.idGenre\n" +
                    "        join equipe on equipe.idEquipe=Composition_etape.idEquipe\n" +
                    "        join etape on etape.idEtape=Composition_etape.idEtape\n" +
                    "where etape.idEtape=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEtape);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Coureur coureur = new Coureur();
                    coureur.setIdCoureur(resultSet.getInt("idcoureur"));
                    coureur.setNumero(resultSet.getString("numero"));
                    coureur.setNom(resultSet.getString("nom"));
                    coureur.setDtn(resultSet.getDate("dtn"));
                    coureur.setNomGenre(resultSet.getString(5));
                    coureur.setIdEquipe(resultSet.getInt("idequipe"));
                    coureurs.add(coureur);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return coureurs;
    }
    public Coureur coureurs_by_numero(String numero, Connection connection) throws SQLException, ClassNotFoundException {
        Coureur coureur = new Coureur();
        if (connection != null) {
            String selectQuery = "select coureur.idCoureur , coureur.numero , coureur.nom , coureur.dtn , genre.nom , coureur.idEquipe\n" +
                    "from coureur\n" +
                    "         join genre on genre.idGenre=coureur.idGenre\n" +
                    "where numero=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1 , numero);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    coureur.setIdCoureur(resultSet.getInt("idcoureur"));
                    coureur.setNumero(resultSet.getString("numero"));
                    coureur.setNom(resultSet.getString("nom"));
                    coureur.setDtn(resultSet.getDate("dtn"));
                    coureur.setNomGenre(resultSet.getString(5));
                    coureur.setIdEquipe(resultSet.getInt("idequipe"));
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return coureur;
    }

    public int getIdCoureur() {
        return idCoureur;
    }

    public void setIdCoureur(int idCoureur) {
        this.idCoureur = idCoureur;
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

    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    public int getGenre() {
        return Genre;
    }

    public void setGenre(int genre) {
        Genre = genre;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }
}
