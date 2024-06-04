package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class Main {
    /*public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        ConnexionBDD connexionBDD = new ConnexionBDD();
        Connection connection = connexionBDD.connect();
        Classement classement = new Classement();
        Timestamp s = classement.addSecond(Timestamp.valueOf("2024-06-01 09:00:00"), (int) 1717235643.000000);
        System.out.println(s);
        connection.close();
    }*/
    public static void main(String[] args) {
        double secondes = 60.0;
        String heure = convertirEnTemps(secondes);
        System.out.println("Heure correspondante: " + heure);
    }

    public static String convertirEnTemps(double secondes) {
        int heures = (int) (secondes / 3600);
        int minutes = (int) ((secondes % 3600) / 60);
        int secondesRestantes = (int) (secondes % 60);

        return String.format("%02d:%02d:%02d", heures, minutes, secondesRestantes);
    }
}
