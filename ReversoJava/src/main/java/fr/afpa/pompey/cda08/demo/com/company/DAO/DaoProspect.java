package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Prospect;
import fr.afpa.pompey.cda08.demo.com.company.metier.Societe;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.formatter;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class DaoProspect {
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static Statement stmt = null;
    private static PreparedStatement preparedStmt = null;
    private static Prospect prospect =null;
    public DaoProspect() {
    }

    public static ArrayList<Prospect> findAll(Connection con) throws DaoSqlEx {
        String query = "SELECT * FROM prospects";
        ArrayList<Prospect> listProspect = new ArrayList();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                byte interesse = rs.getByte("interesse");
                ChoixUtilisateur.chioxInteresser  choxInteresse = null;
                if (((int) interesse) == 1){
                    choxInteresse = ChoixUtilisateur.chioxInteresser.OUI;
                }else {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.NON;
                }
                prospect = new Prospect(rs.getInt("id_prospect"),rs.getString("name_prospect"),
                        rs.getString("adresseMail"),  rs.getString("telephone"),
                        rs.getString("commentarie"),
                        new Address(rs.getString("address"),"2","2","2"),
                        Utilitaire.dateInput(rs.getDate("DateDeProspection").toLocalDate().format(formatter)),
                        choxInteresse);
                listProspect.add(prospect);
            }
        }catch (SQLException | ExceptionMetier e ) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les prospects"
            +"essaiez ultiareemnt");
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
        return listProspect;
    }

    public static Prospect find(Connection con, String idProspect) throws DaoSqlEx {
        String sql = "SELECT * FROM prospects where name_prospect=?";
        try {
            preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString(1, idProspect);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                byte interesse = rs.getByte("interesse");
                ChoixUtilisateur.chioxInteresser  choxInteresse = null;
                if (((int) interesse) == 1){
                    choxInteresse = ChoixUtilisateur.chioxInteresser.OUI;
                }else {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.NON;
                }
                prospect = new Prospect(rs.getInt("id_prospect"),rs.getString("name_prospect"),
                        rs.getString("adresseMail"),  rs.getString("telephone"),
                        rs.getString("commentarie"),
                        new Address(rs.getString("address"),"2","2","2"),
                        Utilitaire.dateInput(rs.getDate("DateDeProspection").toLocalDate().format(formatter)),
                        choxInteresse);
            }
        }  catch (SQLException | ExceptionMetier e ) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer le prospects" +
                    "essaiez ultiareemnt");
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
        return prospect;
    }

    public static int save(Connection con, Prospect upProspect) throws DaoSqlEx {
        int id=0;
        String query = null;
        if (upProspect.getId() == 0) {
            query = "insert into prospects (name_prospect, telephone, adresseMail, address, commentarie," +
                    " DateDeProspection, interesse) VALUES (?, ?, ?, ?, ?, ? , ?)";
        }else {
            id = upProspect.getId();
            query = "UPDATE prospects SET name_prospect= ?,telephone= ?,adresseMail=?,address=? ,commentarie=?," +
                    "DateDeProspection=?,interesse=?  WHERE  id_prospect = ?";
        }
        try {
            preparedStmt = con.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, upProspect.getSociale());
            preparedStmt.setString(2, upProspect.getTelephone());
            preparedStmt.setString(3, upProspect.getAdresseMail());
            preparedStmt.setString(4, upProspect.getAddress().getNumeroDeRue() +
                    upProspect.getAddress().getNomDeRue() + " " + upProspect.getAddress().getVille() +
                    " " + upProspect.getAddress().getCodePostal());
            preparedStmt.setString(5, upProspect.getCommentaries());
            Date date = Date.valueOf(upProspect.getLaDateDeProspection());
            preparedStmt.setDate(6, date);

            if (upProspect.getInteresse().toString().equals("OUI")){
                preparedStmt.setByte(7, (byte) 1);
            }else {
                preparedStmt.setByte(7, (byte) 0);
            }
            if (upProspect.getId()> 0){
                preparedStmt.setInt(8, upProspect.getId());
            }
            // execute the preparedstatement
            preparedStmt.executeUpdate();
            ResultSet resultSet =preparedStmt.getGeneratedKeys();
            if(resultSet.next()){
                id =resultSet.getInt(1);
            }
        } catch (SQLException e ) {
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a modifier ou" +
                    " cree un procpect essaiez ultiareemnt");
        } finally {
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException e) {
                    System.err.print("Transaction is being rolled back");
                    e.printStackTrace();
                    throw new DaoSqlEx("error base de données essaiez ultiareemnt");
                }
            }
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.print("Transaction is being rolled back");
                e.printStackTrace();
                throw new DaoSqlEx("error base de données essaiez ultiareemnt");
            }
        }
        return id;
    }

    public static void delete(Connection con, int IdProspect) throws DaoSqlEx {
        String query = "delete from prospects where id_prospect = ?";
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, IdProspect);
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a delete prospect" +
                    " essaiez ultiareemnt");
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoSqlEx("error base de données essaiez ultiareemnt");
                }
            }
        }
    }
}
