
package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.DAO.*;
import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;
import fr.afpa.pompey.cda08.demo.com.company.metier.Societe;
import fr.afpa.pompey.cda08.demo.com.company.utile.ChoixUtilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;
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
    private JButton sortirButton;
    private JButton homeButton;
    private JButton contartsButton;
    private JComboBox ClinetsQuiOntContra;
    private JComboBox listAfichage;
    private JButton listButton;
    private JButton aficheSonContrat;
    private ArrayList<Integer> listIdObject = new ArrayList<>();
    private ArrayList<Integer> clientsContrat = new ArrayList<>();
    private boolean flageClient = false;
    private boolean condtionAficheEdit = false;
    private boolean condtionAficheContrat = false;
    private Accueil accueilFreme;
    private static final Logger LOGGER = LogManager.getLogger(Accueil.class.getName());


    /**
     * constructeur classe d'accueil sur lequel on définit les mêmes on veut
     */

    public Accueil() {
        setContentPane(contentPane);
        //définir la taille de l'app
        setSize(800, 600);

        //hide cette section
        aficheSonContrat.setVisible(false);
        listAfichage.setVisible(false);
        listButton.setVisible(false);
        modifier.setVisible(false);
        suprimer.setVisible(false);
        manipulerSection.setVisible(false);
        ClinetsQuiOntContra.setVisible(false);
        contartsButton.setVisible(false);

/**
 *  quand clice on affiche la gestion des clients et cascher prospects
 */

        gestionDesClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listButton.setVisible(true);
                gestionDesProspectsButton1.setVisible(false);
                manipulerSection.setVisible(true);
                flageClient = true;
                choixclientsOuProspects = "Clients";
                contartsButton.setVisible(true);
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
                listButton.setVisible(true);
                flageClient = false;
                gestionDesClientsButton.setVisible(false);
                manipulerSection.setVisible(true);
                choixclientsOuProspects = "PROSPECTS";
            }
        });

/**
 *  quand clice on retourne ver page de l'Accueil et bien afficher la gestion des  prospects et clients
 */

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionDesClientsButton.setVisible(true);
                gestionDesProspectsButton1.setVisible(true);
                modifier.setVisible(false);
                suprimer.setVisible(false);
                listButton.setVisible(false);
                aficheSonContrat.setVisible(false);
                manipulerSection.setVisible(false);
                listAfichage.setVisible(false);
                contartsButton.setVisible(false);
                ClinetsQuiOntContra.setVisible(false);
                condtionAficheEdit = false;
                condtionAficheContrat = false;
                listButton.setVisible(false);
                listIdObject.clear();
                listAfichage.removeAllItems();
                flageClient = false;
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
                PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                        ChoixUtilisateur.choix.CREATION,
                        0);
                pageManipulerDeList.setVisible(true);
                dispose();
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAfichage.setVisible(true);
                listButton.setVisible(false);
                rempilerComboBox(flageClient);
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
                if (listAfichage.getSelectedIndex() != -1 && !listIdObject.isEmpty()) {
                    Integer idSociete = listIdObject.get(listAfichage.getSelectedIndex());
                    PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                            ChoixUtilisateur.choix.MODIFIER,
                            idSociete);
                    pageManipulerDeList.setVisible(true);
                    dispose();
                } else {
                    messageErr("msg info", "vous avez pas " + choixclientsOuProspects +
                            " pour edit");
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
                if (listAfichage.getSelectedIndex() != -1 && !listIdObject.isEmpty()) {
                    Integer idSociete = listIdObject.get(listAfichage.getSelectedIndex());
                    PageManipulerDeList pageManipulerDeList = new PageManipulerDeList(flageClient,
                            ChoixUtilisateur.choix.SUPRIMER,
                            idSociete);
                    pageManipulerDeList.setVisible(true);
                    dispose();
                } else {
                    messageErr("msg info", "vous avez pas " + choixclientsOuProspects +
                            " pour edit");
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
                if (!getListPourEdit(flageClient).isEmpty()) {
                    Affichage affichage = new Affichage(flageClient);
                    affichage.setVisible(true);
                    dispose();
                } else {
                    messageErr("msg info", "vous avez pas " + choixclientsOuProspects +
                            " pour affichier");
                }
            }
        });

        contartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplierClinetsQuiOntContrat();
            }
        });

        aficheSonContrat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContratsVue contratsVue = new ContratsVue(clientsContrat.get(ClinetsQuiOntContra.getSelectedIndex()),
                        ClinetsQuiOntContra.getSelectedItem().toString());
                contratsVue.setVisible(true);
                dispose();
            }
        });

        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    //recuperer tous les clients qui ont des contrats

    /**
     * @return lite du Cilent Qui Ont Contrat from BD
     */


    //pour Get tous les contrats dans le basee de données
    private ArrayList getListContrat() {
        try {
            return new DaoContrat().findAllCilentQuiOntContrat();
        } catch (ExceptionMetier exceptionMetier) {
            messageErr("Err ", exceptionMetier.getMessage());
        } catch (DaoSqlEx daoSqlEx) {
            if (daoSqlEx.getGravite() != 5) {
                messageErr("err basse de donne ", daoSqlEx.getMessage());
            } else {
                messageErr("message Err !!", "err System BD on doit fermer l'App");
                LOGGER.fatal("err on ne pas prevu " + daoSqlEx.getMessage());
                System.exit(1);
            }
        } catch (Exception ex) {
            LOGGER.fatal("err non prévue " + ex.getMessage());
            messageErr("err non prévue", "on doit femer l'app");
            System.exit(1);
        }
        return null;
    }

    /**
     * remplier  Clinets Qui Ont Contrat dans combobox de contrat "ClinetsQuiOntContra"
     * et arrylist "clientsContrat"
     */

    private void remplierClinetsQuiOntContrat() {
        if (!(getListContrat().isEmpty())) {
            ClinetsQuiOntContra.removeAllItems();
            clientsContrat.clear();
            ClinetsQuiOntContra.setVisible(true);
            aficheSonContrat.setVisible(true);
            for (int i = 0; i < getListContrat().size(); i++) {
                Client client = (Client) getListContrat().get(i);
                ClinetsQuiOntContra.addItem(client.getSociale());
                clientsContrat.add(client.getId());
            }
        } else {
            messageErr("message Info :", "vous avez pas des contrats");
        }
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
    // recuperer liste client ou procpect de la BD

    /**
     * getList clients ou porcpect Pour Edit from BD
     *
     * @param flageClient
     * @return
     */

    //getList clients ou porcpect Pour Edit from BD
    private ArrayList getListPourEdit(Boolean flageClient) {
        try {
            if (flageClient) {
                return new DaoClient().findAll();
            } else {
                return new DaoProspect().findAll();
            }
        } catch (DaoSqlEx daoSqlEx) {
            if (daoSqlEx.getGravite() != 5) {
                messageErr("err basse de donne ", daoSqlEx.getMessage());
            } else {
                messageErr("message Err !!", "err System BD on doit fermer l'App");
                LOGGER.fatal("err on ne pas prevu " + daoSqlEx.getMessage());
                System.exit(1);
            }
        } catch (ExceptionMetier exceptionMetier) {
            messageErr("ERR  ", exceptionMetier.getMessage());
        } catch (IndexOutOfBoundsException errIndex) {
            errIndex.printStackTrace();
            messageErr("message Err !!", "vous avez pas société a les manipulers");
        } catch (Exception ex) {
            LOGGER.fatal("err non prévue " + ex.getMessage());
            messageErr("err non prévue", "on doit femer l'app");
            System.exit(1);
        }
        return null;
    }

    // remplir combobox de clinet ou procpect
    private void rempilerComboBox(Boolean flageClient) {
        if (!getListPourEdit(flageClient).isEmpty()) {
            modifier.setVisible(true);
            suprimer.setVisible(true);
            listAfichage.removeAllItems();
            listIdObject.clear();
            for (int i = 0; i < getListPourEdit(flageClient).size(); i++) {
                Societe societe = (Societe) getListPourEdit(flageClient).get(i);
                listAfichage.addItem(societe.getSociale());
                listIdObject.add(societe.getId());
            }
        } else {
            modifier.setVisible(false);
            suprimer.setVisible(false);
            listAfichage.setVisible(false);
            messageErr("message Info :", "vous avez pas des " + choixclientsOuProspects +
                    " pour les afichier");
        }
    }

}

