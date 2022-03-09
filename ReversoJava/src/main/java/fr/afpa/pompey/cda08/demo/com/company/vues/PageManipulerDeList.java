
package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.DAO.*;
import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;
import fr.afpa.pompey.cda08.demo.com.company.utile.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeParseException;
import javax.swing.JTextField;

import static fr.afpa.pompey.cda08.demo.com.company.utile.Utilitaire.formatter;


/**
 * Le page on fait le crrer modification et suppression
 */

public class PageManipulerDeList extends JFrame {
    private JPanel contentPane;
    private JButton homeButton;
    private JTextField nomSociale;
    private JButton submitButton;
    private JTextField chiffreDaffaire;
    private JTextField telephone;
    private JTextField email;
    private JTextField nombreDeEmployes;
    private JTextField commentaire;
    private JTextField numeroDeRue;
    private JTextField NomDeRue;
    private JTextField ville;
    private JTextField cd;

    private JTextField id;
    private JPanel modeIInfo;
    private JPanel modeleAdress;
    private JLabel LableModifierOuSuprimer;
    private JLabel idLable;
    private JTextField date;
    private JLabel lableDate;
    private JLabel lableInteresse;
    private JLabel labalChiffre;
    private JLabel labeNomre;
    private JLabel commentaireLabel;
    private JComboBox interesseProspect;
    private JButton sortirButton;
    private Boolean err = true;
    private ChoixUtilisateur.chioxInteresser choixInteresseProspect;
    private Client client;
    private Prospect prospect;
    private PageManipulerDeList pageManipulerFere;
    private static final Logger LOGGER = LogManager.getLogger(PageManipulerDeList.class.getName());


    /**
     * on récupère la paramètre si le choix de user un client si le falge no alors on travaille sur prospect
     *
     * @param flageClient
     * @param choix
     * @throws ExceptionMetier
     */

    public PageManipulerDeList(boolean flageClient, ChoixUtilisateur.choix choix, Integer idSociete) {

        pageManipulerFere = this;
        setContentPane(contentPane);
        modeIInfo.setVisible(false);
        modeleAdress.setVisible(false);
        setSize(800, 600);
        if (flageClient) {
            date.setVisible(false);
            interesseProspect.setVisible(false);
            lableInteresse.setVisible(false);
            lableDate.setVisible(false);
        } else {
            interesseProspect.addItem(ChoixUtilisateur.chioxInteresser.OUI);
            interesseProspect.addItem(ChoixUtilisateur.chioxInteresser.NON);
            // choixInteresseProspect par deflet oui
            choixInteresseProspect = ChoixUtilisateur.chioxInteresser.OUI;
            interesseProspect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch (interesseProspect.getSelectedItem().toString()) {
                        case "NON":
                            choixInteresseProspect = ChoixUtilisateur.chioxInteresser.NON;
                            break;
                        case "OUI":
                            choixInteresseProspect = ChoixUtilisateur.chioxInteresser.OUI;
                            break;
                    }
                }
            });
            labalChiffre.setVisible(false);
            labeNomre.setVisible(false);
            chiffreDaffaire.setVisible(false);
            nombreDeEmployes.setVisible(false);
        }


/**
 * qu'on récupère de la méthode chiox de class
 * choix utilisateurs
 * on affiche où on désactivé des textes filde solon le choix
 */


        switch (choix.toString()) {
            case "CREATION":
                modeIInfo.setVisible(true);
                modeleAdress.setVisible(true);
                submitButton.setText("CREATION");
                id.setVisible(false);
                idLable.setVisible(false);
                break;
            case "MODIFIER":
                submitButton.setText("MODIFIER");
                instertSocieteTextFiled(flageClient, idSociete);
                id.setEnabled(false);
                break;
            case "SUPRIMER":
                instertSocieteTextFiled(flageClient, idSociete);
                submitButton.setText("SUPRIMER");
                setEnabledEnCasDeSuprimer(false);
                break;
        }


/**
 * cette btn sera selon le choix de user si créer ou supprimer ou modifier
 */

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (flageClient) {
                        client = new Client(idSociete,
                                nomSociale.getText(), email.getText(), telephone.getText(), commentaire.getText(),
                                new Address(numeroDeRue.getText(), NomDeRue.getText(), cd.getText()
                                        , ville.getText()),
                                Double.parseDouble(chiffreDaffaire.getText()),
                                Long.parseLong(nombreDeEmployes.getText()));
                        System.out.println(client.getId() + " client.getId() ");
                        err = true;
                    } else {
                        prospect = new Prospect(idSociete, nomSociale.getText(), email.getText(),
                                telephone.getText(), commentaire.getText(),
                                new Address(numeroDeRue.getText(), NomDeRue.getText(),
                                        cd.getText(), ville.getText()),
                                Utilitaire.dateInput(date.getText()), choixInteresseProspect);
                        err = true;
                    }
                    switch (choix.toString()) {
                        case "CREATION":
                        case "MODIFIER":
                            if (err) {
                                if (flageClient) {
                                    int id = new DaoClient().save( client);
                                    client.setId(id);
                                } else {
                                    int id = new DaoProspect().save( prospect);
                                    prospect.setId(id);
                                }
                                if (choix.toString().equals("CREATION")) {
                                    affichageAccueilEtFermerLeEdit("vous avez bien cree");
                                } else {
                                    affichageAccueilEtFermerLeEdit("vous avez bien MODIFIER");
                                }
                            }
                            break;
                        case "SUPRIMER":
                            int isSuprimer = JOptionPane.showConfirmDialog(null,
                                    "vous etes sur de SUPRIMER",
                                    "SUPRIMER", JOptionPane.YES_NO_OPTION);
                            if (isSuprimer == 0) {
                                if (flageClient) {
                                    new DaoClient().delete(idSociete);
                                } else {
                                    new DaoProspect().delete( idSociete);

                                }
                                applAccueil();
                            }
                            break;
                    }
                }catch (NumberFormatException n) {
                    err = false;
                    messageErr("message Err !!", "vous devez ecrir une chiiffre pour nomber employer " +
                            "et chiffre daffire");
                }  catch (DateTimeParseException dataFormatException) {
                    err = false;
                    messageErr("message Err !!", "mauvaise date!! ecrire un format de date comme dd/MM/yyyy");
                }catch (ExceptionMetier ea) {
                    err = false;
                    messageErr("message Err !!", ea.getMessage());
                } catch (DaoSqlEx daoSqlEx) {
                    err = false;
                    if (daoSqlEx.getGravite() != 5) {
                        messageErr("err basse de donne ", daoSqlEx.getMessage());
                    } else {
                        messageErr("message Err !!", "err System BD on doit fermer l'App");
                        LOGGER.fatal("err on ne pas prevu " + daoSqlEx.getMessage());
                        System.exit(1);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    err = false;
                    messageErr("message Err !!", "err System essyeer puls tard");
                    LOGGER.fatal("err on ne pas prevu" + exception.getMessage());
                    System.exit(1);
                }
            }
        });

/**
 * pour retoue a la page accueil
 */

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Accueil accueil = new Accueil();
                accueil.setVisible(true);
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
     * instert objetc Societe qui vinne de bd accueil into TextFiled
     *
     * @param flageClient
     * @param idSociete
     */
    //instert objetc Societe qui vinne de bd accueil into TextFiled
    private void instertSocieteTextFiled(Boolean flageClient, Integer idSociete) {
        Societe societe = null;
        try {
            if (flageClient) {
                societe = new DaoClient().find(idSociete);
                Client clientTxtFiled = ((Client) societe);
                chiffreDaffaire.setText(Double.toString(clientTxtFiled.getLeChiffreDaffaire()));
                nombreDeEmployes.setText(Long.toString(clientTxtFiled.getLeNombreDemployes()));
            } else {
                societe = new DaoProspect().find(idSociete);
                Prospect prospectTxtFild = ((Prospect) societe);
                date.setText(prospectTxtFild.getLaDateDeProspection().format(formatter));
                interesseProspect.setSelectedItem(prospectTxtFild.getInteresse());
            }
            id.setText(String.valueOf(idSociete));
            nomSociale.setText(societe.getSociale());
            telephone.setText(societe.getTelephone());
            cd.setText(societe.getAddress().getCodePostal());
            email.setText(societe.getAdresseMail());
            commentaire.setText(societe.getCommentaries());
            numeroDeRue.setText(societe.getAddress().getNumeroDeRue());
            NomDeRue.setText(societe.getAddress().getNumeroDeRue());
            ville.setText(societe.getAddress().getVille());
            modeIInfo.setVisible(true);
            modeleAdress.setVisible(true);
        } catch (DaoSqlEx e) {
            if (e.getGravite() != 5) {
                messageErr("err basse de donne ", e.getMessage());
            } else {
                messageErr("message Err !!", "err System on doit ferem l'App");
                LOGGER.fatal("err on ne pas prevu" + e.getMessage());
                System.exit(1);
            }
        } catch (Exception ex) {
            LOGGER.fatal("err non prévue " + ex.getMessage());
            messageErr("err non prévue", "on doit femer l'app");
            System.exit(1);
        }

    }


    /**
     * pour former la page édition et aller dans le page d'accueil
     * on pass en paramètre la message qu'on veut afficher
     *
     * @param msg
     */

    private void affichageAccueilEtFermerLeEdit(String msg) {
        JOptionPane.showConfirmDialog(null,
                msg,
                "Appuyer sur ok pour continuer!", JOptionPane.DEFAULT_OPTION);
        applAccueil();
    }

    private void applAccueil() {
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
        dispose();
    }

    /**
     * pour ne pas modifier les textes filde quand on est dans le cadre de suppression
     *
     * @param faux
     */

    private void setEnabledEnCasDeSuprimer(Boolean faux) {
        nomSociale.setEnabled(faux);
        chiffreDaffaire.setEnabled(faux);
        telephone.setEnabled(faux);
        email.setEnabled(faux);
        nombreDeEmployes.setEnabled(faux);
        commentaire.setEnabled(faux);
        numeroDeRue.setEnabled(faux);
        NomDeRue.setEnabled(faux);
        ville.setEnabled(faux);
        cd.setEnabled(faux);
        id.setEnabled(faux);
        date.setEnabled(faux);
        interesseProspect.setEnabled(faux);
    }

    /**
     * on affiche message de erreur
     *
     * @param titleBox
     * @param messageErr
     */

    private void messageErr(String titleBox, String messageErr) {
        JOptionPane.showConfirmDialog(null,
                messageErr,
                titleBox, JOptionPane.DEFAULT_OPTION);
    }

    //get object For Edit
    private Societe getobjectForEdit(Boolean flageClient) {
        if (flageClient) {
            return client;
        }
        return prospect;
    }

}

