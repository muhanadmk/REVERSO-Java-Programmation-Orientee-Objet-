package fr.afpa.pompey.cda08.demo.com.company.DAO;


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

    public static Connection conn() throws DaoSqlEx {
        final Properties dataProperties = new Properties();
        try {
            dataProperties.load(ConnexionManager.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            LOGGER.fatal("err dans le parm de connexion de BD" + e.getMessage());
            throw new DaoSqlEx("err dans le parm de connexion de BD");
        }
        try {
            connexionBD = DriverManager.getConnection(
                    dataProperties.getProperty("url"),
                    dataProperties.getProperty("login"),
                    dataProperties.getProperty("password")
            );
            LOGGER.info("connexionBD a russie");
        } catch (SQLException e) {
            LOGGER.fatal("err dans le connexion de BD" + e.getMessage());
            throw new DaoSqlEx("err dans le connexion de BD");
        }
        return connexionBD;
    }

    static{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try {
                    if (ConnexionManager.conn() != null) {
                        try {
                            ConnexionManager.conn().close();
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


