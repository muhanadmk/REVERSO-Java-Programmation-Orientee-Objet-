package fr.afpa.pompey.cda08.demo;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import fr.afpa.pompey.cda08.demo.com.company.vues.Accueil;

import java.util.zip.DataFormatException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws ExceptionMetier, DataFormatException
    {
        Client f1 = new Client("ali", "face@gmail.com", "789553598",
                "hhhh",
                new Address("79", "Bld de la Liberation", "54000",
                        "Lille"), 2000, 55);
        Client f2 = new Client("hasne", "face@gmail.com", "789553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "nancy"), 222, 55);
        Client f3 = new Client("ahmed", "face@gmail.com", "7895553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "Lill"), 400, 55);

        Prospect g1 = new Prospect("Prospect 1", "face@gmail.com", "7895553598",
                "hhhh",
                new Address("79", "Bld de la Liberation", "54000",
                        "Lille"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.NON);
        Prospect g22 = new Prospect("Prospect 22", "face@gmail.com", "7895553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "Lill"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.OUI);

        Prospect g3 = new Prospect("Prospect 3", "face@gmail.com", "789553598",
                "hhhh",
                new Address("55", "Bld de la Liberation", "54000",
                        "Lill"), Utilitaire.dateInput("02/09/2011"), ChoixUtilisateur.chioxInteresser.NON);
        ListClient.getListClient().add(f1);
        ListClient.getListClient().add(f2);
        ListClient.getListClient().add(f3);
        ListeProspect.getListProspect().add(g1);
        ListeProspect.getListProspect().add(g22);
        ListeProspect.getListProspect().add(g3);

        Accueil accueil = new Accueil();
        accueil.setVisible(true);
        System.out.println( "Hello World!" );

    }
}
