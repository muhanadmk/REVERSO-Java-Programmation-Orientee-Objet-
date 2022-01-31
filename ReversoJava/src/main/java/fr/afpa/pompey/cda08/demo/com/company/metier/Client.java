package fr.afpa.pompey.cda08.demo.com.company.metier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


/**
 * classe client qui hérite de la class société en plus il va avoir attribué différentes
 */

public class Client extends Societe {

    private double LeChiffreDaffaire;
    private long LeNombreDemployes;
    private ArrayList<Contrat> listContrat = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(Client.class.getName());


    /**
     * constructeur de la classe qui va avoir le constructeur de la classe mère de société en plus leurs attribus
     *
     * @param sociale
     * @param adresseMail
     * @param telephone
     * @param commentaries
     * @param address
     * @param leChiffreDaffaire
     * @param leNombreDemployes
     * @throws ExceptionMetier
     */
    public Client(int id ,String sociale, String adresseMail, String telephone, String commentaries,
                  Address address, double leChiffreDaffaire, long leNombreDemployes) throws ExceptionMetier {
        super(id,sociale, adresseMail, telephone, commentaries, address);
        setLeNombreDemployes(leNombreDemployes);
        setLeChiffreDaffaire(leChiffreDaffaire);
    }
    public Client(){}
    public double getLeChiffreDaffaire() {
        return LeChiffreDaffaire;
    }

    /**
     * on vérifie que le chiffre d'affaire n'est pas moins que 200
     *
     * @param leChiffreDaffaire
     * @throws ExceptionMetier
     */
    public void setLeChiffreDaffaire(double leChiffreDaffaire) throws ExceptionMetier {
        if (leChiffreDaffaire < 200) {
            throw new ExceptionMetier("tLe chiffre d’affaire devra être renseigné et être supérieur à 200");
        }
        this.LeChiffreDaffaire = leChiffreDaffaire;
    }

    public long getLeNombreDemployes() {
        return LeNombreDemployes;
    }

    /**
     * on vérifie que le numéro des employés n'est pas puls que 0
     *
     * @param leNombreDemployes
     * @throws ExceptionMetier
     */
    public void setLeNombreDemployes(long leNombreDemployes) throws ExceptionMetier {
        if (leNombreDemployes < 0) {
            throw new ExceptionMetier("Le nombre d’employés devra être renseigné et être strictement supérieur à zéro");
        }
        this.LeNombreDemployes = leNombreDemployes;
    }

    public ArrayList<Contrat> getListContrat() {
        return listContrat;
    }

    public void setListContrat(ArrayList<Contrat> listContrat) {
        this.listContrat = listContrat;
    }

    @Override
    public String toString() {
        return "Client{" +
                "LeChiffreDaffaire=" + LeChiffreDaffaire +
                ", LeNombreDemployes=" + LeNombreDemployes +
                ", listContrat=" + listContrat +
                '}';
    }
}
