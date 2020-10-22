package Mokkivarausjärjestelma;

/**
 * Päävalikko- luokka
 * Tekijä Eetu Karttunen
 * github @EetuKarttunen
 * V.2020
 * */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Paavalikko extends Application {
    public Paavalikko() {
    }

    public void start(Stage alkuikkuna) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("PaavalikkoPopUp.fxml"));
        Scene kehys = new Scene(root);
        alkuikkuna.initStyle(StageStyle.UNDECORATED);

        alkuikkuna.setTitle("Mokkivarausjärjestelma");
        alkuikkuna.setScene(kehys);
        alkuikkuna.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
