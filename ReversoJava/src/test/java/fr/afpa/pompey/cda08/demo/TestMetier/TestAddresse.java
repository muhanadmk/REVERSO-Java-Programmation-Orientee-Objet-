package fr.afpa.pompey.cda08.demo.TestMetier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Prospect;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddresse extends Address {
    private Prospect prospect;
    TestAddresse() {
    }

    @Test
    void testAddresseRue() {
        createAddressForTest();
        assertThrows(ExceptionMetier.class, () ->
                prospect.getAddress().setNomDeRue(null)
        );
    }
    @Test
    void testAddresseNmRue() {
        createAddressForTest();
        assertThrows(ExceptionMetier.class, () ->
                prospect.getAddress().setNumeroDeRue(null)
        );
    }
    @Test
    void testAddresseCd() {
        createAddressForTest();
        assertThrows(ExceptionMetier.class, () ->
                prospect.getAddress().setCodePostal(null)
        );
    }
    @Test
    void testAddresseNmVille() {
        createAddressForTest();
        assertThrows(ExceptionMetier.class, () ->
                prospect.getAddress().setVille(null)
        );
    }


    private void createAddressForTest(){
        boolean exceptionMonter = false;
        try {
            prospect = new Prospect(0,"Prospect 1", "mouhandmk1@gmail.com", "7895553598",
                    "hhhh",
                    new Address("79", "Bld de la Liberation", "54000",
                            "Lille"), Utilitaire.dateInput("02/09/2011"),
                    ChoixUtilisateur.chioxInteresser.NON);
        } catch (ExceptionMetier re) {
            exceptionMonter = true;
        }
    }
}
