package sample;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Asiakas {
    protected int m_palvelut_id;
    protected String m_etunimi;
    protected String m_sukunimi;
    protected String m_osoite;

    public Asiakas() {
    }

    public int getAsiakasId() {
        return this.m_palvelut_id;
    }

    public String getEtunimi() {
        return this.m_etunimi;
    }

    public String getSukunimi() {
        return this.m_sukunimi;
    }

    public String getOsoite() {
        return this.m_osoite;
    }

    public void setAsiakasId(int id) {
        this.m_palvelut_id = id;
    }

    public void setEtunimi(String ni) {
        this.m_etunimi = ni;
    }

    public void setSukunimi(String ni) {
        this.m_sukunimi = ni;
    }

    public void setOsoite(String os) {
        this.m_osoite = os;
    }

    public static Asiakas haeAsiakas(Connection connection, int id) throws SQLException, Exception {
        String sql = "SELECT Asiakas_ID, Etunimi, Sukunimi, Osoite  FROM Asiakkaat WHERE Asiakas_id = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(49, id);
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("Asiakasta ei loydy");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        Asiakas AsiakasOlio = new Asiakas();

        try {
            if (tulosjoukko.next()) {
                AsiakasOlio.setAsiakasId(tulosjoukko.getInt("Asiakas_id"));
                AsiakasOlio.setEtunimi(tulosjoukko.getString("Etunimi"));
                AsiakasOlio.setSukunimi(tulosjoukko.getString("Sukunimi"));
                AsiakasOlio.setOsoite(tulosjoukko.getString("Osoite"));
            }

            return AsiakasOlio;
        } catch (SQLException var7) {
            throw var7;
        }
    }

    public int lisaaAsiakas(Connection connection) throws SQLException, Exception {
        String sql = "SELECT Asiakas_ID FROM Asiakkaat WHERE Asiakas_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.getAsiakasId());
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next()) {
                throw new Exception("Asiakas on jo olemassa");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        sql = "INSERT INTO Asiakkaat (Asiakas_ID, Etunimi, Sukunimi, Osoite)  VALUES (?, ?, ?, ?)";
        lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.getAsiakasId());
            lause.setString(50, this.getEtunimi());
            lause.setString(51, this.getSukunimi());
            lause.setString(52, this.getOsoite());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Asiakaan lisaaminen ei onnistu");
            } else {
                return 0;
            }
        } catch (SQLException var6) {
            throw var6;
        } catch (Exception var7) {
            throw var7;
        }
    }

    public int muutaAsiakas(Connection connection) throws SQLException, Exception {
        String sql = "SELECT Asiakas_ID FROM Asiakkaat WHERE Asiakas_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.getAsiakasId());
            tulosjoukko = lause.executeQuery();
            if (!tulosjoukko.next()) {
                throw new Exception("Asiakasta ei loydy tietokannasta");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        sql = "UPDATE Asiakkaat SET Etunimi = ?, Sukunimi = ?, Osoite = ?  WHERE Asiakas_ID = ?";
        lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setString(1, this.getEtunimi());
            lause.setString(2, this.getSukunimi());
            lause.setString(3, this.getOsoite());
            lause.setInt(4, this.getAsiakasId());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Asiakkaan muuttaminen ei onnistu");
            } else {
                return 0;
            }
        } catch (SQLException var6) {
            throw var6;
        } catch (Exception var7) {
            throw var7;
        }
    }

    public int poistaAsiakas(Connection connection) throws SQLException, Exception {
        String sql = "DELETE FROM Asiakkaat WHERE Asiakas_ID = ?";
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.getAsiakasId());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Asiakkaan poistaminen ei onnistu");
            } else {
                return 0;
            }
        } catch (SQLException var5) {
            throw var5;
        } catch (Exception var6) {
            throw var6;
        }
    }
}
