package Mokkivarausjärjestelma;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ToimipisteController implements Initializable {
    protected Connection m_conn;
    protected Toimipiste m_toimipiste = new Toimipiste();
    @FXML
    protected TextField toimipisteID;
    @FXML
    protected TextField nimi;
    @FXML
    protected TextField sijainti;
    @FXML
    public Button closeButton;

    public ToimipisteController() {
    }

    public void haeToimipiste() {
        this.m_toimipiste = null;

        try {
            this.m_toimipiste = Toimipiste.haetoimipiste(this.m_conn, Integer.parseInt(this.toimipisteID.getText()));
        } catch (SQLException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        this.nimi.setText(this.m_toimipiste.gettoimipistenimi());
        this.sijainti.setText(this.m_toimipiste.getsijainti());
    }

    public void lisaaToimipiste() {
        boolean palvelu_lisatty = true;
        this.m_toimipiste = null;

        Alert alert;
        try {
            this.m_toimipiste = Toimipiste.haetoimipiste(this.m_conn, Integer.parseInt(this.toimipisteID.getText()));
        } catch (SQLException var12) {
            palvelu_lisatty = false;
            var12.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Toimipisteen tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Toimipisteen tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        } catch (Exception var13) {
            palvelu_lisatty = false;
            var13.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Toimipisteen tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Toimipisteen tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        }

        Alert alert1;
        if (this.m_toimipiste.gettoimipistenimi() != null) {
            palvelu_lisatty = false;
            this.nimi.setText(this.m_toimipiste.gettoimipistenimi());
            this.sijainti.setText(this.m_toimipiste.getsijainti());
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Toimipisteen tietojen lisääminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakas on jo olemassa.");
            alert.showAndWait();
        } else {
            this.m_toimipiste.settoimipisteId(Integer.parseInt(this.toimipisteID.getText()));
            this.m_toimipiste.settoimipistenimi(this.nimi.getText());
            this.m_toimipiste.setsijainti(this.sijainti.getText());
            boolean var11 = false;

            label112: {
                label113: {
                    try {
                        var11 = true;
                        this.m_toimipiste.lisaatoimipiste(this.m_conn);
                        var11 = false;
                        break label112;
                    } catch (SQLException var14) {
                        palvelu_lisatty = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Toimipisteen tietojen lisääminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Toimipisteen tietojen lisääminen ei onnistu.");
                        alert.showAndWait();
                        var14.printStackTrace();
                        var11 = false;
                    } catch (Exception var15) {
                        palvelu_lisatty = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Toimipisteen tietojen lisääminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Toimipisteen tietojen lisääminen ei onnistu.");
                        alert.showAndWait();
                        var15.printStackTrace();
                        var11 = false;
                        break label113;
                    } finally {
                        if (var11) {
                            if (palvelu_lisatty) {
                                Alert alert2 = new Alert(AlertType.INFORMATION);
                                alert2.setTitle("Toimipisteen tietojen lisääminen");
                                alert2.setHeaderText("Toiminto OK.");
                                alert2.setContentText("Toimipisteen tiedot lisätty tietokantaan.");
                                alert2.showAndWait();
                            }

                        }
                    }

                    if (palvelu_lisatty) {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Toimipisteen tietojen lisääminen");
                        alert.setHeaderText("Toiminto OK.");
                        alert.setContentText("Toimipisteen tiedot lisätty tietokantaan.");
                        alert.showAndWait();
                    }

                    return;
                }

                if (palvelu_lisatty) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toimipisteen tietojen lisääminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Toimipisteen tiedot lisätty tietokantaan.");
                    alert.showAndWait();
                }

                return;
            }

            if (palvelu_lisatty) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Toimipisteen tietojen lisääminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Toimipisteen tiedot lisätty tietokantaan.");
                alert.showAndWait();
            }
        }

    }

    public void muuta_tiedotO() {
        boolean asiakas_muutettu = true;
        this.m_toimipiste.settoimipistenimi(this.nimi.getText());
        this.m_toimipiste.setsijainti(this.sijainti.getText());
        boolean var9 = false;

        Alert alert;
        label88: {
            label89: {
                try {
                    Alert alert3;
                    try {
                        var9 = true;
                        this.m_toimipiste.muutatoimipiste(this.m_conn);
                        var9 = false;
                        break label88;
                    } catch (SQLException var10) {
                        asiakas_muutettu = false;
                        alert3 = new Alert(AlertType.ERROR);
                        alert3.setTitle("Toimipisteen tietojen muuttaminen");
                        alert3.setHeaderText("Tietokantavirhe");
                        alert3.setContentText("Toimipisteen tietojen muuttaminen ei onnistu.");
                        alert3.showAndWait();
                        var10.printStackTrace();
                        var9 = false;
                    } catch (Exception var11) {
                        asiakas_muutettu = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Toimipisteen tietojen muuttaminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Toimipisteen tietojen muuttaminen ei onnistu.");
                        alert.showAndWait();
                        var11.printStackTrace();
                        var9 = false;
                        break label89;
                    }
                } finally {
                    if (var9) {
                        if (asiakas_muutettu) {
                            Alert alert4 = new Alert(AlertType.INFORMATION);
                            alert4.setTitle("Toimipisteen tietojen muuttaminen");
                            alert4.setHeaderText("Toiminto OK.");
                            alert4.setContentText("Toimipisteen tiedot muutettu tietokantaan.");
                            alert4.showAndWait();
                        }

                    }
                }

                if (asiakas_muutettu) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toimipisteen tietojen muuttaminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Toimipisteen tiedot muutettu tietokantaan.");
                    alert.showAndWait();
                }

                return;
            }

            if (asiakas_muutettu) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Toimipisteen tietojen muuttaminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Toimipisteen tiedot muutettu tietokantaan.");
                alert.showAndWait();
            }

            return;
        }

        if (asiakas_muutettu) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Toimipisteen tietojen muuttaminen");
            alert.setHeaderText("Toiminto OK.");
            alert.setContentText("Toimipisteen tiedot muutettu tietokantaan.");
            alert.showAndWait();
        }

    }

    public void poista_tiedotO() {
        this.m_toimipiste = null;
        boolean Asiakas_poistettu = false;

        Alert alert;
        try {
            this.m_toimipiste = Toimipiste.haetoimipiste(this.m_conn, Integer.parseInt(this.toimipisteID.getText()));
        } catch (SQLException var12) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Toimipisteen tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Toimipisteen tietojen poistaminen ei onnistu.");
            alert.showAndWait();
            var12.printStackTrace();
        } catch (Exception var13) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Toimipisteen tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Toimipisteen tietojen poistaminen ei onnistu.");
            alert.showAndWait();
            var13.printStackTrace();
        }

        Alert alert5;
        if (this.m_toimipiste.gettoimipistenimi() == null) {
            this.nimi.setText("");
            this.sijainti.setText("");
            alert5 = new Alert(AlertType.ERROR);
            alert5.setTitle("Toimipisteen tietojen poisto");
            alert5.setHeaderText("Virhe");
            alert5.setContentText("Toimipistettä ei löydy.");
            alert5.showAndWait();
        } else {
            this.nimi.setText(this.m_toimipiste.gettoimipistenimi());
            this.sijainti.setText(this.m_toimipiste.getsijainti());
            boolean var11 = false;

            label121: {
                label122: {
                    try {
                        var11 = true;
                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Toimipisteen tietojen poisto");
                        alert.setHeaderText("Vahvista");
                        alert.setContentText("Haluatko todella poistaa Toimipisteen?");
                        Optional<ButtonType> vastaus = alert.showAndWait();
                        if (vastaus.get() == ButtonType.OK) {
                            this.m_toimipiste.poistatoimipiste(this.m_conn);
                            Asiakas_poistettu = true;
                            var11 = false;
                        } else {
                            var11 = false;
                        }
                        break label121;
                    } catch (SQLException var14) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Toimipisteen tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Toimipisteen tietojen poistaminen ei onnistu.");
                        alert.showAndWait();
                        var14.printStackTrace();
                        var11 = false;
                    } catch (Exception var15) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Toimipisteen tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Toimipisteen tietojen poistaminen ei onnistu.");
                        alert.showAndWait();
                        var15.printStackTrace();
                        var11 = false;
                        break label122;
                    } finally {
                        if (var11) {
                            if (Asiakas_poistettu) {
                                this.toimipisteID.setText("");
                                this.nimi.setText("");
                                this.sijainti.setText("");
                                Alert alert6 = new Alert(AlertType.INFORMATION);
                                alert6.setTitle("Toimipisteen tietojen poisto");
                                alert6.setHeaderText("Results:");
                                alert6.setContentText("Toimipisteen tiedot poistettu tietokannasta.");
                                alert6.showAndWait();
                                this.m_toimipiste = null;
                            }

                        }
                    }

                    if (Asiakas_poistettu) {
                        this.toimipisteID.setText("");
                        this.nimi.setText("");
                        this.sijainti.setText("");
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Toimipisteen tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Toimipisteen tiedot poistettu tietokannasta.");
                        alert.showAndWait();
                        this.m_toimipiste = null;
                    }

                    return;
                }

                if (Asiakas_poistettu) {
                    this.toimipisteID.setText("");
                    this.nimi.setText("");
                    this.sijainti.setText("");
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toimipisteen tietojen poisto");
                    alert.setHeaderText("Results:");
                    alert.setContentText("Toimipisteen tiedot poistettu tietokannasta.");
                    alert.showAndWait();
                    this.m_toimipiste = null;
                }

                return;
            }

            if (Asiakas_poistettu) {
                this.toimipisteID.setText("");
                this.nimi.setText("");
                this.sijainti.setText("");
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Toimipisteen tietojen poisto");
                alert.setHeaderText("Results:");
                alert.setContentText("Toimipisteen tiedot poistettu tietokannasta.");
                alert.showAndWait();
                this.m_toimipiste = null;
            }

        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage)this.closeButton.getScene().getWindow();
        stage.close();

        try {
            this.sulje_kanta();
        } catch (SQLException var4) {
            System.out.println("Tapahtui tietokantavirhe tietokantaa suljettaessa.");
        } catch (Exception var5) {
            System.out.println("Tapahtui virhe tietokantaa suljettaessa.");
        }

    }

    public void sulje_kanta() throws SQLException, Exception {
        try {
            this.m_conn.close();
        } catch (SQLException var2) {
            throw var2;
        } catch (Exception var3) {
            throw var3;
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.yhdista();
        } catch (SQLException var4) {
            System.out.println("Tapahtui tietokantavirhe tietokantaa avattaessa.");
        } catch (Exception var5) {
            System.out.println("Tapahtui JDBCvirhe tietokantaa avattaessa.");
        }

    }

    public void yhdista() throws SQLException, Exception {
        this.m_conn = null;
        String url = "jdbc:mariadb://localhost:3306/ohjtu";

        try {
            // Tällä yhteys,keksi ja lisää itse salasanasi this.m_conn = DriverManager.getConnection(url, "", "");
        } catch (SQLException var3) {
            this.m_conn = null;
            throw var3;
        } catch (Exception var4) {
            throw var4;
        }
    }
}
