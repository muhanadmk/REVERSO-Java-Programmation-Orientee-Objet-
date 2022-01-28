package fr.afpa.pompey.cda08.demo;


import fr.afpa.pompey.cda08.demo.com.company.DAO.ConnexionManager;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoClient;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoProspect;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoSqlEx;
import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;

import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import fr.afpa.pompey.cda08.demo.com.company.vues.Accueil;


import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.DataFormatException;


public class App {
    public static void main(String[] args) throws ExceptionMetier, IOException, SQLException, DaoSqlEx {
        Client gagging = new Client(0, "ttttt", "mouhandmk1@gmail.com", "789553598",
                "hhhh",
                new Address("79", "Bld de la Liberation", "54000",
                        "Lille"), 2000, 55);
        Client f1666 = new Client(0,"pppppppppp", "mouhandmk1@gmail.com", "789553598",
                "hhhh",
                new Address("79", "Bld de la Liberation", "54000",
                        "Lille"), 2000, 55);
        Client f2 = new Client(0,"uuu ", "mouhandmk1@gmail.com", "789553598",
                "hhhh",
                new Address("55", "mouhandmk1@gmail.com", "54000",
                        "nancy"), 222, 55);
        Client f3 = new Client(0,"ahmed", "mouhandmk1@gmail.com", "7895553598",
                "hhhh",
                new Address("55", "mouhandmk1@gmail.com", "54000",
                        "Lill"), 400, 55);

        Prospect g1 = new Prospect(0,"Prospect 111", "mouhandmk1@gmail.com", "555566976876876",
                "hhhh",
                new Address("79", "Bld de la Liberation", "54000",
                        "Lille"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.NON);
        Prospect g22 = new Prospect(0,"Prospect 22", "mouhandmk1@gmail.com", "789555553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "Lill"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.OUI);

        Prospect g3 = new Prospect(0, "Prospect 9999999", "mouhandmk1@gmail.com", "78955553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "Lill"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.NON);

       // DaoClient.delete(ConnexionManager.getConnexionBD(), 5);
        Accueil accueil = new Accueil();
        accueil.setVisible(true);

        //g3.setId(5);
      /*  DaoProspect.save(ConnexionManager.getConnexionBD(), g1);
        DaoProspect.save(ConnexionManager.getConnexionBD(), g22);
        DaoProspect.save(ConnexionManager.getConnexionBD(), g3);*/
     /*   DaoProspect.delete(ConnexionManager.getConnexionBD(), 6);
        System.out.println(DaoProspect.getListProspect());*/

        //Utilitaire.dateInput("2022-01-02");
/*
      System.out.println(DaoClient.getListClient().get(0));
        System.out.println(DaoClient.getListClient().get(1));
        System.out.println(DaoClient.getListClient().get(2));*/
    }
}
