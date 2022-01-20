package fr.afpa.pompey.cda08.demo.com.company.metier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;


/**
 * classe client qui hérite de la class société en plus il va avoir attribué différentes
 */

public class Client extends Societe {

    private static int idClient;
    private double LeChiffreDaffaire;
    private long LeNombreDemployes;

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
    public Client(String sociale, String adresseMail, String telephone, String commentaries,
                  Address address, double leChiffreDaffaire, long leNombreDemployes) throws ExceptionMetier {
        super(sociale, adresseMail, telephone, commentaries, address);
        idClient++;
        this.id = idClient;
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
        if (leNombreDemployes <= 0) {
            throw new ExceptionMetier("Le nombre d’employés devra être renseigné et être strictement supérieur à zéro");
        }
        this.LeNombreDemployes = leNombreDemployes;
    }

    public int getIdClient() {
        return idClient;
    }

    @Override
    public String toString() {
        return super.toString() + "Client{" +
                "Client Id " + getId() +
                " LeChiffreDaffaire=" + LeChiffreDaffaire +
                ", LeNombreDemployes=" + LeNombreDemployes +
                '}';
    }

}
