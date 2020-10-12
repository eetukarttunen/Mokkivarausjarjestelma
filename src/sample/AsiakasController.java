package sample;

/**
 * Asiakas Controller- luokka
 * Tekijä Eetu Karttunen
 * github @EetuKarttunen
 * V.2020
 * */

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

public class AsiakasController implements Initializable {
    protected Connection m_conn;
    protected Asiakas m_asiakas = new Asiakas();
    @FXML
    protected TextField asiakasID;
    @FXML
    protected TextField etunimi;
    @FXML
    protected TextField sukunimi;
    @FXML
    protected TextField osoite;
    @FXML
    public Button closeButton;

    public AsiakasController() {
    }

    public void hae_tiedotO() {
        this.m_asiakas = null;

        Alert alert;
        try {
            this.m_asiakas = Asiakas.haeAsiakas(this.m_conn, Integer.parseInt(this.asiakasID.getText()));
        } catch (SQLException var3) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();
        } catch (Exception var4) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen hakeminen");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();
        }

        if (this.m_asiakas.getEtunimi() == null) {
            this.etunimi.setText("");
            this.sukunimi.setText("");
            this.osoite.setText("");
            Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Asiakkaan tietojen hakeminen");
            alert1.setHeaderText("Virhe");
            alert1.setContentText("Asiakasta ei löydy.");
            alert1.showAndWait();
        } else {
            this.etunimi.setText(this.m_asiakas.getEtunimi());
            this.sukunimi.setText(this.m_asiakas.getSukunimi());
            this.osoite.setText(this.m_asiakas.getOsoite());
        }

    }

    public void lisaa_tiedotO() {
        boolean palvelu_lisatty = true;
        this.m_asiakas = null;

        Alert alert;
        try {
            this.m_asiakas = Asiakas.haeAsiakas(this.m_conn, Integer.parseInt(this.asiakasID.getText()));
        } catch (SQLException var12) {
            palvelu_lisatty = false;
            var12.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        } catch (Exception var13) {
            palvelu_lisatty = false;
            var13.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen lisääminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
            alert.showAndWait();
        }

        Alert alert2;
        if (this.m_asiakas.getEtunimi() != null) {
            palvelu_lisatty = false;
            this.etunimi.setText(this.m_asiakas.getEtunimi());
            this.sukunimi.setText(this.m_asiakas.getSukunimi());
            this.osoite.setText(this.m_asiakas.getOsoite());
            alert2 = new Alert(AlertType.ERROR);
            alert2.setTitle("Asiakkaan tietojen lisääminen");
            alert2.setHeaderText("Virhe");
            alert2.setContentText("Asiakas on jo olemassa.");
            alert2.showAndWait();
        } else {
            this.m_asiakas.setAsiakasId(Integer.parseInt(this.asiakasID.getText()));
            this.m_asiakas.setEtunimi(this.etunimi.getText());
            this.m_asiakas.setSukunimi(this.sukunimi.getText());
            this.m_asiakas.setOsoite(this.osoite.getText());
            boolean var11 = false;

            label112: {
                label113: {
                    try {
                        var11 = true;
                        this.m_asiakas.lisaaAsiakas(this.m_conn);
                        var11 = false;
                        break label112;
                    } catch (SQLException var14) {
                        palvelu_lisatty = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen lisääminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Asiakas tietojen lisääminen ei onnistu.");
                        alert.showAndWait();
                        var14.printStackTrace();
                        var11 = false;
                    } catch (Exception var15) {
                        palvelu_lisatty = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen lisääminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Asiakkaan tietojen lisääminen ei onnistu.");
                        alert.showAndWait();
                        var15.printStackTrace();
                        var11 = false;
                        break label113;
                    } finally {
                        if (var11) {
                            if (palvelu_lisatty) {
                                Alert alert3 = new Alert(AlertType.INFORMATION);
                                alert3.setTitle("Asiakkaan tietojen lisääminen");
                                alert3.setHeaderText("Toiminto OK.");
                                alert3.setContentText("Asiakkaan tiedot lisätty tietokantaan.");
                                alert3.showAndWait();
                            }

                        }
                    }

                    if (palvelu_lisatty) {
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Asiakkaan tietojen lisääminen");
                        alert.setHeaderText("Toiminto OK.");
                        alert.setContentText("Asiakkaan tiedot lisätty tietokantaan.");
                        alert.showAndWait();
                    }

                    return;
                }

                if (palvelu_lisatty) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Asiakkaan tietojen lisääminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Asiakkaan tiedot lisätty tietokantaan.");
                    alert.showAndWait();
                }

                return;
            }

            if (palvelu_lisatty) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Asiakkaan tietojen lisääminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Asiakkaan tiedot lisätty tietokantaan.");
                alert.showAndWait();
            }
        }

    }

    public void muuta_tiedotO() {
        boolean asiakas_muutettu = true;
        this.m_asiakas.setEtunimi(this.etunimi.getText());
        this.m_asiakas.setSukunimi(this.sukunimi.getText());
        this.m_asiakas.setOsoite(this.osoite.getText());
        boolean var9 = false;

        Alert alert;
        label88: {
            label89: {
                try {
                    Alert alert4;
                    try {
                        var9 = true;
                        this.m_asiakas.muutaAsiakas(this.m_conn);
                        var9 = false;
                        break label88;
                    } catch (SQLException var10) {
                        asiakas_muutettu = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen muuttaminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Asiakkaan tietojen muuttaminen ei onnistu.");
                        alert.showAndWait();
                        var10.printStackTrace();
                        var9 = false;
                    } catch (Exception var11) {
                        asiakas_muutettu = false;
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen muuttaminen");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Asiakkaan tietojen muuttaminen ei onnistu.");
                        alert.showAndWait();
                        var11.printStackTrace();
                        var9 = false;
                        break label89;
                    }
                } finally {
                    if (var9) {
                        if (asiakas_muutettu) {
                            Alert alert5 = new Alert(AlertType.INFORMATION);
                            alert5.setTitle("Asiakkaan tietojen muuttaminen");
                            alert5.setHeaderText("Toiminto OK.");
                            alert5.setContentText("Asiakkaan tiedot muutettu tietokantaan.");
                            alert5.showAndWait();
                        }

                    }
                }

                if (asiakas_muutettu) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Asiakkaan tietojen muuttaminen");
                    alert.setHeaderText("Toiminto OK.");
                    alert.setContentText("Asiakkaan tiedot muutettu tietokantaan.");
                    alert.showAndWait();
                }

                return;
            }

            if (asiakas_muutettu) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Asiakkaan tietojen muuttaminen");
                alert.setHeaderText("Toiminto OK.");
                alert.setContentText("Asiakkaan tiedot muutettu tietokantaan.");
                alert.showAndWait();
            }

            return;
        }

        if (asiakas_muutettu) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Asiakkaan tietojen muuttaminen");
            alert.setHeaderText("Toiminto OK.");
            alert.setContentText("Asiakkaan tiedot muutettu tietokantaan.");
            alert.showAndWait();
        }

    }

    public void poista_tiedotO() {
        this.m_asiakas = null;
        boolean Asiakas_poistettu = false;

        Alert alert;
        try {
            this.m_asiakas = Asiakas.haeAsiakas(this.m_conn, Integer.parseInt(this.asiakasID.getText()));
        } catch (SQLException var12) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();
            var12.printStackTrace();
        } catch (Exception var13) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poistaminen");
            alert.setHeaderText("Tietokantavirhe");
            alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
            alert.showAndWait();
            var13.printStackTrace();
        }

        Alert alert1;
        if (this.m_asiakas.getEtunimi() == null) {
            this.etunimi.setText("");
            this.sukunimi.setText("");
            this.osoite.setText("");
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Asiakkaan tietojen poisto");
            alert.setHeaderText("Virhe");
            alert.setContentText("Asiakasta ei löydy.");
            alert.showAndWait();
        } else {
            this.etunimi.setText(this.m_asiakas.getEtunimi());
            this.sukunimi.setText(this.m_asiakas.getSukunimi());
            this.osoite.setText(this.m_asiakas.getOsoite());
            boolean var11 = false;

            label121: {
                label122: {
                    try {
                        var11 = true;
                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Asiakkaan tietojen poisto");
                        alert.setHeaderText("Vahvista");
                        alert.setContentText("Haluatko todella poistaa Asiakkaan?");
                        Optional<ButtonType> vastaus = alert.showAndWait();
                        if (vastaus.get() == ButtonType.OK) {
                            this.m_asiakas.poistaAsiakas(this.m_conn);
                            Asiakas_poistettu = true;
                            var11 = false;
                        } else {
                            var11 = false;
                        }
                        break label121;
                    } catch (SQLException var14) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
                        alert.showAndWait();
                        var14.printStackTrace();
                        var11 = false;
                    } catch (Exception var15) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Asiakkaan tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Asiakkaan tietojen poistaminen ei onnistu.");
                        alert.showAndWait();
                        var15.printStackTrace();
                        var11 = false;
                        break label122;
                    } finally {
                        if (var11) {
                            if (Asiakas_poistettu) {
                                this.asiakasID.setText("");
                                this.etunimi.setText("");
                                this.sukunimi.setText("");
                                this.osoite.setText("");
                                Alert alert6 = new Alert(AlertType.INFORMATION);
                                alert6.setTitle("Asiakkaan tietojen poisto");
                                alert6.setHeaderText("Results:");
                                alert6.setContentText("Asiakkaan tiedot poistettu tietokannasta.");
                                alert6.showAndWait();
                                this.m_asiakas = null;
                            }

                        }
                    }

                    if (Asiakas_poistettu) {
                        this.asiakasID.setText("");
                        this.etunimi.setText("");
                        this.sukunimi.setText("");
                        this.osoite.setText("");
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Asiakkaan tietojen poisto");
                        alert.setHeaderText("Results:");
                        alert.setContentText("Asiakkaan tiedot poistettu tietokannasta.");
                        alert.showAndWait();
                        this.m_asiakas = null;
                    }

                    return;
                }

                if (Asiakas_poistettu) {
                    this.asiakasID.setText("");
                    this.etunimi.setText("");
                    this.sukunimi.setText("");
                    this.osoite.setText("");
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Asiakkaan tietojen poisto");
                    alert.setHeaderText("Results:");
                    alert.setContentText("Asiakkaan tiedot poistettu tietokannasta.");
                    alert.showAndWait();
                    this.m_asiakas = null;
                }

                return;
            }

            if (Asiakas_poistettu) {
                this.asiakasID.setText("");
                this.etunimi.setText("");
                this.sukunimi.setText("");
                this.osoite.setText("");
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Asiakkaan tietojen poisto");
                alert.setHeaderText("Results:");
                alert.setContentText("Asiakkaan tiedot poistettu tietokannasta.");
                alert.showAndWait();
                this.m_asiakas = null;
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
            this.m_conn = DriverManager.getConnection(url, "root", "eetu");
        } catch (SQLException var3) {
            this.m_conn = null;
            throw var3;
        } catch (Exception var4) {
            throw var4;
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
}
