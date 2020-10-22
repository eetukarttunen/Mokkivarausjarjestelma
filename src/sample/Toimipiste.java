package sample;

/**
 * Eetu Karttunen
 * Toimipiste-luokka
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Toimipiste {
    protected int m_toimipiste_id;
    protected String m_toimipistenimi;
    protected String m_sijainti;

    public Toimipiste() {
    }

    public int gettoimipisteId() {
        return this.m_toimipiste_id;
    }

    public String gettoimipistenimi() {
        return this.m_toimipistenimi;
    }

    public String getsijainti() {
        return this.m_sijainti;
    }

    public void settoimipisteId(int id) {
        this.m_toimipiste_id = id;
    }

    public void settoimipistenimi(String ni) {
        this.m_toimipistenimi = ni;
    }

    public void setsijainti(String ni) {
        this.m_sijainti = ni;
    }

    public static Toimipiste haetoimipiste(Connection connection, int id) throws SQLException, Exception {
        String sql = "SELECT Toimipiste_ID, Toimipiste_Nimi, Sijainti FROM Toimipiste WHERE Toimipiste_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, id);
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("toimipistetta ei loydy");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        Toimipiste toimipisteOlio = new Toimipiste();

        try {
            if (tulosjoukko.next()) {
                toimipisteOlio.settoimipisteId(tulosjoukko.getInt("toimipiste_id"));
                toimipisteOlio.settoimipistenimi(tulosjoukko.getString("toimipiste_nimi"));
                toimipisteOlio.setsijainti(tulosjoukko.getString("sijainti"));
            }

            return toimipisteOlio;
        } catch (SQLException var7) {
            throw var7;
        }
    }

    public static Toimipiste haetoimipistenimi(Connection connection, String nimi) throws SQLException, Exception {
        String sql = "SELECT Toimipiste_ID, Toimipiste_Nimi, Sijainti FROM Toimipiste WHERE Toimipiste_Nimi = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setString(1, nimi);
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("toimipistetta ei loydy");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        Toimipiste toimipisteOlio = new Toimipiste();

        try {
            if (tulosjoukko.next()) {
                toimipisteOlio.settoimipisteId(tulosjoukko.getInt("toimipiste_id"));
                toimipisteOlio.settoimipistenimi(tulosjoukko.getString("toimipiste_nimi"));
                toimipisteOlio.setsijainti(tulosjoukko.getString("sijainti"));
            }

            return toimipisteOlio;
        } catch (SQLException var7) {
            throw var7;
        }
    }

    public int lisaatoimipiste(Connection connection) throws SQLException, Exception {
        String sql = "SELECT Toimipiste_ID FROM toimipiste WHERE Toimipiste_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.gettoimipisteId());
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next()) {
                throw new Exception("toimipiste on jo olemassa");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        sql = "INSERT INTO toimipiste (Toimipiste_ID, Toimipiste_nimi, Sijainti) VALUES (?, ?, ?)";
        lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.gettoimipisteId());
            lause.setString(2, this.gettoimipistenimi());
            lause.setString(3, this.getsijainti());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("toimipisteen lisaaminen ei onnistu");
            } else {
                return 0;
            }
        } catch (SQLException var6) {
            throw var6;
        } catch (Exception var7) {
            throw var7;
        }
    }

    public int muutatoimipiste(Connection connection) throws SQLException, Exception {
        String sql = "SELECT Toimipiste_ID FROM toimipiste WHERE Toimipiste_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.gettoimipisteId());
            tulosjoukko = lause.executeQuery();
            if (!tulosjoukko.next()) {
                throw new Exception("Toimipistettä ei loydy tietokannasta");
            }
        } catch (SQLException var8) {
            throw var8;
        } catch (Exception var9) {
            throw var9;
        }

        sql = "UPDATE toimipiste SET Toimipiste_Nimi = ?, Sijainti = ? WHERE Toimipiste_ID = ?";
        lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setString(1, this.gettoimipistenimi());
            lause.setString(2, this.getsijainti());
            lause.setInt(3, this.gettoimipisteId());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Toimipisteen muuttaminen ei onnistu");
            } else {
                return 0;
            }
        } catch (SQLException var6) {
            throw var6;
        } catch (Exception var7) {
            throw var7;
        }
    }

    public int poistatoimipiste(Connection connection) throws SQLException, Exception {
        String sql = "DELETE FROM toimipiste WHERE Toimipiste_ID = ?";
        PreparedStatement lause = null;

        try {
            lause = connection.prepareStatement(sql);
            lause.setInt(1, this.gettoimipisteId());
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("toimipisteen poistaminen ei onnistu");
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
