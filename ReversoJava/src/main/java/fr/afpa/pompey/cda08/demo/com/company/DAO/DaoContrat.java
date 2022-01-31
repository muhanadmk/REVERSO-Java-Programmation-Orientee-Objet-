package fr.afpa.pompey.cda08.demo.com.company.DAO;


import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Contrat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoContrat {
    private static PreparedStatement preparedStmt;
    public DaoContrat(){}
    public static ArrayList<Contrat> findByIdClient(Connection con, int Idcliente) throws DaoSqlEx {
        ArrayList<Contrat> listContrat = new ArrayList<>();
        try {
            String query = "SELECT * FROM `contrat` INNER JOIN clients ON contrat.idClient = clients.Id_cliente" +
                " WHERE idClient=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Idcliente);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
            Contrat contrat= new Contrat(rs.getInt("id"), rs.getInt("idClient"),
                    rs.getString("libelleDeContrat"),rs.getDouble("montantDeContrat"));
                System.out.println(contrat);
            listContrat.add(contrat);
        }
    }  catch (SQLException e ) {
        throw new DaoSqlEx("error base de données vous pouvez pas recuperer les contrats essaiez ultiareemnt");
    } finally {
        if (preparedStmt!= null) {
            try {
                preparedStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DaoSqlEx("error base de données essaiez ultiareemnt");
            }
        }
    }
        return listContrat;
    }
}
