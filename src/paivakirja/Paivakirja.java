package paivakirja;

import java.io.File;
import java.util.Collection;

/**
 * P‰iv‰kirja-luokka
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class Paivakirja {
    private Tapahtumat tapahtumat = new Tapahtumat();
    private Tilit tilit = new Tilit();
    private Paikat paikat = new Paikat();


    /**
     * Constructori
     * @example
     * <pre name="test">
     * 
     * Paivakirja paivakirja = new Paivakirja();
     * paivakirja.getTapahtumia() === 0;
     * 
     * Tapahtuma tapahtuma1 = new Tapahtuma();
     * tapahtuma1.taytaTapahtuma();
     * try {
     *      paivakirja.lisaa(tapahtuma1);
     * } catch (SailoException e) {
     *      System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage());
     * }
     * paivakirja.getTapahtumia() === 1;
     * 
     * Tapahtuma tapahtuma2 = new Tapahtuma();
     * tapahtuma2.taytaTapahtuma();
     * try {
     *      paivakirja.lisaa(tapahtuma2);
     * } catch (SailoException e) {
     *      System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage());
     * }
     * paivakirja.getTapahtumia() === 2;
     * paivakirja.annaTapahtuma(0) === tapahtuma1;
     * paivakirja.annaTapahtuma(1) === tapahtuma2;
     * 
     * paivakirja.setTiedosto("testifilu");
     * try {
     *      paivakirja.tallenna();
     * } catch (SailoException e) {
     *      System.out.println("Virhe tallennuksessa");
     * }
     * 
     * Tapahtuma tapahtuma3 = new Tapahtuma();
     * tapahtuma3.taytaTapahtuma();
     * try {
     *      paivakirja.lisaa(tapahtuma3);
     * } catch (SailoException e) {
     *      System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage());
     * }
     * paivakirja.getTapahtumia() === 3;
     * 
     * try {
     *      paivakirja.lueTiedostosta("testifilu");
     * } catch (SailoException e) {
     *      System.out.println("Virhe avauksessa " + e.getMessage());
     * }
     * 
     * 
     * </pre>
     */

    public Paivakirja() {
        // Ei tee mit‰‰n
    }


    /**
     * Antaa tapahtumien m‰‰r‰n "tapahtumat"-luokassa
     * @return tapahtumien m‰‰r‰
     */
    public int getTapahtumia() {
        return tapahtumat.getLkm();
    }


    /**
     * Muuttaa tapahtuman "Muutettu"-arvon jolloin se tiedet‰‰n tallentaa, tai olla tallentamatta.
     * @param a Boolean arvo joka annetaan "Muutettu"-attribuutille
     */
    public void setMuutettu(boolean a) {
        tapahtumat.setMuutettu(a);
    }


    /**
     * Tallentaa luokkien tiedot tiedostoon.
     * @throws SailoException Jos tallentamisissa tapahtuu virheit‰.
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            tapahtumat.tallenna();
            tilit.tallenna();
            paikat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }

        if (!"".equals(virhe))
            throw new SailoException(virhe);
    }


    /**
     * Poistaa tapahtumia (kesken)
     * @param poistettava poistettava tapahtuma
     * @return palauttaa -1 jos poista ei onnistu muuten >=1
     */
    public int poista(Tapahtuma poistettava) {
        if (poistettava == null)
            return 0;
        int ret = tapahtumat.poista(poistettava.getTunnus());
        return ret;
    }


    /**
     * Lis‰t‰‰n uusi tili
     * @param tili lis‰tt‰v‰ tili
     */
    public void lisaa(Tili tili) {
        tilit.lisaa(tili);
    }


    /**
     * Lis‰t‰‰n uusi paikka
     * @param paikka lis‰tt‰v‰ paikka
     */
    public void lisaa(Paikka paikka) {
        paikat.lisaa(paikka);
    }


    /**
     * Hakee taulukkona kaikki tapahtumat
     * @return tapahtumat taulukossa;
     */
    public Tapahtuma[] getTapahtumat() {
        return tapahtumat.getTapahtumat();
    }


    /**
     * Hakee listana kaikki paikat
     * @return Paikat-luokan paikat listassa
     */
    public Collection<Paikka> annaPaikat() {
        return paikat.annaPaikat();
    }


    /**
     * Lis‰‰ uuden tapahtuman p‰iv‰kirjaan
     * @param tapahtuma Tapahtuma joka halutaan lis‰t‰
     * @throws SailoException jos tietorakenne t‰ynn‰
     */
    public void lisaa(Tapahtuma tapahtuma) throws SailoException {
        tapahtumat.lisaa(tapahtuma);
    }


    /**
     * Lukee tiedostosta kaikkien luokkien oliot.
     * @param nimi Tiedoston nimi josta luetaan.
     * @throws SailoException Jos lukemisessa tulee ongelmia
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        tapahtumat = new Tapahtumat();
        tilit = new Tilit();
        paikat = new Paikat();

        setTiedosto(nimi);
        tapahtumat.lueTiedostosta();
        tilit.lueTiedostosta();
        paikat.lueTiedostosta();
    }


    /**
     * Luo uuden kansion annetulle p‰iv‰kirjalle mik‰li kansiota ei ole viel‰ olemassa sek‰ asettaa tiedostoille perusnimen.
     * @param nimi P‰iv‰kirjan nimi jonka mukaan kansio nimet‰‰n.
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty())
            hakemistonNimi = nimi + "/";
        tapahtumat.setTiedostonPerusNimi(hakemistonNimi + "tapahtumat");
        tilit.setTiedostonPerusNimi(hakemistonNimi + "tilit");
        paikat.setTiedostonPerusNimi(hakemistonNimi + "paikat");
    }


    /**
     * Antaa eniten k‰ytetyn paikan nimen
     * @return Eniten k‰ytetty paikka merkkijonona
     */
    public String annaEnitenPaikka() {
        int yleisin = tapahtumat.annaEnitenPaikka();
        if (yleisin == 0)
            return "Tapahtumia alle 2";
        return paikat.getPaikka(yleisin);
    }


    /**
     * Antaa menojen kokonaism‰‰r‰n
     * @return Menojen kokonaism‰‰r‰ kokonaislukuna.
     */
    public int annaMenoMaara() {
        return tapahtumat.annaMenoMaara();
    }


    /**
     * Antaa tulojen kokonaism‰‰r‰n
     * @return Tulojen kokonaism‰‰r‰ kokonaislukuna
     */
    public int annaTuloMaara() {
        return tapahtumat.annaTuloMaara();
    }


    /**
     * Antaa halutun tapahtuman
     * @param i tapahtuma joka halutaan
     * @return palauttaa halutun tapahtuman
     */
    public Tapahtuma annaTapahtuma(int i) {
        return tapahtumat.anna(i);
    }


    /**
     * Antaa id:t‰ vastaavan tilin
     * @param id Id jota vastaava tili halutaan
     * @return tili joka vasta id:t‰.
     */
    public String annaTili(int id) {
        return tilit.annaTili(id);
    }


    /**
     * Antaa tilien lukum‰‰r‰n
     * @return Palauttaa tilien lukum‰‰r‰n kokonaislukuna
     */
    public int getTileja() {
        return tilit.getTileja();
    }


    /**
     * Hakee ID:t‰ vastaavan paikan nimen
     * @param paikkaId ID jota vastaavaa paikkaa haetaan
     * @return ID:t‰ vastaavan paikan nimi merkkijonona.
     */
    public String getPaikka(int paikkaId) {
        return paikat.getPaikka(paikkaId);
    }


    /**
     * Korvataan tapahtuma tai lis‰t‰‰n uusi, riippuen muokataanko vai lis‰t‰‰nkˆ.
     * @param tapahtuma Tapahtuma jolla korvataan tai joka lis‰t‰‰n.
     * @throws SailoException Jos toimenpiteiss‰ tulee ongelmia
     */
    public void korvaaTaiLisaa(Tapahtuma tapahtuma) throws SailoException {
        tapahtumat.korvaaTaiLisaa(tapahtuma);

    }


    /**
     * Antaa merkkijonoa vastaavan paikan ID:n
     * @param s Merkkijono jota vastaavaa paikkaa etsit‰‰n
     * @return Palauttaa ID:t‰ vastaavan paikan nimen merkkijonona.
     */
    public int annaPaikkaId(String s) {
        return paikat.annaPaikkaId(s);

    }


    /**
     * Antaa keskim‰‰r‰isen menon.
     * @return Keskim‰‰r‰inen meno desimaalilukuna.
     */
    public double annaAvgMeno() {
        return tapahtumat.maksutKA();
    }


    /**
     * Antaa halutun tilin saldon.
     * @param i Tilin ID jonka saldo halutaan laskea.
     * @return Palauttaa desimaalilukuna ID:t‰ vastaavan tilin saldon.
     */
    public double annaSaldo(int i) {
        return tapahtumat.annaSaldo(i);
    }

    /**
     * Lajittelee tapahtumat p‰iv‰m‰‰r‰n mukaan
     */
    public void lajittele() {
        tapahtumat.lajittele();
        
    }

}
