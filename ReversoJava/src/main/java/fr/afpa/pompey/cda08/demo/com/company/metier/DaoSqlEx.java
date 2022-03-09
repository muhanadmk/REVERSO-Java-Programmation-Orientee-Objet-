package fr.afpa.pompey.cda08.demo.com.company.metier;

public class DaoSqlEx extends Exception {
    private int gravite;
    public DaoSqlEx(String Message) {
        super(Message);
    }
   public DaoSqlEx(String Message, int gravite) {
        super(Message);
        setGravite(gravite);
   }

    public int getGravite() {
        return gravite;
    }

    public void setGravite(int gravite) {
        this.gravite = gravite;
    }
}
