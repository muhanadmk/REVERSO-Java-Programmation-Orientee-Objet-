package fr.afpa.pompey.cda08.demo.com.company.utile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.apache.logging.log4j.message.LoggerNameAwareMessage;


public class Utilitaire {
    public static Logger logger = LogManager.getLogger(Utilitaire.class);

    public static final Pattern verifyEmail = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate dateInput(String userInput) {
        LocalDate date = LocalDate.parse(userInput, formatter);
        return date;
    }


}


