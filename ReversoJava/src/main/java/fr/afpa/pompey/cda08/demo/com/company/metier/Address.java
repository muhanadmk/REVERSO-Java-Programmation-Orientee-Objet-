package fr.afpa.pompey.cda08.demo.com.company.metier;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;

/**
 * c'est un class avec lequel on peut récupérer les informations de l'adresse
 * pour qu'on puisse utiliser à des autres projets si on veut.
 */
public class Address {
    private String numeroDeRue;
    private String nomDeRue;
    private String codePostal;
    private String ville;

    /**
     * constructor de l'adderss qui prender des String valuer
     *
     * @param numeroDeRue
     * @param nomDeRue
     * @param codePostal
     * @param ville
     * @throws ExceptionMetier
     */

    public Address(String numeroDeRue, String nomDeRue, String codePostal, String ville) throws ExceptionMetier {
        setNumeroDeRue(numeroDeRue);
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setVille(ville);
    }
    public Address(){}
    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    public void setNumeroDeRue(String numeroDeRue) throws ExceptionMetier {
        if (numeroDeRue == null || numeroDeRue.trim().isEmpty()) {
            throw new ExceptionMetier("numeroDeRue : Tous les champs de l’adresse devront être renseignés");
        }
        this.numeroDeRue = numeroDeRue;
    }

    public String getNomDeRue() {
        return nomDeRue;
    }

    /**
     * pour toute les setter de
     * l'adresse, on vérifie que tout les champs sont bien remplis
     *
     * @param nomDeRue
     * @throws ExceptionMetier
     */
    public void setNomDeRue(String nomDeRue) throws ExceptionMetier {
        if (nomDeRue == null || nomDeRue.trim().isEmpty()) {
            throw new ExceptionMetier("nomDeRue : Tous les champs de l’adresse devront être renseignés");
        }
        this.nomDeRue = nomDeRue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    /**
     * pour toute les setter de
     * l'adresse, on vérifie que tout les champs sont bien remplis
     *
     * @param codePostal
     * @throws ExceptionMetier
     */
    public void setCodePostal(String codePostal) throws ExceptionMetier {
        if (codePostal == null || codePostal.trim().isEmpty()) {
            throw new ExceptionMetier(" codePostal :Tous les champs de l’adresse devront être renseignés");
        }
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    /**
     * pour toute les setter de
     * l'adresse, on vérifie que tout les champs sont bien remplis
     *
     * @param ville
     * @throws ExceptionMetier
     */
    public void setVille(String ville) throws ExceptionMetier {
        if (ville == null || ville.trim().isEmpty()) {
            throw new ExceptionMetier("ville :Tous les champs de l’adresse devront être renseignés");
        }
        this.ville = ville;
    }
}
