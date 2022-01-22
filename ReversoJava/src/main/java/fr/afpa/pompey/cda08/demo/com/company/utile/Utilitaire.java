package fr.afpa.pompey.cda08.demo.com.company.utile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class Utilitaire {
    public static final Pattern verifyEmail = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
    public static final Pattern verifyTel = Pattern.compile("^\\+([0-9\\-]?){9,11}[0-9]$");

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static LocalDate dateInput(String userInput) {
        LocalDate date = LocalDate.parse(userInput, formatter);
        return date;
    }

}


