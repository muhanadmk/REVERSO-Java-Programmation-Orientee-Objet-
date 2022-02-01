package fr.afpa.pompey.cda08.demo;
import fr.afpa.pompey.cda08.demo.com.company.DAO.ConnexionManager;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoClient;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoContrat;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoSqlEx;
import fr.afpa.pompey.cda08.demo.com.company.vues.Accueil;

public class App {
    public static void main(String[] args) {
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
    }
}
