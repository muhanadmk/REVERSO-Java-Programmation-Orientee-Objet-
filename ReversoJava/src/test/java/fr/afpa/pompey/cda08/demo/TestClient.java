package fr.afpa.pompey.cda08.demo;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestClient extends Client {
    public TestClient(){}
    @Test
    void null_clientName() {
        Client client = new Client();
        boolean exceptionRaised = false;
        try {
            client.setSociale(null);
        }
        catch (ExceptionMetier re) {
            exceptionRaised = true;
        }
        assertTrue(exceptionRaised);
    }
}
