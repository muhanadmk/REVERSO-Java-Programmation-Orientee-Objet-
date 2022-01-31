package fr.afpa.pompey.cda08.demo.com.company.metier;

public class Contrat {
    private  int id;
    private int idClient;
    private String libelleDeContrat;
    private double montantDeContrat;

    public Contrat(){}

    public Contrat(int id, int idClient, String libelleDeContrat, double montantDeContrat) {
        setId(id);
        setIdClient(idClient);
        setLibelleDeContrat(libelleDeContrat);
        setMontantDeContrat(montantDeContrat);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getLibelleDeContrat() {
        return libelleDeContrat;
    }

    public void setLibelleDeContrat(String libelleDeContrat) {
        this.libelleDeContrat = libelleDeContrat;
    }

    public double getMontantDeContrat() {
        return montantDeContrat;
    }

    public void setMontantDeContrat(double montantDeContrat) {
        this.montantDeContrat = montantDeContrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", libelleDeContrat='" + libelleDeContrat + '\'' +
                ", montantDeContrat=" + montantDeContrat +
                '}';
    }
}
