package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.DAO.*;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Contrat;
import fr.afpa.pompey.cda08.demo.com.company.metier.Prospect;
import fr.afpa.pompey.cda08.demo.com.company.metier.Societe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ContratsVue extends JFrame {
    private JPanel contentPane;
    private JButton sortirButton;
    private JButton homeButton;
    private JTable contratsTable;
    private JPanel jPanel;
    private JScrollPane scrollable;
    private static final Logger LOGGER = LogManager.getLogger(ContratsVue.class.getName());


    private String[][] contrats;

    public ContratsVue(int idClient) {

        setSize(800, 600);
        setContentPane(contentPane);
        scrollable.setVisible(true);
        // le column Names de Jtable
        String[] columnNames = new String[]{"IdContrat", "IdClient", "libellé de contrat", "montant de contrat "};

        // définir un modle Jtable
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return columnNames.length;
            }

            public String getColumnName(int col) {
                return columnNames[col];
            }

            public int getRowCount() {
                return contrats.length;
            }

            public Object getValueAt(int row, int col) {
                return contrats[row][col];
            }
        };
        contratsTable.setModel(dataModel);
        // on rempile le tableau en utilisant la taille de la list qu'on a récupéré
        if (getListContrats(idClient) != null || !getListContrats(idClient).isEmpty()) {
            contrats = new String[getListContrats(idClient).size()][4];
            for (int i = 0; i < getListContrats(idClient).size(); i++) {
                for (int j = 0; j < 4; j++) {
                    Contrat contrat = (Contrat) getListContrats(idClient).get(i);
                    contrats[i][j++] = String.valueOf(contrat.getId());
                    contrats[i][j++] = String.valueOf(contrat.getIdClient());
                    contrats[i][j++] = contrat.getLibelleDeContrat();
                    contrats[i][j++] = String.valueOf(contrat.getMontantDeContrat());
                }
            }
        }
    else {
            JOptionPane.showConfirmDialog(null,
                    "vous avez rine a afichier",
                    "message de Erreur", JOptionPane.DEFAULT_OPTION);
        }

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Accueil accueil = new Accueil();
                accueil.setVisible(true);
                accueil.getListAficha().setVisible(false);
            }
        });
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    private ArrayList getListContrats(int idClient) {
        ArrayList <Contrat> listContrats = new ArrayList();
        try {
            listContrats = new DaoContrat().findByIdClient(ConnexionManager.conn(),idClient);
        } catch (DaoSqlEx sq) {
            LOGGER.info("err BD", sq.getMessage());
            messageErr("ERR BD", sq.getMessage());
        }
        return listContrats;
    }
    private void messageErr(String titleBox, String messageErr) {
        JOptionPane.showConfirmDialog(null,
                messageErr,
                titleBox, JOptionPane.DEFAULT_OPTION);
    }
}
