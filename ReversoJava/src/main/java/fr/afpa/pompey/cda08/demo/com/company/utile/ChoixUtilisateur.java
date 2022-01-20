package fr.afpa.pompey.cda08.demo.com.company.utile;

/**
 * class pour avoir des méthodes de enum pour oblige le user vers choix
 * et on peut  l'appeler partout en écriture le nom de classe après le nom de méthode
 */
public class ChoixUtilisateur {

    /**
     * cette méthode de enum pour le situation de CREATION,
     * MODIFIER,
     * SUPRIMER
     */
    public enum choix {
        CREATION,
        MODIFIER,
        SUPRIMER
    }

    /**
     * cette méthode pour chiox Interesser si oui ou si non
     */
    public enum chioxInteresser {
        OUI,
        NON
    }

    public enum choixClientOuProcpect {
        Client,
        PROSPECT
    }

}
