package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Societe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DaoClient {
    private static Client client;
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static ArrayList<Client> listClient = new ArrayList();

    public DaoClient() {
    }


    public static ArrayList getListClient() {
        return listClient;
    }
    public static void setClient(int id ,String sociale, String adresseMail, String telephone, String commentaries,
                                 String numeroDeRue, String codePostal, String nomDeRue, String ville,
                                 double leChiffreDaffaire, long leNombreDemployes) throws ExceptionMetier {
        client = new Client(id,sociale, adresseMail, telephone,
                commentaries,
                new Address(numeroDeRue, nomDeRue, codePostal,
                        ville), leChiffreDaffaire, leNombreDemployes);
    }

    public static Client getClient() {
        return client;
    }

    public static ArrayList<Client> findAll(Connection con) throws SQLException {
        Statement stmt = null;
        String query = "SELECT * FROM clients";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name_Client = rs.getString("name_Client");
                int Id_cliente = rs.getInt("Id_cliente");
                String adresseMail = rs.getString("adresseMail");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String commentarie = rs.getString("commentarie");
                int Nombre_demployes = rs.getInt("Nombre_demployes");
                double Chiffre_daffaire = rs.getDouble("Chiffre_daffaire");
                setClient(Id_cliente,name_Client, adresseMail, telephone, commentarie, address, address, address, address,
                        Chiffre_daffaire, Nombre_demployes);
                listClient.add(client);
            }
        } catch (SQLException | ExceptionMetier sqlE) {
            sqlE.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return listClient;
    }


    public static void find(Connection con, String IdCliente) throws SQLException {
        String sql = "SELECT * FROM clients where Id_cliente=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, IdCliente);
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name_Client = rs.getString("name_Client");
                int Id_cliente = rs.getInt("Id_cliente");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String commentarie = rs.getString("commentarie");
                int Nombre_demployes = rs.getInt("Nombre_demployes");
                double Chiffre_daffaire = rs.getDouble("Chiffre_daffaire");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void save(Connection con, Client Upclient) throws SQLException {
        Statement stmt = null;
        PreparedStatement preparedStmt = null;
        String query = null;
        System.out.println("Upclient.getId() =" + Upclient.getId());
        if (Upclient.getId() == 0) {
            query = "INSERT INTO  clients(name_Client, telephone, adresseMail, address, commentarie, Nombre_demployes, Chiffre_daffaire) VALUES (?,?,?,?,?,?,?)";
        } else {
            query = "UPDATE clients SET name_Client= ?,telephone= ?,adresseMail=?,address=? ,commentarie=?,Nombre_demployes=?,Chiffre_daffaire=?  WHERE  Id_cliente = ?";
        }
        try {
            con.setAutoCommit(false);
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, Upclient.getSociale());
            preparedStmt.setString(2, Upclient.getTelephone());
            preparedStmt.setString(3, Upclient.getAdresseMail());
            preparedStmt.setString(4, Upclient.getAddress().getNumeroDeRue() +
                    Upclient.getAddress().getNomDeRue() + "" + Upclient.getAddress().getVille() +
                    "" + Upclient.getAddress().getCodePostal());
            preparedStmt.setString(5, Upclient.getCommentaries());
            preparedStmt.setLong(6, Upclient.getLeNombreDemployes());
            preparedStmt.setDouble(7, Upclient.getLeChiffreDaffaire());
            //preparedStmt.setInt(8, 1);
           if (Upclient.getId() > 0 ) {
                preparedStmt.setInt(8, Upclient.getId());
            }
            preparedStmt.executeUpdate();
            System.out.println("preparedStmt.executeUpdate();");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStmt != null) {
                preparedStmt.close();
            }
            if (preparedStmt != null) {
                preparedStmt.close();
            }
            con.setAutoCommit(true);
        }

        /*finally {
            if (stmt != null) {
                stmt.close();
            }
        }*/
    }

    public static void delete(Connection con, int IdClient) throws SQLException {
        Statement stmt = null;
        String query = "delete from clients where Id_cliente = ?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, IdClient);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

}

