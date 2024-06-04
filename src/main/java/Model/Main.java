package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        ConnexionBDD connexionBDD = new ConnexionBDD();
        Connection connection = connexionBDD.connect();
        Etape etape = new Etape();
        Classement classement = new Classement();
        Resultat resultat = new Resultat();
        Coureur coureur = new Coureur();
        classement.reinitialisation_Classement(connection);
        List<Etape> etapes = etape.AllEtape(connection);
        List<Resultat> resultats = resultat.temps_coureur_by_equipe(18, connection);
        List<Coureur> coureurs = coureur.coureurs_Composer_by_equipe(18, connection);
        for (Etape et : etapes){
            for (Resultat re : resultats){
                if ( re.getIdEtape()==et.getIdEtape()){
                    System.out.println(et.getLieu());
                    System.out.println("---------------------------------------------------------");
                    System.out.println("coureur :"+re.getNom()+" || temps :"+re.getFin());
                }
            }
            for (Coureur cour : coureurs){
                if (cour.getIdEtape() == et.getIdEtape()){
                    System.out.println(et.getLieu());
                    System.out.println("---------------------------------------------------------");
                    System.out.println("coureur :"+cour.getNom()+" || temps : null");
                }
            }
        }
    }
}
