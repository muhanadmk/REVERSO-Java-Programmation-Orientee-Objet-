package fr.afpa.pompey.cda08.demo.com.company.DAO;


import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnexionManager.class.getName());
    public ConnexionManager() {
    }

    private static Connection connexionBD = null;

    public static Connection getConnexionBD() throws DaoSqlEx {
        if (connexionBD == null){
            try {
                final Properties dataProperties = new Properties();
                dataProperties.load(ConnexionManager.class.getClassLoader().getResourceAsStream("database.properties"));
                connexionBD = DriverManager.getConnection(
                        dataProperties.getProperty("url"),
                        dataProperties.getProperty("login"),
                        dataProperties.getProperty("password")
                );
            } catch (SQLException e) {
                LOGGER.fatal("connexion échouer de BD " + e.getMessage());
                throw new DaoSqlEx("connexion échouer de BD l'App va fermer", 5);
            }catch (IOException e) {
                LOGGER.fatal("err dans le parm de connexion de BD dans le fichier database.properties" + e.getMessage());
                throw new DaoSqlEx("connexion échouer de BD l'App va fermer", 5);
            }
        }
        return connexionBD;
    }

    static{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try {
                    if (ConnexionManager.getConnexionBD() != null) {
                        try {
                            ConnexionManager.getConnexionBD().close();
                            LOGGER.info("Database fermée");
                        } catch (SQLException ex) {
                            LOGGER.fatal(ex.getMessage());
                            throw new DaoSqlEx("err dans le connexion de BD");
                        }
                    }
                } catch (DaoSqlEx e) {
                    LOGGER.fatal("Database ne pas bien fermée" + e.getMessage());
                }
            }
        });
    }
}


