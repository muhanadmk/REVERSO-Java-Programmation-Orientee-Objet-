package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.formatter;


import java.sql.*;
import java.util.ArrayList;

/**
 *
 *    class DaoClient qui gere aller router avel le Bd en cas de Prospect
 *
 */
public class DaoProspect extends DAO<Prospect> {
    private static final Logger LOGGER = LogManager.getLogger(DaoClient.class.getName());
    private static Statement stmt = null;
    private static PreparedStatement preparedStmt = null;
    private static Prospect prospect =null;
    public DaoProspect() {
    }

    /**
     * findAll pour recuperer les prospects de BD
     * @return ArrayList<Prospect>
     * @throws DaoSqlEx
     * @throws ExceptionMetier
     */
    public ArrayList<Prospect>findAll() throws DaoSqlEx,ExceptionMetier, Exception {
        String query = "SELECT * FROM prospects";
        ArrayList<Prospect> listProspect = new ArrayList();
        try {
            stmt = ConnexionManager.getConnexionBD().createStatement();
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
            if (stmt != null) {
                stmt.close();
            }
        }catch (SQLException e ) {
            e.printStackTrace();
            LOGGER.info("err Basee de donnees ,vous n'avez pas reussi a recuperer les prospects essaiez ultiareemnt "+
                    e.getMessage());
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer les prospects "
            +"essaiez ultiareemnt");
        }
        return listProspect;
    }

    /**
     * cette methode recuperate un seul Prospect en ut lisant son Id
     * @param idProspect
     * @return prospect
     * @throws DaoSqlEx
     * @throws ExceptionMetier
     */
    public Prospect find(Integer idProspect) throws DaoSqlEx,ExceptionMetier, Exception {
        String sql = "SELECT * FROM prospects where id_prospect = ?";
        try {
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(sql);
            preparedStmt.setInt(1, idProspect);
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
            if (stmt != null) {
                stmt.close();
            }
        }  catch (SQLException e ) {
            e.printStackTrace();
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a recuperer le prospects" +
                    "essaiez ultiareemnt");
        }
        return prospect;
    }

    /**
     * cette methode cree ou modifier un seul client
     * @param upProspect
     * @return id de client en cas cree
     * @throws DaoSqlEx
     */
    public Integer save(Prospect upProspect) throws DaoSqlEx, Exception {
        Integer id=0;
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
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query,
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
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        } catch (SQLException e ) {
            throw new DaoSqlEx("error Basee de donnees ,vous n'avez pas reussi a modifier ou" +
                    " cree un procpect essaiez ultiareemnt");
        }
        return id;
    }

    /**
     * cette methode supprimer un seul prospect en utlisant son Id
     * @param con
     * @param IdProspect
     * @throws DaoSqlEx
     */
    public void delete(Integer IdProspect) throws DaoSqlEx, Exception{
        String query = "delete from prospects where id_prospect = ?";
        try {
            preparedStmt = ConnexionManager.getConnexionBD().prepareStatement(query);
            preparedStmt.setInt(1, IdProspect);
            // execute the preparedstatement
            preparedStmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DaoSqlEx("err Basee de donnees ,vous n'avez pas reussi a delete prospect " +
                    " essaiez ultiareemnt");
        }
    }


}
