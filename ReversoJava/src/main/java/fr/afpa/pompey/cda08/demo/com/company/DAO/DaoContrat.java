package fr.afpa.pompey.cda08.demo.com.company.DAO;


import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Contrat;
import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DaoContrat{
    private static final Logger LOGGER = LogManager.getLogger(DaoContrat.class.getName());
    private static PreparedStatement preparedStmt;
    private static Statement stmt;
    public DaoContrat() {
    }


    /**
     * find le info de contra By Id Client
     * @param idCilent
     * @return ArrayList<Contrat>
     * @throws DaoSqlEx
     */
    public ArrayList<Contrat> findByIdClient(Integer idCilent) throws DaoSqlEx, Exception {
        ArrayList<Contrat> listContrat = new ArrayList<>();
        try {
            String query = "SELECT `id`, `idClient`, `libelleDeContrat`, `montantDeContrat` FROM `contrat`" +
                        " INNER JOIN clients ON contrat.idClient = clients.Id_cliente WHERE  Id_cliente =?";
                preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query);
                preparedStmt.setInt(1, idCilent);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                Contrat contrat = new Contrat(rs.getInt("id"), rs.getInt("idClient"),
                        rs.getString("libelleDeContrat"), rs.getDouble("montantDeContrat"));
                listContrat.add(contrat);
            }
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("error base de données vous pouvez pas recuperer " +
                    "les contrats essaiez ultiareemnt");
        }
        return listContrat;
    }

    /**
     * ArrayList<Client> qui dedans l id et raison sosiale
     * @return
     * @throws DaoSqlEx
     * @throws ExceptionMetier
     */
    public ArrayList<Client> findAllCilentQuiOntContrat() throws DaoSqlEx, ExceptionMetier, Exception {
        String query = "SELECT DISTINCT name_Client, Id_cliente  FROM `contrat` " +
                "INNER JOIN clients ON contrat.idClient = clients.Id_cliente";
        ArrayList listClientQuiontdeContrat = new ArrayList();
        try {
            stmt = ConnexionManager.getConnexionBD().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               Client client = new Client();
               client.setSociale(rs.getString("name_Client"));
               client.setId(rs.getInt("Id_cliente"));
                listClientQuiontdeContrat.add(client);
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les Cilents");
        }
        return listClientQuiontdeContrat;
    }
}
