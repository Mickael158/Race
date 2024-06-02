package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Point {
    private int idPoint;
    private int place;
    private int point;

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
