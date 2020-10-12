package sample;

/**
 * Päävalikon Controller- luokka
 * Tekijä Eetu Karttunen
 * github @EetuKarttunen
 * V.2020
 *
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PaavalikkoController implements Initializable {
    @FXML
    protected Button naytaToimipisteet;
    @FXML
    protected Button naytaPalvelut;
    @FXML
    protected Button naytaMajoitukset;
    @FXML
    protected Button naytaAsiakkaat;
    @FXML
    protected Button naytaLaskut;

    public PaavalikkoController() {
    }

    @FXML
    public void naytaToimipisteet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("ToimipistePopuptest.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Toimipisteiden hallinta");
        stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void naytaPalvelut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("PalvelutPopup.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Palveluiden hallinta");
        stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void naytaMajoitukset(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("MajoitusPopup.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Majoitusvarausten hallinta");
        stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void naytaAsiakkaat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("AsiakasPopUp.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Asiakashallintajärjestelmä");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void naytaLaskut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("LaskutPopup.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Laskujen hallinta ja seuranta");
        stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void initialize(URL url, ResourceBundle rb) {
    }
}
