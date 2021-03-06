package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 *  class DaoClient qui gere aller router avel le Bd en cas de CLient
 */
public class DaoClient extends DAO<Client>  {
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static Statement stmt = null;
    private static PreparedStatement preparedStmt = null;
    private static Client client;

    public DaoClient() {
    }

    /**
     * findAll pour recuperer les Cilents de BD
     *
     * @param con
     * @return ArrayList<Client>
     * @throws DaoSqlEx
     * @throws ExceptionMetier
     */
    public ArrayList<Client> findAll() throws DaoSqlEx, ExceptionMetier, Exception {
        String query = "SELECT * FROM clients";
        ArrayList<Client> listClient = new ArrayList<>();
        try {
            stmt = ConnexionManager.getConnexionBD().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                client =
                        new Client(rs.getInt("Id_cliente"), rs.getString("name_Client"),
                                rs.getString("adresseMail"), rs.getString("telephone"),
                                rs.getString("commentarie"),
                                new Address(rs.getString("address"), "2", "2", "2"),
                                rs.getDouble("Chiffre_daffaire"), rs.getInt("Nombre_demployes"));
                client.setListContrat(new DaoContrat().findByIdClient(client.getId()));
                listClient.add(client);
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les Cilents");
        }
        return listClient;
    }

    /**
     * cette methode recuperate un seul client en ut lisant son Id
     * @param con
     * @param idcliente
     * @return object de client
     * @throws DaoSqlEx
     * @throws ExceptionMetier
     */
    public Client find(Integer idcliente) throws DaoSqlEx, ExceptionMetier, Exception {
        try {
            String query = "SELECT * FROM `clients` WHERE Id_cliente = ?";
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query);
            preparedStmt.setInt(1, idcliente);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                client = new Client(rs.getInt("Id_cliente"), rs.getString("name_Client"),
                        rs.getString("adresseMail"), rs.getString("telephone"),
                        rs.getString("commentarie"),
                        new Address(rs.getString("address"), "2", "2", "2"),
                        rs.getDouble("Chiffre_daffaire"), rs.getInt("Nombre_demployes"));
            }
            client.setListContrat(new DaoContrat().findByIdClient(idcliente));
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer le Cilente");
        }
        return client;
    }

    /**
     * cette methode cree ou modifier un seul client
     * @param con
     * @param Upclient
     * @return id de client en cas cree
     * @throws DaoSqlEx
     */
    @Override
    public Integer save( Client Upclient) throws DaoSqlEx, Exception {
        int id = 0;
        String query = null;
        if (Upclient.getId() == 0) {
            query = "INSERT INTO  clients(name_Client, telephone, adresseMail, address, commentarie," +
                    "Nombre_demployes, Chiffre_daffaire) VALUES (?,?,?,?,?,?,?)";
        } else {
            query = "UPDATE clients SET name_Client= ?,telephone= ?,adresseMail=?,address=? ,commentarie=?," +
                    "Nombre_demployes=?,Chiffre_daffaire=?  WHERE  Id_cliente = ?";
        }
        try {
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, Upclient.getSociale());
            preparedStmt.setString(2, Upclient.getTelephone());
            preparedStmt.setString(3, Upclient.getAdresseMail());
            preparedStmt.setString(4, Upclient.getAddress().getNumeroDeRue() +
                    Upclient.getAddress().getNomDeRue() + "" + Upclient.getAddress().getVille() +
                    "" + Upclient.getAddress().getCodePostal());
            preparedStmt.setString(5, Upclient.getCommentaries());
            preparedStmt.setLong(6, Upclient.getLeNombreDemployes());
            preparedStmt.setDouble(7, Upclient.getLeChiffreDaffaire());

            if (Upclient.getId() > 0) {
                preparedStmt.setInt(8, Upclient.getId());
            }
            preparedStmt.executeUpdate();
            ResultSet resultSet = preparedStmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a modifier ou" +
                    "cree un Cilente essaiez ultiareemnt");
        }
        return id;
    }

    /**
     * cette methode supprimer un seul client en utlisant son Id
     * @param con
     * @param IdClient
     * @throws DaoSqlEx
     */
    public void delete(Integer IdClient) throws DaoSqlEx , Exception{
        String query = "delete from clients where Id_cliente = ?";
        try {
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query);
            preparedStmt.setInt(1, IdClient);

            // execute the preparedstatement
            preparedStmt.execute();
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a delete ou Client");
        }
    }
}

