package fr.afpa.pompey.cda08.demo.com.company.DAO;


import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Contrat;

import java.sql.*;
import java.util.ArrayList;

public class DaoContrat extends DAO{
    private static PreparedStatement preparedStmt;
    private static Statement stmt;
    public DaoContrat() {
    }

    @Override
    ArrayList findAll(Connection con) throws DaoSqlEx, ExceptionMetier {
        return null;
    }

    @Override
    Object find(Connection con, Integer id) throws DaoSqlEx, ExceptionMetier {
        return null;
    }

    @Override
    Integer save(Connection con, Object o) throws DaoSqlEx {
        return null;
    }

    @Override
    void delete(Connection con, Integer IdClient) throws DaoSqlEx {

    }


    public ArrayList<Contrat> findByIdClient(Connection con, Integer idCilent) throws DaoSqlEx {
        ArrayList<Contrat> listContrat = new ArrayList<>();
        try {
            String query = "SELECT `id`, `idClient`, `libelleDeContrat`, `montantDeContrat` FROM `contrat`" +
                        " INNER JOIN clients ON contrat.idClient = clients.Id_cliente WHERE  Id_cliente =?";
                preparedStmt = con.prepareStatement(query);
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
            e.printStackTrace();
            throw new DaoSqlEx("error base de données vous pouvez pas recuperer " +
                    "les contrats essaiez ultiareemnt");
        }
        return listContrat;
    }
    public ArrayList<Client> findAllCilentQuiOntContrat(Connection con) throws DaoSqlEx, ExceptionMetier {
        String query = "SELECT DISTINCT name_Client, Id_cliente  FROM `contrat` " +
                "INNER JOIN clients ON contrat.idClient = clients.Id_cliente";
        ArrayList listClientQuiontdeContrat = new ArrayList();
        try {
            stmt = con.createStatement();
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
            e.printStackTrace();
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les Cilents");
        }
        return listClientQuiontdeContrat;
    }
}
