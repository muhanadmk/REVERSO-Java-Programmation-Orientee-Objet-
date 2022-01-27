package fr.afpa.pompey.cda08.demo.TestMetier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;

import static org.junit.jupiter.api.Assertions.*;

import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestClient extends Client {
    public TestClient() {
    }

    @Test
    void nePasAvoirNull() {
        Client client = new Client();
        boolean exceptionMonter = false;
        try {
            client.setSociale(null);
        } catch (ExceptionMetier re) {
            exceptionMonter = true;
        }
        assertTrue(exceptionMonter);
        client = null;
    }
    @ParameterizedTest
    @ValueSource(strings = {"mouhandmkgmail.com", "muhanamkafpacom"})
    void testPatternMailFaux(String mail) {
        assertFalse(Utilitaire.verifyEmail.matcher(mail).matches());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mouhandmk1@gmail.com", "mouhanad1@yahoo.com"})
    void testPatternMailVrai(String mail) {
        assertTrue(Utilitaire.verifyEmail.matcher(mail).matches());
    }

    @Test
    void valueNullEmptySetterSocieteName() {
        String[] invalidValues = {"", null, "\r", "\n"};
        for (String invalidValue : invalidValues) {
            assertThrows(ExceptionMetier.class, () -> {
                new Client().setSociale(invalidValue);
            });
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {3,9,-9,199})
    void TestleChiffreDaffaire(double ca) {
        assertThrows(ExceptionMetier.class, () ->
                new Client().setLeChiffreDaffaire(ca)
        );
    }
    @ParameterizedTest
    @ValueSource(ints = {-5,0})
    void TestleNombreDemployes(int leNombreDemployes) {
        assertThrows(ExceptionMetier.class, () ->
                new Client().setLeChiffreDaffaire(leNombreDemployes)
        );
    }
}
