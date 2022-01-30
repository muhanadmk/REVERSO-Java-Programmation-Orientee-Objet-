package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DaoClient {
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static Statement stmt = null;
    private static PreparedStatement preparedStmt = null;
    private static Client client;
    public DaoClient() {
    }

    public static ArrayList<Client> findAll(Connection con) throws DaoSqlEx {
        String query = "SELECT * FROM clients";
        ArrayList <Client> listClient = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                client = new Client(rs.getInt("Id_cliente"),rs.getString("name_Client"),
                        rs.getString("adresseMail"), rs.getString("telephone"),
                        rs.getString("commentarie"),
                        new Address(rs.getString("address"),"2","2","2"),
                        rs.getDouble("Chiffre_daffaire"), rs.getInt("Nombre_demployes"));
                listClient.add(client);
            }
        }
        catch (SQLException | ExceptionMetier e ) {
            e.printStackTrace();
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les Cilents");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listClient;
    }


    public static Client find(Connection con, String nameCliente) throws DaoSqlEx {
        Client client =null;
        try {
            String sql = "SELECT * FROM clients where name_Client=?";
            preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString(1, nameCliente);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                client = new Client(rs.getInt("Id_cliente"),rs.getString("name_Client"),
                        rs.getString("adresseMail"), rs.getString("telephone"),
                        rs.getString("commentarie"),
                        new Address(rs.getString("address"),"2","2","2"),
                        rs.getDouble("Chiffre_daffaire"), rs.getInt("Nombre_demployes"));

            }
        }  catch (SQLException | ExceptionMetier e ) {
            throw new DaoSqlEx("error base de données essaiez ultiareemnt");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoSqlEx("error base de données essaiez ultiareemnt");
                }
            }
        }
        return client;
    }

    public static int save(Connection con, Client Upclient) throws DaoSqlEx {
        int id = 0;
        String query = null;
        if (Upclient.getId() == 0) {
            query = "INSERT INTO  clients(name_Client, telephone, adresseMail, address, commentarie," +
                    " Nombre_demployes, Chiffre_daffaire) VALUES (?,?,?,?,?,?,?)";
        } else {
            query = "UPDATE clients SET name_Client= ?,telephone= ?,adresseMail=?,address=? ,commentarie=?," +
                    "Nombre_demployes=?,Chiffre_daffaire=?  WHERE  Id_cliente = ?";
        }
        try {
            preparedStmt = con.prepareStatement(query,
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

           if (Upclient.getId() > 0 ) {
                preparedStmt.setInt(8, Upclient.getId());
            }
            preparedStmt.executeUpdate();
            ResultSet resultSet =preparedStmt.getGeneratedKeys();
            if(resultSet.next()){
                id =resultSet.getInt(1);
            }
            if (preparedStmt !=null){
                preparedStmt.close();
            }
        } catch (SQLException e ) {
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a modifier ou" +
                    " cree un Cilente essaiez ultiareemnt");
        } finally {
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException e) {
                    System.err.print("Transaction is being rolled back");
                    e.printStackTrace();
                }
            }
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.print("Transaction is being rolled back");
                e.printStackTrace();
            }
        }
        return id;
    }

    public static void delete(Connection con, int IdClient) throws DaoSqlEx {
        String query = "delete from clients where Id_cliente = ?";
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, IdClient);

            // execute the preparedstatement
            preparedStmt.execute();
            if (preparedStmt !=null){
                preparedStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a delete ou Cilent");
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

