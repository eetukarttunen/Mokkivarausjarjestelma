package Mokkivarausjärjestelma;

import java.sql.*;
import java.lang.*;

public class Laskut {

    @Override
    public String toString() {
        return "Lasku yritykseltä Eetun mökkivarausjärjestelmä Oy \n" +
                "LaskuID = " + m_lasku_id + "\n" +
                "Eräpäivä = " + m_erapaiva + "\n" +
                "AsiakasID = " + m_asiakas_id + "\n" +
                "Summa = " + m_summa + "€"
                ;
    }

    /**
     * lasku_id
     *
     */
    protected int m_lasku_id;

    /**
     * eräpäivä
     */
    protected String m_erapaiva;

    public int getLaskuID() {
        return m_lasku_id;
    }

    public void setLaskuID(int m_lasku_id) {
        this.m_lasku_id = m_lasku_id;
    }

    public String getErapaiva() {
        return m_erapaiva;
    }

    public void setErapaiva(String m_erapaiva) {
        this.m_erapaiva = m_erapaiva;
    }

    public int getAsiakasID() {
        return m_asiakas_id;
    }

    public void setAsiakasID(int m_asiakas_id) {
        this.m_asiakas_id = m_asiakas_id;
    }

    public int getSumma() {
        return m_summa;
    }

    public void setSumma(int m_summa) {
        this.m_summa = m_summa;
    }

    /**
     * Asiakas_id
     */
    protected int m_asiakas_id;

    /**
     * summa
     */
    protected int m_summa;



    /**
     * Konstruktori
     */
    public Laskut(){

    }





    /**
     * @param connection
     * @param id
     * @return Asiakaslio
     * @throws SQLException
     * @throws Exception
     * Haetaan Laskun tiedot tietokannasta ja palautetaan Laskuolio kutsujalle.
     */
    public static Laskut haeLasku (Connection connection, int id) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
        // haetaan tietokannasta laskua, jonka Lasku_id = id
        String sql = "SELECT Lasku_ID, Erapaiva, Asiakas_ID, Summa "
                + " FROM Laskut WHERE Lasku_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
            lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
            // suorita sql-lause
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko == null) {
                throw new Exception("Laskua ei loydy");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC virheet
            throw e;
        }
        // käsitellään resultset - laitetaan tiedot Asiakasoliolle
        Laskut LaskuOlio = new Laskut ();

        try {
            if (tulosjoukko.next () == true){
                //Lasku_id, erapaiva, asiakas_id, summa
                LaskuOlio.setLaskuID (tulosjoukko.getInt("Lasku_ID"));
                LaskuOlio.setErapaiva (tulosjoukko.getString("Erapaiva"));
                LaskuOlio.setAsiakasID(tulosjoukko.getInt("Asiakas_ID"));
                LaskuOlio.setSumma (tulosjoukko.getInt("Summa"));

            }

        }catch (SQLException e) {
            throw e;
        }
        // palautetaan Asiakasolio

        return LaskuOlio;
    }


    /**
     * @param connection
     * @return 0
     * @throws SQLException
     * @throws Exception
     * Lisätään Laskun tiedot tietokantaan.
     */



    public int lisaaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
        // haetaan tietokannasta Laskua, jonka Lasku_id = olion id -> ei voi lisätä, jos on jo kannassa
        String sql = "SELECT Lasku_ID"
                + " FROM Laskut WHERE Lasku_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;

        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
            lause.setInt( 1, getLaskuID()); // asetetaan where ehtoon (?) arvo, olion Asiakasid
            // suorita sql-lause
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next () == true) { // Asiakas loytyi
                throw new Exception("Asiakas on jo olemassa");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC virheet
            throw e;
        }
        // parsitaan INSERT


        sql = "INSERT INTO Laskut "
                + "(Lasku_ID, Erapaiva, Asiakas_ID, Summa) "
                + " VALUES (?, ?, ?, ?)";
        // System.out.println("Lisataan " + sql);
        lause = null;
        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
            // laitetaan arvot INSERTtiin
            lause.setInt( 1, getLaskuID());
            lause.setString(2, getErapaiva());
            lause.setInt(3, getAsiakasID());
            lause.setInt(4, getSumma());

            // suorita sql-lause
            int lkm = lause.executeUpdate();
            //	System.out.println("lkm " + lkm);
            if (lkm == 0) {
                throw new Exception("Laskun lisaaminen ei onnistu");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC ym. virheet
            throw e;
        }
        return 0;
    }



    /**
     * @param connection
     * @return 0
     * @throws SQLException
     * @throws Exception
     * Muutetaan Laskun tiedot tietokantaan id-tietoa (avain) lukuunottamatta.
     */


    public int muutaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina
        // haetaan tietokannasta Laskua, jonka Lasku_id = olion id, virhe, jos ei löydy
        String sql = "SELECT Lasku_ID"
                + " FROM Laskut WHERE Lasku_ID = ?";
        ResultSet tulosjoukko = null;
        PreparedStatement lause = null;
        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
            lause.setInt( 1, getLaskuID()); // asetetaan where ehtoon (?) arvo
            // suorita sql-lause
            tulosjoukko = lause.executeQuery();
            if (tulosjoukko.next () == false) { // Laskua ei löytynyt
                throw new Exception("Laskua ei loydy tietokannasta");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC virheet
            throw e;
        }
        // parsitaan Update, päivitetään tiedot lukuunottamatta avainta
        sql = "UPDATE Laskut "
                + "SET Erapaiva = ?, Asiakas_ID = ?, Summa = ? "
                + " WHERE Lasku_ID = ?";

        lause = null;
        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);

            // laitetaan olion attribuuttien arvot UPDATEen

            lause.setString(1, getErapaiva());
            lause.setInt(2, getAsiakasID());
            lause.setInt(3, getSumma());

            // where-ehdon arvo
            lause.setInt( 4, getLaskuID());
            // suorita sql-lause
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Laskun muuttaminen ei onnistu");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC ym. virheet
            throw e;
        }
        return 0; // toiminto ok
    }


    /**
     * @param connection
     * @return 0
     * @throws SQLException
     * @throws Exception
     * Poistetaan Laskun tiedot tietokannasta.
     */



    public int poistaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys vÃ¤litetÃ¤Ã¤n parametrina

        // parsitaan DELETE
        String sql = "DELETE FROM Laskut WHERE Lasku_ID = ?";
        PreparedStatement lause = null;
        try {
            // luo PreparedStatement-olio sql-lauseelle
            lause = connection.prepareStatement(sql);
            // laitetaan arvot DELETEn WHERE-ehtoon
            lause.setInt( 1, getLaskuID());
            // suorita sql-lause
            int lkm = lause.executeUpdate();
            if (lkm == 0) {
                throw new Exception("Laskun poistaminen ei onnistu");
            }
        } catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC virheet
            throw e;
        }
        return 0; // toiminto ok
    }

}

