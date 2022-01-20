package fr.afpa.pompey.cda08.demo.com.company.vues;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.*;
import fr.afpa.pompey.cda08.demo.com.company.utile.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    /**
     * on récupère la paramètre si le choix de user un client si le falge no alors on travaille sur prospect
     *
     * @param flageClient
     * @param choix
     * @throws ExceptionMetier
     */
    public PageManipulerDeList(boolean flageClient, ChoixUtilisateur.choix choix, int getIndexListe) {
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
                remplirTextFild(flageClient, getIndexListe);
                id.setEnabled(false);
                break;
            case "SUPRIMER":
                remplirTextFild(flageClient, getIndexListe);
                submitButton.setText("SUPRIMER");
                setEnabledEnCasDeSuprimer(false);
                break;
        }

        /**
         * cette btn sera selon le choix de user si créer ou supprimer ou modifier
         */
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (flageClient) {
                    try {
                        client = new Client(
                                nomSociale.getText(), email.getText(), telephone.getText(), commentaire.getText(),
                                new Address(numeroDeRue.getText(), NomDeRue.getText(), cd.getText()
                                        , ville.getText()),
                                Double.parseDouble(chiffreDaffaire.getText()),
                                Long.parseLong(nombreDeEmployes.getText()));
                        err = true;
                    } catch (ExceptionMetier ea) {
                        err = false;
                        messageErr("message Err !!", ea.getMessage());
                    } catch (NumberFormatException n) {
                        err = false;
                        messageErr("message Err !!", "vous devez ecrir une chiiffre pour nomber employer " +
                                "et chiffre daffire");
                    } catch (Exception exception) {
                        err = false;
                        messageErr("message Err !!", "err System essyeer puls tard");
                        System.exit(1);
                    }
                } else {
                    try {
                        prospect = new Prospect(nomSociale.getText(), email.getText(),
                                telephone.getText(), commentaire.getText(),
                                new Address(numeroDeRue.getText(), NomDeRue.getText(),
                                        cd.getText(), ville.getText()),
                                Utilitaire.dateInput(date.getText()), choixInteresseProspect);
                        err = true;
                    } catch (ExceptionMetier ea) {
                        err = false;
                        messageErr("message Err !!", ea.getMessage());
                    } catch (DateTimeParseException dataFormatException) {
                        err = false;
                        messageErr("message Err !!", "mauvaise date!! ecrire un format de date comme dd/MM/yyyy");
                    } catch (Exception exception) {
                        err = false;
                        messageErr("message Err !!", "err System essyeer puls tard");
                        System.exit(1);
                    }
                }

                switch (choix.toString()) {
                    case "CREATION":
                        if (err) {
                            getListPourEdit(flageClient).add(getobjectForEdit(flageClient));
                            affichageAccueilEtFermerLeEdit("vous avez bien cree");
                        }

                        break;

                    case "MODIFIER":
                        if (err) {
                            getListPourEdit(flageClient).set(getIndexListe, getobjectForEdit(flageClient));
                            affichageAccueilEtFermerLeEdit("vous avez bien MODIFIER");
                        }

                        break;

                    case "SUPRIMER":
                        int isSuprimer = JOptionPane.showConfirmDialog(null,
                                "vous etes sur de SUPRIMER",
                                "SUPRIMER", JOptionPane.YES_NO_OPTION);
                        if (isSuprimer == 0) {
                            getListPourEdit(flageClient).remove(getIndexListe);
                            applAccueil();
                        }

                        break;
                }
            }
        });
        /**
         * pour retoue a la page accueil
         */
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
                System.exit(2);
            }
        });
    }

    /**
     * remplie le data qui sont partger
     *
     * @param avoirDataClient
     */
    private void setData(Societe avoirDataClient) {
        id.setText(Integer.toString(avoirDataClient.getId()));
        nomSociale.setText(avoirDataClient.getSociale());
        telephone.setText(avoirDataClient.getTelephone());
        cd.setText(avoirDataClient.getAddress().getCodePostal());
        email.setText(avoirDataClient.getAdresseMail());
        commentaire.setText(avoirDataClient.getCommentaries());
        numeroDeRue.setText(avoirDataClient.getAddress().getNumeroDeRue());
        NomDeRue.setText(avoirDataClient.getAddress().getNumeroDeRue());
        ville.setText(avoirDataClient.getAddress().getVille());
    }

    /**
     * remplie le data qui uniqe a client
     * remplie le data qui sur
     *
     * @param avoirDataClient
     */
    private void setDataClient(Client avoirDataClient) {
        chiffreDaffaire.setText(Double.toString(avoirDataClient.getLeChiffreDaffaire()));
        nombreDeEmployes.setText(Long.toString(avoirDataClient.getLeNombreDemployes()));
    }

    /**
     * remplie le data qui uniqe a Prospect
     *
     * @param avoirDataClient
     */

    private void setDataProspect(Prospect avoirDataClient) {
        date.setText(avoirDataClient.getLaDateDeProspection().format(formatter));
        interesseProspect.setSelectedItem(avoirDataClient.getInteresse());
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
     * on récupère la liste solon le choix de user
     *
     * @param flageClient
     * @return
     */
    private ArrayList getListPourEdit(Boolean flageClient) {
        if (flageClient) {
            return ListClient.getListClient();
        } else {
            return ListeProspect.getListProspect();
        }
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

    private void remplirTextFild(Boolean flageClient, int getIndexListe) {
        if (flageClient) {
            Client avoirDataClient = (Client) ListClient.getListClient()
                    .get(getIndexListe);
            setData(avoirDataClient);
            setDataClient(avoirDataClient);
        } else {
            Prospect avoirDataClient = (Prospect) ListeProspect.getListProspect()
                    .get(getIndexListe);
            setData(avoirDataClient);
            setDataProspect(avoirDataClient);
        }
        modeIInfo.setVisible(true);
        modeleAdress.setVisible(true);
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
