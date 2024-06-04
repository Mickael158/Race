package Traitement;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Donne {

    public final static String data = "D:/Rojo/Eval/Race/Csv/";

    public static List<String> read(String filePath) throws IOException {
        System.out.println(filePath);
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }
}
