package fr.afpa.pompey.cda08.demo.com.company.utile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Utilitaire {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static LocalDate dateInput(String userInput) {
        LocalDate date = LocalDate.parse(userInput, formatter);
        return date;
    }
}


