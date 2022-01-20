package fr.afpa.pompey.cda08.demo.com.company.metier;

import java.util.ArrayList;

/**
 * un claas sur lequel on stoke les liste de Prospect en utilisant les array liste dans la colocation de Java
 */
public class ListeProspect {

    private static ArrayList<Prospect> listProspect = new ArrayList();

    public static ArrayList getListProspect() {
        return listProspect;
    }
}
