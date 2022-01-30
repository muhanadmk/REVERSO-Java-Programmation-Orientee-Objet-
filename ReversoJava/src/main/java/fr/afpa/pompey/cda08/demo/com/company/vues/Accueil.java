package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.DAO.ConnexionManager;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoClient;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoProspect;
import fr.afpa.pompey.cda08.demo.com.company.DAO.DaoSqlEx;
import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Societe;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * c'est la première page de l'application, la page d'accueil sur laquelle on montre pour les user
 * ce qu'il va choisir entre gestion de client ou prospect,
 * cela ca serait dans seul écran s'adapter à le choix de la user
 */
public class Accueil extends JFrame {

    private JPanel contentPane;
    private JButton gestionDesClientsButton;
    private JButton gestionDesProspectsButton1;
    private JButton creationButton;
    private JPanel clientsOuProspects;
    private String choixclientsOuProspects;
    private JPanel manipulerSection;
    private JButton affichageeButton;
    private JButton suprimer;
    private JButton modifier;
    private JComboBox listAficha;
    private JButton sortirButton;
    private JButton homeButton;
    private ArrayList <Societe> listDeObjectTeleharger = new ArrayList<>();
    private boolean flageClient = false;
    private boolean condtionAficheEdit = false;
    private Accueil accueilFreme;

    /**
     * constructeur classe d'accueil sur lequel on définit les mêmes on veut
     */
    public Accueil() {
        setContentPane(contentPane);
        //définir la taille de l'app
        setSize(800, 600);
        listAficha.setVisible(false);
        //hide cette section
        manipulerSection.setVisible(false);
/**
 *  quand clice on affiche la gestion des clients et cascher prospects
 */
        gestionDesClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionDesProspectsButton1.setVisible(false);
                manipulerSection.setVisible(true);
                flageClient = true;
                choixclientsOuProspects = "Clients";
                rempilerComboBox(true);
            }
        });

        /**
         *  quand clice on affiche la gestion des  prospects et cascher  clients
         *  on envoie en tant que paramètre le choix le user si client si no le PageManipulerDeList
         *  il sait que ca un prospect
         */
        gestionDesProspectsButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionDesClientsButton.setVisible(false);
                manipulerSection.setVisible(true);
                choixclientsOuProspects = "PROSPECTS";
                rempilerComboBox(false);
            }
        });
        /**
         *  quand clice on retourne ver page de l'Accueil et bien afficher la gestion des  prospects et clients
         */
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manipulerSection.setVisible(false);
                gestionDesClientsButton.setVisible(true);
                gestionDesProspectsButton1.setVisible(true);
                listAficha.removeAllItems();
                listAficha.setVisible(false);
                condtionAficheEdit = false;
            }
        });
        /**
         * quand on clique on va ouvrir la Page Manipuler De List
         * et on catch s'il y a des erreurs
         * et on va directement sur la création
         *  on envoie en tant que paramètre le choix le user si client si no le PageManipulerDeList
         *          il sait que ca un prospect
         */
        creationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                            ChoixUtilisateur.choix.CREATION,
                            null);
                    pageManipulerDeList.setVisible(true);
                    dispose();
                }catch (DaoSqlEx daoSqlEx){
                    daoSqlEx.printStackTrace();
                }

            }
        });
        /**
         * quand on clique on va ouvrir la Page Manipuler De List
         * et on catch s'il y a des erreurs
         * et on va directement sur la modifier et on peut modifier tout Suaf le id
         * on envoie en tant que paramètre le choix le user si client si no le PageManipulerDeList
         *         il sait que ca un prospect
         */
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAficha.setVisible(true);
                try {
                    if (condtionAficheEdit) {
                        try {
                            Societe societe = listDeObjectTeleharger.get(listAficha.getSelectedIndex());
                            PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                                    ChoixUtilisateur.choix.MODIFIER,
                                    societe);
                            pageManipulerDeList.setVisible(true);
                            dispose();
                        } catch (DaoSqlEx daoSqlEx) {
                            daoSqlEx.printStackTrace();
                        }
                    }
                    condtionAficheEdit = true;
                } catch (IndexOutOfBoundsException errIndex) {
                    listAficha.removeAllItems();
                    listAficha.setVisible(false);
                    errIndex.printStackTrace();
                    messageErr("message Err !!", "vous avez pas société a les manipulers");
                }
            }
        });
        /**
         * quand on clique on va ouvrir la Page Manipuler De List
         * et on catch s'il y a des erreurs
         * et on va directement sur la suprimer et on peut pas modifier , on peut que suprimer
         *  on envoie en tant que paramètre le choix le user si client si no le PageManipulerDeList
         *     il sait que ca un prospect
         */
        suprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAficha.setVisible(true);
                try {
                    if (condtionAficheEdit) {
                            try {
                                Societe societe = listDeObjectTeleharger.get(listAficha.getSelectedIndex());
                                PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                                        ChoixUtilisateur.choix.SUPRIMER,
                                        societe);
                                pageManipulerDeList.setVisible(true);
                                dispose();
                            } catch (DaoSqlEx daoSqlEx) {
                                daoSqlEx.printStackTrace();
                            }
                        }

                    condtionAficheEdit = true;
                } catch (IndexOutOfBoundsException errIndex) {
                    listAficha.removeAllItems();
                    listAficha.setVisible(false);
                    errIndex.printStackTrace();
                    messageErr("message Err !!", "vous avez pas société a les manipulers");
                }
            }
        });
        /**
         * quand on clique on va ouvrir la Page affichagee
         * et on catch s'il y a des erreurs
         * et on va directement sur J table qui va affiche ce qui est dans le liste
         *  on envoie en tant que paramètre le choix le user si client si no le PageManipulerDeList
         *          il sait que ca un prospect
         */
        affichageeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (listAficha.getItemCount() != 0) {
                        Affichage affichage = new Affichage(flageClient);
                        affichage.setVisible(true);
                        dispose();
                    } else {
                        messageErr("message Err !!", "vous avez pas " + choixclientsOuProspects + " " +
                                "a les afficher");
                    }
                } catch (DaoSqlEx daoSqlEx) {
                    daoSqlEx.printStackTrace();
                }
            }
        });
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * méthode pour récupérer l'erreur et l'afficher sur l'écran et après on retronne à la page d'accueil
     *
     * @param exception
     */
    private void retourAlacuile(ExceptionMetier exception) {
        messageErr("Msg Eerr", exception.getMessage());
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
        accueil.getListAficha().setVisible(false);
    }

    /**
     * méthode pour récupérer l'erreur et l'afficher sur l'écran et après on retronne à la page d'accueil
     * sauf cette méthode on écrit le message de erreur
     *
     * @param titleBox
     * @param messageErr
     */
    private void messageErr(String titleBox, String messageErr) {
        JOptionPane.showConfirmDialog(null,
                messageErr,
                titleBox, JOptionPane.DEFAULT_OPTION);
    }

    private ArrayList getListPourEdit(Boolean isClient) {
        try {
            if (isClient) {
                return DaoClient.findAll(ConnexionManager.conn());
            } else {
                return DaoProspect.findAll(ConnexionManager.conn());
            }
        } catch (DaoSqlEx daoSqlEx) {
            daoSqlEx.printStackTrace();
            return null;
        }
    }

    private void rempilerComboBox(Boolean isClient) {
        if (listAficha == null || listAficha.getItemCount() == 0) {
            for (int i = 0; i < getListPourEdit(isClient).size(); i++) {
                Societe societe = (Societe) getListPourEdit(isClient).get(i);
                listAficha.addItem(societe.getSociale());
                listDeObjectTeleharger.add(societe);
            }
        }
    }

    public JComboBox getListAficha() {
        return listAficha;
    }
}
