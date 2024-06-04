package Model;

import Traitement.Donne;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Point {
    private int idPoint;
    private int place;
    private int point;

    public void insertPoint(Point point , Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "insert into points ( place, point) VALUES (?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, point.getPlace());
                preparedStatement.setInt(2, point.getPoint());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête INSERT : " + e.getMessage());
            }
        }
    }

    public List<Point> AllPoint( Connection connection) throws SQLException, ClassNotFoundException {
        List<Point> points = new ArrayList<>();
        if (connection != null) {
            String selectQuery = "select * from points;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Point point = new Point();
                    point.setIdPoint(resultSet.getInt("idpoint"));
                    point.setPlace(resultSet.getInt("place"));
                    point.setPoint(resultSet.getInt("point"));
                    points.add(point);
                }

                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SELECT : " + e.getMessage());
            }
        }
        return points;
    }
    public void delet_Point( Connection connection) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            String insertQuery = "delete from points";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                System.out.println("Point Ok");
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
    public List<Point> findAll(String file) throws IOException, ParseException {
        List<Point> points = new ArrayList<>();
        Point point;
        String[] data_temp;
        List<String[]> str_data = getDonnee(Donne.data+file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");


        for (int i=0; i<str_data.size(); i++) {
            point = new Point();
            point.setPlace(Integer.parseInt(str_data.get(i)[0]));
            point.setPoint(Integer.parseInt(str_data.get(i)[1]));
            points.add(point);
        }
        return points;
    }
    public void insertAll_point(String file , Connection connection) throws SQLException, ClassNotFoundException, IOException, ParseException {
        List<Point> points = findAll(file);
        for (int i = 0 ; i < points.size() ; i++){
            insertPoint(points.get(i) , connection);
            System.out.println("Inserer :"+i);
        }
    }
    public int getIdPoint() {
        return idPoint;
    }

    public void setIdPoint(int idPoint) {
        this.idPoint = idPoint;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
