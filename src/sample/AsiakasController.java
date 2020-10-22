package sample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author eetuk
 */
public class AsiakasController implements Initializable {
    protected Connection m_conn;
    protected Asiakas m_asiakas  = new Asiakas ();

    @FXML
    protected TextField asiakasID;

    @FXML
    protected TextField etunimi;

    @FXML
    protected TextField sukunimi;

    @FXML
    protected TextField osoite;

    public void hae_tiedotO() {
        // haetaan tietokannasta Asiakasa, jonka Asiakas_id = txtAsiakasID
        m_asiakas = null;
        //ArrayList <Suoritus> lstSuoritukset = null;

        try {
            m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(asiakasID.getText()));
        } catch (SQLException se) {
            // SQL virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();

        } catch (Exception e) {
            // muut virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();

        }
        if (m_asiakas.getEtunimi() == null) {
            // muut virheet
            etunimi.setText("");
            sukunimi.setText("");
            osoite.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();

        }
        else
        {
            // naytetaan tiedot
            etunimi.setText(m_asiakas.getEtunimi());
            sukunimi.setText(m_asiakas.getSukunimi());
            osoite.setText(m_asiakas.getOsoite());

        }

    }


    public  void lisaa_tiedotO() {
        boolean palvelu_lisatty = true;
        m_asiakas = null;
        try {
            m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(asiakasID.getText()));
        } catch (SQLException se) {
            // SQL virheet
            palvelu_lisatty = false;
            se.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
            alert.showAndWait();

        } catch (Exception e) {
            // muut virheet
            palvelu_lisatty = false;
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        }
        if (m_asiakas.getEtunimi() != null) {
            // Asiakas jo olemassa, näytetään tiedot
            palvelu_lisatty = false;
            etunimi.setText(m_asiakas.getEtunimi());
            sukunimi.setText(m_asiakas.getSukunimi());
            osoite.setText(m_asiakas.getOsoite());


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen lisääminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakas on jo olemassa.");
            alert.showAndWait();

        }
        else
        {
            // asetetaan tiedot oliolle
            m_asiakas.setAsiakasId(Integer.parseInt(asiakasID.getText()));
            m_asiakas.setEtunimi(etunimi.getText());
            m_asiakas.setSukunimi(sukunimi.getText());
            m_asiakas.setOsoite(osoite.getText());

            try {
                // yritetään kirjoittaa kantaan
                m_asiakas.lisaaAsiakas (m_conn);
            } catch (SQLException se) {
                // SQL virheet
                palvelu_lisatty = false;

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Asiakkaan tietojen lisääminen");
                alert.setHeaderText("Tietokantavirhe");
                alert.setContentText("Asiakas tietojen lisääminen ei onnistu.");
                alert.showAndWait();

                se.printStackTrace();
            } catch (Exception e) {
                // muut virheet
                palvelu_lisatty = false;

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Asiakkaan tietojen lisääminen");
                alert.setHeaderText("Tietokantavirhe");
                alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
                alert.showAndWait();

                e.printStackTrace();
            }finally {
                if (palvelu_lisatty == true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Asiakkaan tietojen lisääminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Asiakkaan tiedot lisätty tietokantaan.");
                    alert.showAndWait();

                }
            }

        }

    }

    public void muuta_tiedotO() {
        boolean asiakas_muutettu = true;
        // asetetaan tiedot oliolle
        m_asiakas.setEtunimi(etunimi.getText());
        m_asiakas.setSukunimi(sukunimi.getText());
        m_asiakas.setOsoite(osoite.getText());


        try {
            // yritetään muuttaa (UPDATE) tiedot kantaan
            m_asiakas.muutaAsiakas (m_conn);
        } catch (SQLException se) {
            // SQL virheet
            asiakas_muutettu = false;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen muuttaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen muuttaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet
            asiakas_muutettu = false;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen muuttaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen muuttaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        } finally {
            if (asiakas_muutettu == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Asiakkaan tietojen muuttaminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Asiakkaan tiedot muutettu tietokantaan.");
                alert.showAndWait();

            }
        }

    }

    public void poista_tiedotO() {
        // haetaan tietokannasta Asiakasa, jonka Asiakas_id = txtAsiakasID
        m_asiakas = null;
        boolean Asiakas_poistettu = false;

        try {
            m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(asiakasID.getText()));
        } catch (SQLException se) {
            // SQL virheet

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        }
        if (m_asiakas.getEtunimi() == null) {
            // poistettavaa Asiakasta ei lÃ¶ydy tietokannasta, tyhjennetÃ¤Ã¤n tiedot nÃ¤ytÃ¶ltÃ¤
            etunimi.setText("");
            sukunimi.setText("");
            osoite.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poisto");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();

            return; // poistutaan
        }
        else
        {
            // näytetään poistettavan asiakkaan tiedot
            etunimi.setText(m_asiakas.getEtunimi());
            sukunimi.setText(m_asiakas.getSukunimi());
            osoite.setText(m_asiakas.getOsoite());
        }
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Asiakkaan tietojen poisto");
            alert.setHeaderText("Vahvista");
            alert.setContentText("Haluatko todella poistaa Asiakkaan?");

            Optional<ButtonType> vastaus = alert.showAndWait();

            if (vastaus.get() == ButtonType.OK) {
                m_asiakas.poistaAsiakas (m_conn);
                Asiakas_poistettu = true;
            }
        } catch (SQLException se) {
            // SQL virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poisto");
            alert.setHeaderText("Results:");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poisto");
            alert.setHeaderText("Results:");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        } finally {
            if (Asiakas_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
                asiakasID.setText("");
                etunimi.setText("");
                sukunimi.setText("");
                osoite.setText("");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Asiakkaan tietojen poisto");
                alert.setHeaderText("Results:");
                alert.setContentText("Asiakkaan tiedot poistettu tietokannasta.");
                alert.showAndWait();

                m_asiakas = null;
            }
        }


    }


    @FXML
    public Button closeButton;
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        try {
            sulje_kanta();
        } catch (SQLException se) {
            // SQL virheet
            System.out.println("Tapahtui tietokantavirhe tietokantaa suljettaessa.");
            //	JOptionPane.showMessageDialog(null, "Tapahtui tietokantavirhe tietokantaa suljettaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // muut virheet
            System.out.println("Tapahtui virhe tietokantaa suljettaessa.");
            //	JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa suljettaessa.", "Virhe", JOptionPane.ERROR_MESSAGE);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            yhdista();
        } catch (SQLException se) {
            // SQL virheet
            System.out.println("Tapahtui tietokantavirhe tietokantaa avattaessa.");
            //JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // JDBC virheet
            System.out.println("Tapahtui JDBCvirhe tietokantaa avattaessa.");
            //JOptionPane.showMessageDialog(null, "Tapahtui JDBCvirhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void yhdista() throws SQLException, Exception {
        m_conn = null;
        String url = "jdbc:mariadb://localhost:3306/ohjtu"; // palvelin = localhost, :portti annettu asennettaessa, tietokannan nimi
        try {
            // yhteys kantaan, kayttaja = root, salasana = ohjelmointi
            m_conn= DriverManager.getConnection(url,"root", "eetu");
        }
        catch (SQLException e) { // tietokantaan ei saada yhteyttä
            m_conn = null;
            throw e;
        }
        catch (Exception e ) { // JDBC ajuria ei löydy
            throw e;
        }



    }

    public void sulje_kanta() throws SQLException, Exception {
        // suljetaan
        try {
            // sulje yhteys kantaan
            m_conn.close ();
        }
        catch (SQLException e) { // tietokantavirhe
            throw e;
        }
        catch (Exception e ) { // muu virhe tapahtui
            throw e;
        }

    }

}