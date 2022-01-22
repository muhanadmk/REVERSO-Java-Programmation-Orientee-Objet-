package fr.afpa.pompey.cda08.demo.TestMetier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Prospect;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

public class TestProspect extends Prospect {
    public TestProspect(){

    }
    @Test
    void nePasAvoirNull() {
        Prospect prospect = new Prospect();
        boolean exceptionMonter = false;
        try {
            prospect.setSociale(null);
        } catch (ExceptionMetier re) {
            exceptionMonter = true;
        }
        assertTrue(exceptionMonter);
        prospect = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"mouhandmkgmail.com", "muhanamkafpacom"})
    void testPatternMail(String mail) {
        assertFalse(Utilitaire.verifyEmail.matcher(mail).matches());
    }

    @ParameterizedTest
    @ValueSource(strings = {"111/12/2005","32/02/2021","10/01/-000", "00/00/-000"})
    void testLocalDate(String date) {
        assertThrows(java.time.format.DateTimeParseException.class, () ->
                new Prospect().setLaDateDeProspection(Utilitaire.dateInput(date))
        );
    }


}
