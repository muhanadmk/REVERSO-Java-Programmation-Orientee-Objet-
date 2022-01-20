package fr.afpa.pompey.cda08.demo.com.company.metier;

import java.util.ArrayList;

/**
 * un claas sur lequel on stoke les liste de clients en utilisant les array liste dans la colocation de Java
 */
public class ListClient {

    private static ArrayList<Client> listClient = new ArrayList();

    public static ArrayList getListClient() {
        return listClient;

    }

}
