package paivakirja;

import java.io.PrintStream;
import java.text.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tapahtuma-luokka
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class Tapahtuma implements Comparable<Tapahtuma>, Cloneable {

    private int tunnus = 0;
    private String nimi = "";
    private double raha = -0.00;
    private int paikkaId = 0;
    private String aika = "";
    private int tiliId = 0;
    private String lisatiedot = "";

    private static int seuraavaNro = 1;


    /**
     * Apumetodi joka t‰ytt‰‰ tapahtumalle testiarvot.
     * @example
     * <pre name="test">
     * 
     * Tapahtuma tapahtuma1 = new Tapahtuma();
     * tapahtuma1.taytaTapahtuma();
     * tapahtuma1.getNimi() === "Kauppareissu";
     * tapahtuma1.parse("4|Hevostalli|121.36510308800352|S-Market|10.02.2017|0|Maito oli loppu kaupasta");
     * tapahtuma1.getNimi() === "Hevostalli";
     * 
     * </pre>
     */
    public void taytaTapahtuma() {
        nimi = "Kauppareissu";
        raha = rand(20, 150);
        paikkaId = 1;
        aika = "10.02.2017";
        lisatiedot = "Maito oli loppu kaupasta";
    }


    /**
     * Luo satunnaisen doublen
     * @param ala Luvun alaraja
     * @param yla Luvun yl‰raja
     * @return palauttaa luodun double-luvun
     */
    public static double rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala;
        return n;
    }


    /**
     * Asettaa tilille id:n.
     * @param id asetettava id.
     */
    public void setTili(int id) {
        this.tiliId = id;
    }


    /**
     * Rekisterˆid‰‰n tapahtuma, eli annetaan sille tunnusnumero.
     * @return palauttaa tunnusnumeron.
     */
    public int rekisteroi() {
        tunnus = seuraavaNro;
        seuraavaNro++;
        return tunnus;
    }


    @Override
    public Tapahtuma clone() throws CloneNotSupportedException {
        Tapahtuma uusi;
        uusi = (Tapahtuma) super.clone();
        return uusi;
    }
    /*
     * kesken public Tapahtuma (String s) { String[] tiedot = s.split("[|]");
     * for (String i : tiedot) { i.trim(); } }
     */


    /**
     * Tulostaa tapahtuman arvot.
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(nimi);
        out.println(String.format("%4.2f", raha) + "Ä");
        out.println(aika);
        out.println(lisatiedot);
    }


    @Override
    public int compareTo(Tapahtuma vertailtava) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return sdf.parse(this.aika).compareTo(sdf.parse(vertailtava.aika));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void setTunnus(int nro) {
        tunnus = nro;
        if (tunnus >= seuraavaNro)
            seuraavaNro = tunnus + 1;
    }


    /**
     * Sijoittaa luetun rivin tiedot tapahtuman attribuutteihin.
     * @param rivi Rivi jolta tiedot luetaan.
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnus(Mjonot.erota(sb, '|', getTunnus()));
        nimi = Mjonot.erota(sb, '|', nimi);
        raha = Mjonot.erota(sb, '|', raha);
        paikkaId = Mjonot.erota(sb, '|', paikkaId);
        aika = Mjonot.erota(sb, '|', aika);
        tiliId = Mjonot.erota(sb, '|', tiliId);
        lisatiedot = Mjonot.erota(sb, '|', lisatiedot);
    }


    /**
     * get-metodi tunnukselle
     * @return palauttaa tapahtuman tunnusnumeron
     */
    public int getTunnus() {
        return tunnus;
    }


    /**
     * get-metodi nimelle
     * @return palauttaa tapahtuman nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Antaa luotavien kenttien m‰‰r‰n
     * @return Palauttaa luotavien kenttien m‰‰r‰n
     */
    public int getKenttia() {
        return 7;
    }


    /**
     * Palauttaa ensimm‰isen luotavan kent‰n.
     * @return Ensimm‰inen luotava kentt‰.
     */
    public int ekaKentta() {
        return 1;
    }


    /**
     * Palauttaa tekstin joka tulee labeliin ennen textfieldi‰.
     * @param k Tunnusnumero jonka perusteella p‰‰tet‰‰n mink‰ kent‰n otsikko palautetaan.
     * @return Palauttaa merkkijonon joka vastaa kentt‰‰n tulevaa attribuuttia.
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0:
            return "Tunnus";
        case 1:
            return "Nimi";
        case 2:
            return "Summa";
        case 3:
            return "P‰iv‰m‰‰r‰";
        case 4:
            return "Lis‰tiedot";
        case 5:
            return "Paikka";
        case 6:
            return "Tili";
        default:
            return "jee";
        }
    }


    /**
     * get-metodi tapahtuman rahasummalle.
     * @return palauttaa tapahtumassa muuttuneen rahatilanteen.
     */
    public double getRaha() {
        return raha;
    }


    /**
     * get-metodi p‰iv‰m‰‰r‰lle
     * @return Olion p‰iv‰m‰‰r‰
     */
    public String getAika() {
        return aika;
    }


    /**
     * Get-metodi lis‰tiedoille
     * @return Olion lis‰tiedot
     */
    public String getTiedot() {
        return lisatiedot;
    }


    /**
     * Antaa halutun attribuutin arvon merkkijonona.
     * @param k Tunniste jonka perusteella tiedet‰‰n mink‰ attribuutin arvo halutaan.
     * @return Palauttaa tunnistetta vastaavan attribuutin arvon merkkijonona.
     */
    public String anna(int k) {
        switch (k) {
        case 0:
            return "" + tunnus;
        case 1:
            return "" + nimi;
        case 2:
            return "" + raha;
        case 3:
            return "" + aika;
        case 4:
            return "" + lisatiedot;
        case 5:
            return "" + paikkaId;
        case 6:
            return "" + tiliId;

        default:
            return "jee";
        }
    }


    /**
     * Asettaa attribuutille arvon.
     * @param k Attribuutin "tunniste" jolle arvo halutaan asettaa
     * @param jono Arvo joka attribuutille halutaan asettaa
     * @return Palauttaa null, tarkistus siirretty toiseen metodiin.
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (k) {
        case 0:
            setTunnus(Mjonot.erota(sb, 'ß', getTunnus()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            raha = Double.parseDouble(tjono);
            return null;
        case 3:
            aika = tjono;
            return null;
        case 4:
            lisatiedot = tjono;
            return null;
        case 5:
            paikkaId = Integer.parseInt(tjono);
            return null;
        case 6:
            // tiliId = Integer.parseInt(tjono);
            return null;
        default:
            return "jee";
        }
    }


    /**
     * Tarkistaa attribuutille syˆtett‰v‰n merkkijonon oikeellisuuden
     * @param k Attribuutin "tunniste" jolle tietoa ollaan syˆtt‰m‰ss‰
     * @param jono Merkkijono joka attribuutin arvoksi halutaan syˆtt‰‰
     * @return Palauttaa null jos merkkijono on oikeellinen, virheen jos ei.
     */
    public String tarkista(int k, String jono) {
        String tjono = jono.trim();
        switch (k) {
        case 0:
            return null;
        case 1:
            if (tjono.length() == 0)
                return "Nimi ei voi olla tyhj‰!";
            return null;
        case 2:
            
            try {
                Double.parseDouble(tjono);
            } catch (NumberFormatException e) {
                return "Virheellinen summa!";
            }
            if (tjono.startsWith("+") != true) {
                if (tjono.startsWith("-") != true)
                    return "M‰‰rittele onko meno vai tulo!";
            }

            if (Double.parseDouble(tjono) == 0)
                return "Summa ei voi olla 0!";

            return null;
        case 3:
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            try {
                df.parse(tjono);
            } catch (ParseException e) {
                return "Virheellinen p‰iv‰m‰‰r‰!";
            }
            return null;
        case 4:
            return null;
        case 5:
            if (tjono.length() == 0)
                return "Paikka ei voi olla tyhj‰!";
            return null;
        case 6:
            // tiliId = Integer.parseInt(tjono);
            return null;
        default:
            return "jee";
        }
    }


    // public void set

    /**
     * Get-metodi paikalle
     * @return palauttaa paikan
     */
    public int getPaikkaId() {
        return paikkaId;
    }


    /**
     * get-metodi tilille
     * @return palauttaa tapahtuman tilin id:n
     */
    public int getTili() {
        return tiliId;
    }


    @Override
    public String toString() {
        return "" + getTunnus() + "|" + nimi + "|" + raha + "|" + paikkaId + "|"
                + aika + "|" + tiliId + "|" + lisatiedot;
    }


    /**
     * Testip‰‰ohjelma
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Tapahtuma kauppareissu = new Tapahtuma();
        Tapahtuma kauppareissu2 = new Tapahtuma();

        kauppareissu.taytaTapahtuma();
        kauppareissu2.taytaTapahtuma();
        kauppareissu.tulosta(System.out);
        System.out.println("\n");
        kauppareissu2.tulosta(System.out);

    }

}
