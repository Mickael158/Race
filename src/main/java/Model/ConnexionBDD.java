package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/race";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345678";

    // Méthode pour établir une connexion à la base de données PostgreSQL
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
