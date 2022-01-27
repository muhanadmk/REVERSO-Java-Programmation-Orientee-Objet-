package fr.afpa.pompey.cda08.demo.com.company.DAO;


import fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnexionManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnexionManager.class.getName());
    public ConnexionManager() {
    }

    private static Connection connexionBD = null;

    private static Connection conn() throws IOException, DaoSqlEx {
        final Properties dataProperties = new Properties();
        dataProperties.load(ConnexionManager.class.getClassLoader().getResourceAsStream("database.properties"));
        try {
            connexionBD = DriverManager.getConnection(
                    dataProperties.getProperty("url"),
                    dataProperties.getProperty("login"),
                    dataProperties.getProperty("password")
            );
            LOGGER.info("connexionBD a russie");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.fatal("err dans le connexion de BD" + e.getMessage());
            throw new DaoSqlEx("err dans le connexion de BD");
        }
        return connexionBD;
    }

    static{
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (ConnexionManager.getConnexionBD() != null) {
                        try {
                            LOGGER.info("Database fermée");
                            ConnexionManager.getConnexionBD().close();
                        } catch (SQLException | IOException | DaoSqlEx ex) {
                            LOGGER.fatal(ex.getMessage());
                        }
                    }
                } catch (IOException | DaoSqlEx e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Connection getConnexionBD() throws IOException, DaoSqlEx {
        return connexionBD = conn();
    }

}


