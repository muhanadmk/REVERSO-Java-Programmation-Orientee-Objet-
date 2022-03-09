
package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.DAO.ConnexionManager;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoClient;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoProspect;
import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;
import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * classe pour afficher en jatble le liste qui le user a choisi
 */

public class Affichage extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JButton sortirButton;
    private JButton homeButton;
    private JScrollPane scrollpane;
    private String[][] data;
    private static final Logger LOGGER = LogManager.getLogger(Affichage.class.getName());


    /**
     * on récupère si client si non on traite le prospect
     *
     * @param flageClient
     * @throws ExceptionMetier
     */

    public Affichage(Boolean flageClient) {

        //définir la taille de l'app
        setSize(800, 600);
        setContentPane(contentPane);
        scrollpane.setVisible(true);

        // le column Names de Jtable
        String[] columnNames;
        if (flageClient) {
            columnNames = new String[]{"ID", "raison sociale", "Email", "Téléphone", "Address", "chiffre d’affaire",
                    "nombre d'employé"};
        } else {
            columnNames = new String[]{"ID", "raison sociale", "Email", "Téléphone", "Address", "Date De Prospection",
                    "Interesse"};
        }

        // définir un modle Jtable
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return columnNames.length;
            }

            public String getColumnName(int col) {
                return columnNames[col];
            }

            public int getRowCount() {
                return data.length;
            }

            public Object getValueAt(int row, int col) {
                return data[row][col];
            }
        };
        table.setModel(dataModel);

        // on rempile le tableau en utilisant la taille de la list qu'on a récupéré
        if (getListAfichge(flageClient) != null || !(getListAfichge(flageClient).isEmpty())) {
            data = new String[getListAfichge(flageClient).size()][7];
            for (int i = 0; i < getListAfichge(flageClient).size(); i++) {
                for (int j = 0; j < 7; j++) {
                    Societe societe = (Societe) getListAfichge(flageClient).get(i);
                    data[i][j++] = String.valueOf(societe.getId());
                    data[i][j++] = societe.getSociale();
                    data[i][j++] = societe.getAdresseMail();
                    data[i][j++] = societe.getTelephone();
                    data[i][j++] = societe.getAddress().getNumeroDeRue() + " " + societe.getAddress().getNomDeRue() +
                            " " + societe.getAddress().getVille() + " " + societe.getAddress().getCodePostal();
                    if (flageClient) {
                        Client client = (Client) getListAfichge(flageClient).get(i);
                        data[i][j++] = String.valueOf(client.getLeChiffreDaffaire());
                        data[i][j++] = String.valueOf(client.getLeNombreDemployes());
                    } else {
                        Prospect prospect = (Prospect) getListAfichge(flageClient).get(i);
                        data[i][j++] = String.valueOf(prospect.getLaDateDeProspection());
                        data[i][j++] = String.valueOf(prospect.getInteresse());
                    }
                }
            }
        }


        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retourVersAccueil();
            }
        });
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }


    private ArrayList getListAfichge(Boolean flageClient) {
        try {
            if (flageClient) {
                return new DaoClient().findAll();
            } else {
                return new DaoProspect().findAll();
            }
        } catch (DaoSqlEx sq) {
            if (sq.getGravite() != 5) {
                messageErr("err basse de donne ", sq.getMessage());
            } else {
                messageErr("message Err !!", "err System BD on doit fermer l'App");
                LOGGER.fatal("err on ne pas prevu " + sq.getMessage());
                System.exit(1);
            }
        } catch (ExceptionMetier exceptionMetier) {
            messageErr("ERR BD ", exceptionMetier.getMessage());
        }catch (Exception ex) {
            LOGGER.fatal("err non prévue " + ex.getMessage());
            messageErr("err non prévue", "on doit femer l'app");
            System.exit(1);
        }
        return null;
    }

//pour fermer cette page et retourner à la page d'accueil
    private void retourVersAccueil() {
        dispose();
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
    }

    //pour afficher msg personnaliser aux utilisateurs
    private void messageErr(String titleBox, String messageErr) {
        JOptionPane.showConfirmDialog(null,
                messageErr,
                titleBox, JOptionPane.DEFAULT_OPTION);
    }
}


