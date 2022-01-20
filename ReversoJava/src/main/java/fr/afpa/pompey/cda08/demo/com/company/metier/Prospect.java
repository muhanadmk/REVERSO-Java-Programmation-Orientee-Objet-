package fr.afpa.pompey.cda08.demo.com.company.metier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;

import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;

import java.time.LocalDate;

/**
 * classe Prospect qui hérite de la class société en plus il va avoir attribué différentes
 */
public class Prospect extends Societe {

    private LocalDate laDateDeProspection;
    private static int idProspect;
    private ChoixUtilisateur.chioxInteresser interesse;

    /**
     * constructeur de la classe qui va avoir le constructeur de la classe mère de société en plus leurs attribus
     *
     * @param sociale
     * @param adresseMail
     * @param telephone
     * @param commentaries
     * @param address
     * @param laDateDeProspection
     * @param interesse
     * @throws ExceptionMetier
     */
    public Prospect(String sociale, String adresseMail, String telephone, String commentaries,
                    Address address, LocalDate laDateDeProspection, ChoixUtilisateur.chioxInteresser interesse)
            throws ExceptionMetier {
        super(sociale, adresseMail, telephone, commentaries, address);
        idProspect++;
        this.id = idProspect;
        setLaDateDeProspection(laDateDeProspection);
        setInteresse(interesse);
    }

    public LocalDate getLaDateDeProspection() {
        return laDateDeProspection;
    }

    public void setLaDateDeProspection(LocalDate laDateDeProspection) {
        this.laDateDeProspection = laDateDeProspection;
    }

    public ChoixUtilisateur.chioxInteresser getInteresse() {
        return interesse;
    }

    public void setInteresse(ChoixUtilisateur.chioxInteresser interesse) throws ExceptionMetier {
        if (interesse == null) {
            throw new ExceptionMetier("interesse peut pas etre null");
        }
        this.interesse = interesse;
    }


    @Override
    public String toString() {
        return super.toString() + "Prospect{ " +
                " Prospect ID " + getId() +
                "LaDateDeProspection=" + laDateDeProspection +
                ", interesse='" + interesse + '\'' +
                '}';
    }

}
