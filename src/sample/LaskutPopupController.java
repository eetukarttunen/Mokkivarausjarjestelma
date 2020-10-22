package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class LaskutPopupController implements Initializable {
    protected Connection m_conn;
    protected Laskut m_laskut = new Laskut();


    @FXML
    protected TextField laskuID;

    @FXML
    protected TextField erapaiva;

    @FXML
    protected TextField asiakasID;

    @FXML
    protected TextField summa;






    @FXML
    public Button closeButton;

    public void tulosta() {
        // haetaan tietokannasta Asiakasa, jonka Asiakas_id = txtAsiakasID
        m_laskut = null;

        try {
            m_laskut = Laskut.haeLasku(m_conn, Integer.parseInt(laskuID.getText()));
        } catch (SQLException se) {
            // SQL virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

        } catch (Exception e) {
            // muut virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

        }

        try {
            File tiedosto = new File("Lasku" + m_laskut.getLaskuID() + ".txt");
            if (tiedosto.createNewFile()) {
                System.out.println("File created: " + tiedosto.getName());

                try {
                    FileWriter myWriter = new FileWriter("Lasku" + m_laskut.getLaskuID() + ".txt");
                    myWriter.write(m_laskut.toString());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }


            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void haeLaskut() {
        // haetaan tietokannasta Asiakasa, jonka Asiakas_id = txtAsiakasID
        m_laskut = null;


        /*
        palveluID.setText("");
        nimi.setText("");
        hinta.setText("");
         */


        try {
            m_laskut = Laskut.haeLasku(m_conn, Integer.parseInt(laskuID.getText()));
        } catch (SQLException se) {
            // SQL virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

        } catch (Exception e) {
            // muut virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

        }
        if (m_laskut.getLaskuID() == 0) {
            // muut virheet
            erapaiva.setText("");
            asiakasID.setText("");
            summa.setText("");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

        }

        else {
            // naytetaan tiedot

            erapaiva.setText(m_laskut.getErapaiva());
            asiakasID.setText(Integer.toString(m_laskut.getAsiakasID()));
            summa.setText(Integer.toString(m_laskut.getSumma()));


        }
    }

    public  void lisaaLaskut() {
        boolean lasku_lisatty = true;
        m_laskut = null;
        try {
            m_laskut = Laskut.haeLasku (m_conn, Integer.parseInt(laskuID.getText()));
        } catch (SQLException se) {
            // SQL virheet
            lasku_lisatty = false;
            se.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen lisääminen ei onnistu.");
            alert.showAndWait();

        } catch (Exception e) {
            // muut virheet
            lasku_lisatty = false;
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        }
        if (m_laskut.getErapaiva() != null) {
            // Asiakas jo olemassa, näytetään tiedot
            lasku_lisatty = false;
            erapaiva.setText(m_laskut.getErapaiva());
            asiakasID.setText(Integer.toString(m_laskut.getAsiakasID()));
            summa.setText(Integer.toString(m_laskut.getSumma()));



            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen lisääminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelu on jo olemassa.");
            alert.showAndWait();

        }
        else
        {
            // asetetaan tiedot oliolle
            m_laskut.setLaskuID(Integer.parseInt(laskuID.getText()));
            m_laskut.setErapaiva(erapaiva.getText());
            m_laskut.setAsiakasID(Integer.parseInt(asiakasID.getText()));
            m_laskut.setSumma(Integer.parseInt(summa.getText()));


            try {
                // yritetään kirjoittaa kantaan
                m_laskut.lisaaLasku (m_conn);
            } catch (SQLException se) {
                // SQL virheet
                lasku_lisatty = false;

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Palvelun tietojen lisääminen");
                alert.setHeaderText("Tietokantavirhe");
                alert.setContentText("Palvelun tietojen lisääminen ei onnistu.");
                alert.showAndWait();

                se.printStackTrace();
            } catch (Exception e) {
                // muut virheet
                lasku_lisatty = false;

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Palvelun tietojen lisääminen");
                alert.setHeaderText("Tietokantavirhe");
                alert.setContentText("Palvelun tietojen lisääminen ei onnistu.");
                alert.showAndWait();

                e.printStackTrace();
            }finally {
                if (lasku_lisatty == true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Palvelun tietojen lisääminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Palvelun tiedot lisätty tietokantaan.");
                    alert.showAndWait();

                }
            }

        }

    }

    public void muutaLaskut() {
        boolean lasku_muutettu = true;
        // asetetaan tiedot oliolle
        m_laskut.setErapaiva(erapaiva.getText());
        m_laskut.setAsiakasID(Integer.parseInt(asiakasID.getText()));
        m_laskut.setSumma(Integer.parseInt(summa.getText()));



        try {
            // yritetään muuttaa (UPDATE) tiedot kantaan
            m_laskut.muutaLasku (m_conn);
        } catch (SQLException se) {
            // SQL virheet
            lasku_muutettu = false;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen muuttaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen muuttaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet
            lasku_muutettu = false;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen muuttaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen muuttaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        } finally {
            if (lasku_muutettu == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Palvelun tietojen muuttaminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Palvelun tiedot muutettu tietokantaan.");
                alert.showAndWait();

            }
        }

    }

    public void poistaLaskut() {
        // haetaan tietokannasta Asiakasa, jonka Asiakas_id = txtAsiakasID
        m_laskut = null;
        boolean lasku_poistettu = false;

        try {
            m_laskut = Laskut.haeLasku (m_conn, Integer.parseInt(laskuID.getText()));
        } catch (SQLException se) {
            // SQL virheet

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Palvelun tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        }
        if (m_laskut.getErapaiva() == null) {
            // poistettavaa Asiakasa ei lÃ¶ydy tietokannasta, tyhjennetÃ¤Ã¤n tiedot nÃ¤ytÃ¶ltÃ¤
            asiakasID.setText("");
            summa.setText("");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen poisto");
            alert.setHeaderText("Virhe");
            alert.setContentText("Palvelua ei löydy.");
            alert.showAndWait();

            return; // poistutaan
        }
        else
        {
            // näytetään poistettavan asiakkaan tiedot
            erapaiva.setText(m_laskut.getErapaiva());
            asiakasID.setText(Integer.toString(m_laskut.getAsiakasID()));
            summa.setText(Integer.toString(m_laskut.getSumma()));

        }
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Palvelun tietojen poisto");
            alert.setHeaderText("Vahvista");
            alert.setContentText("Haluatko todella poistaa Palvelun?");

            Optional<ButtonType> vastaus = alert.showAndWait();

            if (vastaus.get() == ButtonType.OK) {
                m_laskut.poistaLasku (m_conn);
                lasku_poistettu = true;
            }
        } catch (SQLException se) {
            // SQL virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen poisto");
            alert.setHeaderText("Results:");
            alert.setContentText("Palvelun tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            se.printStackTrace();
        } catch (Exception e) {
            // muut virheet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Palvelun tietojen poisto");
            alert.setHeaderText("Results:");
            alert.setContentText("Palvelun tietojen poistaminen ei onnistu.");
            alert.showAndWait();

            e.printStackTrace();
        } finally {
            if (lasku_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui
                laskuID.setText("");
                erapaiva.setText("");
                asiakasID.setText("");
                summa.setText("");


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Palvelun tietojen poisto");
                alert.setHeaderText("Results:");
                alert.setContentText("Palvelun tiedot poistettu tietokannasta.");
                alert.showAndWait();

                m_laskut = null;
            }
        }

    }

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

    }

}


