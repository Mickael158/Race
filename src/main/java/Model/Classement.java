package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Classement extends Coureur{
    private int idClassement;
    private int idResultat;
    private int place;
    private int idCoureur;
    private int idEtape;
    private Timestamp debut;
    private Timestamp fin;
    private double diffTemps;
    private int point;

    public List<Classement> classements_by_etape(int idEtape, Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select classement_etape.idClassement, coureur.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, classement_etape.idetape, etape.lieu, classement_etape.fin, classement_etape.debut, EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)) AS time_difference , classement_etape.point\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "        join etape on etape.idEtape=classement_etape.idEtape\n" +
                    "    where classement_etape.idEtape=?\n" +
                    "    ORDER BY point ASC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEtape);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Classement classement = new Classement();
                    classement.setIdClassement(resultSet.getInt("idclassement"));
                    classement.setIdCoureur(resultSet.getInt("idcoureur"));
                    classement.setNom(resultSet.getString(3));
                    classement.setIdEquipe(resultSet.getInt("idequipe"));
                    classement.setNomEquipe(resultSet.getString(5));
                    classement.setIdEtape(resultSet.getInt("idetape"));
                    classement.setLieu(resultSet.getString("lieu"));
                    classement.setFin(resultSet.getTimestamp("fin"));
                    classement.setDebut(resultSet.getTimestamp("debut"));
                    classement.setDiffTemps(resultSet.getDouble("time_difference"));
                    classement.setPoint(resultSet.getInt("point"));
                    classements.add(classement);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return classements;
    }

    public List<Classement> classements_group_by_equipe( Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select equipe.idEquipe , equipe.nom,  SUM(classement_etape.point) as point , SUM(EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)))AS time_difference\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "    Group By equipe.idEquipe\n" +
                    "    Order By point ASC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Classement classement = new Classement();
                    classement.setIdEquipe(resultSet.getInt("idequipe"));
                    classement.setNomEquipe(resultSet.getString("nom"));
                    classement.setPoint(resultSet.getInt("point"));
                    classement.setDiffTemps(resultSet.getInt("time_difference"));
                    classements.add(classement);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return classements;
    }

    public List<Classement> classements_group_by_equipe_by_equipe(int idequipe, Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select equipe.idEquipe , equipe.nom,  SUM(classement_etape.point) as point , SUM(EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)))AS time_difference\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "    where equipe.idEquipe=?\n" +
                    "    Group By equipe.idEquipe\n" +
                    "    Order By point ASC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, idequipe);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Classement classement = new Classement();
                    classement.setIdEquipe(resultSet.getInt("idequipe"));
                    classement.setNomEquipe(resultSet.getString("nom"));
                    classement.setPoint(resultSet.getInt("point"));
                    classement.setDiffTemps(resultSet.getInt("time_difference"));
                    classements.add(classement);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return classements;
    }


    public List<Classement> select_resultats_by_diff_temps(Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "SELECT re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, re.idetape, re.fin, e.depart, EXTRACT(EPOCH FROM (re.fin - e.depart)) AS time_difference\n" +
                    "    FROM resultat_etape re\n" +
                    "        JOIN etape e ON re.idetape = e.idEtape\n" +
                    "        join coureur on coureur.idCoureur=re.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "    ORDER BY time_difference ASC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Classement classement = new Classement();
                    classement.setIdResultat(resultSet.getInt("idresultat"));
                    classement.setIdCoureur(resultSet.getInt("idcoureur"));
                    classement.setNom(resultSet.getString(3));
                    classement.setIdEquipe(resultSet.getInt("idequipe"));
                    classement.setNomEquipe(resultSet.getString(5));
                    classement.setIdEtape(resultSet.getInt("idetape"));
                    classement.setFin(resultSet.getTimestamp("fin"));
                    classement.setDebut(resultSet.getTimestamp("depart"));
                    classement.setDiffTemps(resultSet.getDouble("time_difference"));
                    classements.add(classement);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return classements;
    }
    public List<Classement> filtre_classements_by_equipe(int idEtape ,Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classementList = select_resultats_by_diff_temps(connection);
        List<Classement> classements = new ArrayList<>();
        for (Classement classement : classementList){
            if(classement.getIdEtape() == idEtape){
                classements.add(classement);
            }
        }
        return classements;
    }

    public List<Classement> classements_place(List<Classement> classementList) throws SQLException, ClassNotFoundException {
        for (int i = 0 ; i < classementList.size() ; i++){
            if (i == 0){
                classementList.get(i).setPlace(i+1);
            }
            if (i >= 1){
                if (classementList.get(i).getDiffTemps() == classementList.get(i-1).getDiffTemps()){
                    classementList.get(i).setPlace(classementList.get(i-1).getPlace());
                }
                else {
                    classementList.get(i).setPlace(classementList.get(i-1).getPlace()+1);
                }
            }
        }
        return classementList;
    }
    public List<Classement> classements_final(List<Classement> classementList , Connection connection) throws SQLException, ClassNotFoundException {
        Point point = new Point();
        List<Point> pointList = point.AllPoint(connection);
        for (int i = 0 ; i < classementList.size() ; i++){
            for (int j = 0 ; j < pointList.size() ; j++){
                if (classementList.get(i).getPlace() == pointList.get(j).getPlace()){
                    classementList.get(i).setPoint(pointList.get(j).getPoint());
                }
            }
        }
        return classementList;
    }
    public void insertClassement(Classement classement, Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into classement_etape ( idresultat, place, idCoureur, idEtape, debut, fin, point) VALUES \n" +
                    "    (?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, classement.getIdResultat());
                preparedStatement.setInt(2, classement.getPlace());
                preparedStatement.setInt(3, classement.getIdCoureur());
                preparedStatement.setInt(4, classement.getIdEtape());
                preparedStatement.setTimestamp(5, classement.getDebut());
                preparedStatement.setTimestamp(6, classement.getFin());
                preparedStatement.setInt(7, classement.getPoint());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }

    public void delete_Classement( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from classement_etape;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);


                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public void insertAllClassement(List<Classement> classements, Connection connection) throws SQLException, ClassNotFoundException {
        for (int i = 0 ; i < classements.size() ; i++){
            insertClassement(classements.get(i) , connection);
        }
    }
    public void reinitialisation_Classement(Connection connection) throws SQLException, ClassNotFoundException {
        delete_Classement(connection);
        Etape et = new Etape();
        List<Etape> etapeList = et.AllEtape(connection);
        for (Etape etape : etapeList){
            List<Classement> filtre = filtre_classements_by_equipe(etape.getIdEtape() , connection);
            List<Classement> place = classements_place(filtre);
            List<Classement> point = classements_final(place , connection);
            insertAllClassement(point , connection);
        }
    }

    public double getDiffTemps() {
        return diffTemps;
    }

    public void setDiffTemps(double diffTemps) {
        this.diffTemps = diffTemps;
    }

    public int getIdClassement() {
        return idClassement;
    }

    public void setIdClassement(int idClassement) {
        this.idClassement = idClassement;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getIdCoureur() {
        return idCoureur;
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
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

    public Timestamp getDebut() {
        return debut;
    }

    public void setDebut(Timestamp debut) {
        this.debut = debut;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}