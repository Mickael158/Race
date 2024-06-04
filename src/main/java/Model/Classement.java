package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Classement extends Categorie{
    private int idClassement;
    private int idResultat;
    private int place;
    private int idCoureur;
    private int idEtape;
    private int idCategorie;
    private Timestamp debut;
    private Timestamp fin;
    private double diffTemps;
    private int point;

    private Timestamp temps_final;
    public void delet_classement( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from classement_etape";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Classement Ok");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }
    public List<Classement> classements_by_etape_by_categorie(int idEtape,int idCategorie, Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select classement_etape.idClassement, coureur.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, classement_etape.idetape, etape.lieu, classement_etape.fin, classement_etape.debut, EXTRACT(EPOCH FROM (classement_etape.tfinal - classement_etape.debut)) AS time_difference , classement_etape.point , classement_etape.place , classement_etape.penalite\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "        join etape on etape.idEtape=classement_etape.idEtape\n" +
                    "    where classement_etape.idEtape=? and classement_etape.idCategorie=?\n" +
                    "    ORDER BY point DESC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEtape);
                preparedStatement.setInt(2 , idCategorie);
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
                    classement.setPlace(resultSet.getInt("place"));
                    classement.setPenalite(resultSet.getTime("penalite"));
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

    public List<Classement> classements_by_equipe_by_categorie(int idCategorie , int idEquipe, Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select classement_etape.idClassement, coureur.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, classement_etape.idetape, etape.lieu, classement_etape.fin, classement_etape.debut, EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)) AS time_difference , classement_etape.point , classement_etape.place\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "        join etape on etape.idEtape=classement_etape.idEtape\n" +
                    "    where equipe.idEquipe=? and classement_etape.idCategorie=?\n" +
                    "    ORDER BY point DESC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1 , idEquipe);
                preparedStatement.setInt(2 , idCategorie);
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
                    classement.setPlace(resultSet.getInt("place"));
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

    public List<Classement> classements_group_by_equipe_by_categorie(int idCategorie,  Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "SELECT\n" +
                    "    equipe.idEquipe,\n" +
                    "    equipe.nom,\n" +
                    "    SUM(classement_etape.point) AS point,\n" +
                    "    SUM(EXTRACT(EPOCH FROM (classement_etape.tfinal - classement_etape.debut))) AS time_difference,\n" +
                    "    SUM(etape.longueur) AS sum_longueur,\n" +
                    "    SUM(EXTRACT(EPOCH FROM classement_etape.penalite)) AS penalite\n" +
                    "FROM\n" +
                    "    classement_etape\n" +
                    "        JOIN\n" +
                    "    coureur ON coureur.idCoureur = classement_etape.idCoureur\n" +
                    "        JOIN\n" +
                    "    equipe ON equipe.idEquipe = coureur.idEquipe\n" +
                    "        JOIN\n" +
                    "    etape ON classement_etape.idEtape = etape.idEtape\n" +
                    "WHERE\n" +
                    "        classement_etape.idCategorie = ?\n" +
                    "GROUP BY\n" +
                    "    equipe.idEquipe\n" +
                    "ORDER BY\n" +
                    "    point DESC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, idCategorie);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Classement classement = new Classement();
                    classement.setIdEquipe(resultSet.getInt("idequipe"));
                    classement.setNomEquipe(resultSet.getString("nom"));
                    classement.setPoint(resultSet.getInt("point"));
                    classement.setLongueur(resultSet.getInt("sum_longueur"));
                    classement.setDiffTemps(resultSet.getDouble("time_difference"));
                    classement.setPenalite(Time.valueOf(secondesVersHeure(resultSet.getDouble("penalite"))));
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
    public static String secondesVersHeure(double secondes) {
        int heures = (int) (secondes / 3600);
        int minutes = (int) ((secondes % 3600) / 60);
        int secondesRestantes = (int) (secondes % 60);

        return String.format("%02d:%02d:%02d", heures, minutes, secondesRestantes);
    }

    public List<Classement> classements_group_by_equipe_by_equipe_by_categorie(int idequipe,int idCategorie ,  Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select equipe.idEquipe , equipe.nom,  SUM(classement_etape.point) as point , SUM(EXTRACT(EPOCH FROM (classement_etape.fin - classement_etape.debut)))AS time_difference\n" +
                    "    from classement_etape\n" +
                    "        join coureur on coureur.idCoureur=classement_etape.idCoureur\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "    where equipe.idEquipe=? and classement_etape.idCategorie=?\n" +
                    "    Group By equipe.idEquipe\n" +
                    "    Order By point DESC;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, idequipe);
                preparedStatement.setInt(2, idCategorie);
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

    public List<Classement> select_resultats_by_diff_temps_by_categorie(Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "SELECT re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe , equipe.nom, re.idetape, re.fin, e.depart, EXTRACT(EPOCH FROM (re.fin - e.depart)) AS time_difference , coureurs_categories.idCategories , categories.nom , EXTRACT(EPOCH FROM (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval))) AS time_difference_final , COALESCE(SUM(penalite.temps), '00:00:00'::interval) AS penalite_t\n" +
                    "    FROM resultat_etape re\n" +
                    "        JOIN etape e ON re.idetape = e.idEtape\n" +
                    "        join coureur on coureur.idCoureur=re.idCoureur\n" +
                    "        join coureurs_categories on coureurs_categories.idCoureur = coureur.idCoureur\n" +
                    "        join categories on categories.idCategorie = coureurs_categories.idCategories\n" +
                    "        join equipe on equipe.idEquipe=coureur.idEquipe\n" +
                    "        left join penalite on penalite.idEquipe=equipe.idEquipe and penalite.idEtape=e.idEtape\n" +
                    "    GROUP BY re.idResultat, re.idCoureur, coureur.nom,equipe.idEquipe, re.idetape, e.depart , coureurs_categories.idCategories , categories.nom\n" +
                    "    ORDER BY time_difference_final Asc ;";
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
                    classement.setIdCategorie(resultSet.getInt("idCategories"));
                    classement.setNom_Categorie(resultSet.getString(11));
                    classement.setTemps_final(addSecond(resultSet.getTimestamp("fin") , resultSet.getDouble("time_difference_final")));
                    classement.setPenalite(resultSet.getTime("penalite_t"));
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

    public List<Classement> select_resultats_by_diff_temps_All_categorie(Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classements = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "SELECT \n" +
                    "    re.idResultat, \n" +
                    "    re.idCoureur, \n" +
                    "    coureur.nom, \n" +
                    "    equipe.idEquipe, \n" +
                    "    equipe.nom, \n" +
                    "    re.idetape, \n" +
                    "    re.fin, \n" +
                    "    e.depart, \n" +
                    "    EXTRACT(EPOCH FROM (re.fin - e.depart)) AS time_difference, \n" +
                    "    EXTRACT(EPOCH FROM (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval))) AS time_difference_final, \n" +
                    "    COALESCE(SUM(penalite.temps), '00:00:00'::interval) AS penalite_t,\n" +
                    "    (re.fin + COALESCE(SUM(penalite.temps), '00:00:00'::interval)) AS final_time_with_penalty\n" +
                    "FROM \n" +
                    "    resultat_etape re\n" +
                    "JOIN \n" +
                    "    etape e ON re.idetape = e.idEtape\n" +
                    "JOIN \n" +
                    "    coureur ON coureur.idCoureur = re.idCoureur\n" +
                    "JOIN \n" +
                    "    equipe ON equipe.idEquipe = coureur.idEquipe\n" +
                    "LEFT JOIN \n" +
                    "    penalite ON penalite.idEquipe = equipe.idEquipe AND penalite.idEtape = e.idEtape\n" +
                    "GROUP BY \n" +
                    "    re.idResultat, re.idCoureur, coureur.nom, equipe.idEquipe, re.idetape, e.depart\n" +
                    "ORDER BY \n" +
                    "    time_difference_final ASC;\n";
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
                    classement.setTemps_final(resultSet.getTimestamp("final_time_with_penalty"));
                    classement.setPenalite(resultSet.getTime("penalite_t"));
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
    public static Timestamp addSecond(Timestamp timestamp, double seconde) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());

        // Convertir les secondes en millisecondes
        long millisecondsToAdd = (long) (seconde * 1000);

        // Ajouter les millisecondes au calendrier
        calendar.add(Calendar.MILLISECOND, (int) millisecondsToAdd);

        return new Timestamp(calendar.getTimeInMillis());
    }
    public List<Classement> filtre_classements_by_etape(int idEtape ,Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classementList = select_resultats_by_diff_temps_All_categorie(connection);
        List<Classement> classements = new ArrayList<>();
        for (Classement classement : classementList){
            if(classement.getIdEtape() == idEtape){
                classements.add(classement);
            }
        }
        return classements;
    }
    public List<Classement> filtre_classements_by_etape_by_categorie(int idCategorie ,int idEtape , Connection connection) throws SQLException, ClassNotFoundException {
        List<Classement> classementList = select_resultats_by_diff_temps_by_categorie(connection);
        List<Classement> classements = new ArrayList<>();
        for (Classement classement : classementList){
            if(classement.getIdCategorie() == idCategorie && classement.getIdEtape()== idEtape){
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
            String insertQuery = "insert into classement_etape ( idresultat, place, idCategorie, idCoureur, idEtape, debut, fin, point, penalite, tfinal) VALUES \n" +
                    "                            (?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, classement.getIdResultat());
                preparedStatement.setInt(2, classement.getPlace());
                preparedStatement.setInt(3, classement.getIdCategorie());
                preparedStatement.setInt(4, classement.getIdCoureur());
                preparedStatement.setInt(5, classement.getIdEtape());
                preparedStatement.setTimestamp(6, classement.getDebut());
                preparedStatement.setTimestamp(7, classement.getFin());
                preparedStatement.setInt(8, classement.getPoint());
                preparedStatement.setTime(9, classement.getPenalite());
                preparedStatement.setTimestamp(10, classement.getTemps_final());

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
                System.out.println("Classement etape OK");
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
            List<Classement> filtre = filtre_classements_by_etape(etape.getIdEtape() , connection);
            List<Classement> place = classements_place(filtre);
            List<Classement> point = classements_final(place , connection);
            insertAllClassement(point , connection);
            System.out.println("All");
        }
        Categorie categorie = new Categorie();
        List<Categorie> categories = categorie.AllCategorie(connection);
        for (Etape etape : etapeList){
            for (Categorie cat : categories){
                List<Classement> filtre = filtre_classements_by_etape_by_categorie(cat.getIdCategorie() , etape.getIdEtape() , connection);
                List<Classement> place = classements_place(filtre);
                List<Classement> point = classements_final(place , connection);
                insertAllClassement(point , connection);
                System.out.println("by");
            }
        }
    }


    public Timestamp getTemps_final() {
        return temps_final;
    }

    public void setTemps_final(Timestamp temps_final) {
        this.temps_final = temps_final;
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

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
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
