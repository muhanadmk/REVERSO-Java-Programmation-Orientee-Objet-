package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Address;
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
    private static Prospect prospect;
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static ArrayList<Prospect> listProspect = new ArrayList();

    public DaoProspect() {
    }


    public static ArrayList<Prospect> getListProspect() {
        return listProspect;
    }

    public static void setProspect(int id, String sociale, String adresseMail, String telephone, String commentaries,
                                   String numeroDeRue, String codePostal, String nomDeRue, String ville,
                                   String laDateDeProspection, ChoixUtilisateur.chioxInteresser interesse) throws ExceptionMetier {
        prospect = new Prospect(id, sociale, adresseMail, telephone,
                commentaries,
                new Address(numeroDeRue, nomDeRue, codePostal,
                        ville), Utilitaire.dateInput(laDateDeProspection), interesse);
    }

    public static Prospect getprospect() {
        return prospect;
    }

    public static ArrayList<Prospect> findAll(Connection con) throws SQLException {
        Statement stmt = null;
        String query = "SELECT * FROM prospects";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name_prospect = rs.getString("name_prospect");
                int id_prospect = rs.getInt("id_prospect");
                String adresseMail = rs.getString("adresseMail");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String commentarie = rs.getString("commentarie");
                LocalDate DateDeProspection = rs.getDate("DateDeProspection").toLocalDate();
                byte interesse = rs.getByte("interesse");
                ChoixUtilisateur.chioxInteresser choxInteresse = null;
                if (interesse == 1) {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.OUI;
                } else {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.NON;
                }

                setProspect(id_prospect, name_prospect, adresseMail, telephone, commentarie, address, address, address, address,
                        DateDeProspection.format(formatter), choxInteresse);
                listProspect.add(prospect);
            }
        } catch (SQLException | ExceptionMetier e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return listProspect;
    }

    public static void find(Connection con, int idProspect) throws SQLException {
        String sql = "SELECT * FROM prospects where id_prospect=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idProspect);
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name_prospect = rs.getString("name_prospect");
                int id_prospect = rs.getInt("id_prospect");
                String adresseMail = rs.getString("adresseMail");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String commentarie = rs.getString("commentarie");
                LocalDate DateDeProspection = rs.getDate("DateDeProspection").toLocalDate();
                byte interesse = rs.getByte("interesse");
                ChoixUtilisateur.chioxInteresser choxInteresse = null;
                if (interesse == 1) {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.OUI;
                } else {
                    choxInteresse = ChoixUtilisateur.chioxInteresser.NON;
                }

                setProspect(id_prospect, name_prospect, adresseMail, telephone, commentarie, address, address, address, address,
                        DateDeProspection.format(formatter), choxInteresse);
                listProspect.add(prospect);
            }
        } catch (SQLException | ExceptionMetier e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void save(Connection con, Prospect upProspect) throws SQLException {
        Statement stmt = null;
        String query = null;
        if (upProspect.getId() == 0) {
            query = "insert into prospects (name_prospect, telephone, adresseMail, address, commentarie, DateDeProspection, interesse) VALUES (?, ?, ?, ?, ?, ? , ?)";
        } else {
            query = "UPDATE prospects SET name_prospect= ?,telephone= ?,adresseMail=?,address=? ,commentarie=?,DateDeProspection=?,interesse=?  WHERE  id_prospect = ?";
        }
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, upProspect.getSociale());
            preparedStmt.setString(2, upProspect.getTelephone());
            preparedStmt.setString(3, upProspect.getAdresseMail());
            preparedStmt.setString(4, upProspect.getAddress().getNumeroDeRue() +
                    upProspect.getAddress().getNomDeRue() + "" + upProspect.getAddress().getVille() +
                    "" + upProspect.getAddress().getCodePostal());
            preparedStmt.setString(5, upProspect.getCommentaries());
            Date date = Date.valueOf(upProspect.getLaDateDeProspection());
            preparedStmt.setDate(6, date);

            if (upProspect.getInteresse().toString().equals("OUI")) {
                preparedStmt.setByte(7, (byte) 1);
            }
            preparedStmt.setByte(7, (byte) 0);
            if (upProspect.getId() > 0) {
                preparedStmt.setInt(8, upProspect.getId());
            }
            // execute the preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(Connection con, int IdProspect) throws SQLException {
        Statement stmt = null;
        String query = "delete from prospects where id_prospect = ?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, IdProspect);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
