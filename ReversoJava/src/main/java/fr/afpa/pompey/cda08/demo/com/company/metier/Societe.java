package fr.afpa.pompey.cda08.demo.com.company.metier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.*;
import static fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.verifyEmail;
import static fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.verifyTel;


/**
 * le classe mere qui a tout les attribut en commun entre le client et le prospect
 *
 * @author almokdad muhanad
 * @version 1.0.0
 */

public abstract class Societe {
    private String sociale;
    private String telephone;
    private String adresseMail;
    private String commentaries;
    private Address address;
    protected int id = 0;

    /**
     * @param sociale
     * @param adresseMail
     * @param telephone
     * @param commentaries
     * @param address
     * @throws ExceptionMetier
     */
    // constructeur de le classe mere qui va a
    public Societe(String sociale, String adresseMail, String telephone, String commentaries, Address address)
            throws ExceptionMetier {
        setSociale(sociale);
        setTelephone(telephone);
        setAdresseMail(adresseMail);
        setCommentaries(commentaries);
        setAddress(address);
    }
    public Societe(){}
    public int getId() {
        return id;
    }

    public String getSociale() {
        return sociale;
    }

    /**
     * c'est un setter de le nom de société qui va avoir un condition de exception
     * qui oblige le user à ne pas laisser vide
     *
     * @param sociale
     * @throws ExceptionMetier
     */
    public void setSociale(String sociale) throws ExceptionMetier {
        if (sociale == null || sociale.trim().isEmpty()) {
            throw new ExceptionMetier("La raison sociale devra être saisie");
        }
        this.sociale = sociale;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) throws ExceptionMetier {
        if (address == null) {
            throw new ExceptionMetier("addres cant etre nuul");
        }
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }
    /**
     * on test si le téléphone aura moins de 10 caractères
     *
     * @param telephone
     * @throws ExceptionMetier
     */


    public void setTelephone(String telephone) throws ExceptionMetier {
        if (telephone == null || telephone.length() < 9) {
            throw new ExceptionMetier("Le téléphone devra être renseigné et avoir au moins 10 caractères");
        }
        this.telephone = telephone;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    /**
     * on vérifie si le e-email continent la valeur de "@"
     *
     * @param adresseMail
     * @throws ExceptionMetier
     */
    public void setAdresseMail(String adresseMail) throws ExceptionMetier {

        if (adresseMail == null || !(verifyEmail.matcher(adresseMail).matches())) {
            throw new ExceptionMetier("L’adresse mail devra être renseignée et avoir au moins le caractère « @ »" +
                    "et un « . »");
        }

        this.adresseMail = adresseMail;
    }

    public String getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(String commentaries) throws ExceptionMetier {
        if (commentaries == null) {
            throw new ExceptionMetier("commentaries cant etre nuul");
        }
        this.commentaries = commentaries;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "sociale='" + sociale + '\'' +
                ", telephone=" + telephone +
                ", adresseMail='" + adresseMail + '\'' +
                ", commentaries='" + commentaries + '\'' +
                ", address=" + getAddress().getNumeroDeRue() +
                " " + getAddress().getNumeroDeRue() +
                " " + getAddress().getVille() +
                " " + getAddress().getCodePostal() +
                '}';
    }

}
