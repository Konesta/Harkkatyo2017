package paivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author Laura
 * @version 23.4.2017
 *
 */
public class Paikat implements Iterable<Paikka> {

    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;

    private final Collection<Paikka> paikat = new ArrayList<Paikka>();


    /** 
     * Paikkojen alustaminen
     * @example
     * <pre name="test">
     * Paikka paikka = new Paikka();
     * paikka.getId() === 1;
     * 
     * Paikka paikka2 = new Paikka();
     * paikka2.getId() === 2;
     * 
     * paikka.parse("3|CM");
     * paikka.getNimi() === "CM";
     * 
     * Paikka paikka3 = new Paikka();
     * paikka3.getId() === 4;
     * 
     * Paikat paikat = new Paikat();
     * paikat.lisaa(paikka);
     * paikat.lisaa(paikka2);
     * paikat.lisaa(paikka3);
     * 
     * paikat.annaPaikka(3) === "CM";
     * 
     * paikat.annaPaikkaId("CM") === 3;
     * 
     * paikat.annaPaikkaId("Prisma") === 5;
     * 
     * </pre>
    */
    public Paikat() {
        // ei tarvitse tehd‰ viel‰ mit‰‰n
    }


    /**
    * Lis‰‰ uuden paikan tietorakenteeseen. Ottaa paikan omistukseensa.
    * @param paikka lis‰tt‰v‰ paikka.
    */
    public void lisaa(Paikka paikka) {
        paikat.add(paikka);
        muutettu = true;
    }


    /**
    * Palauttaa p‰iv‰kirjan paikkojen m‰‰r‰n.
    * @return paikkojen lukum‰‰r‰.
    */
    public int getLkm() {
        return paikat.size();
    }


    /**
    * Antaa listan paikoista
    * @return paikat-lista
    */
    public Collection<Paikka> annaPaikat() {
        return paikat;
    }


    /**
    * Antaa id:t‰ vastaavan paikan
    * @param id annettava id
    * @return palauttaa paikan merkkijonon
    */
    public String annaPaikka(int id) {
        String loydetty = "";
        for (Paikka i : paikat) {
            if (i.getId() == id)
                loydetty = i.getNimi();
        }
        return loydetty;
    }


    /**
    * Tallentaa olion tiedot tiedostoon.
    * @throws SailoException Jos kirjoittamisessa tulee ongelmia
    */
    public void tallenna() throws SailoException {
        if (!muutettu)
            return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try (PrintWriter fo = new PrintWriter(
                new FileWriter(ftied.getCanonicalPath()))) {
            for (Paikka paikka : paikat) {
                fo.println(paikka.toString());
            }
        } catch (IOException e) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia!");
        }
        muutettu = false;
    }


    /**
     * Antaa tiedoston nimen .bak muodossa
     * @return palauttaa String-muodossa .bak tiedoston
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
    * Lukee oliot tiedostosta
    * @param tied Tiedoston nimi josta luetaan
    * @throws SailoException Jos lukemisessa tulee ongelmia
    */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {
            String rivi;

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';')
                    continue;
                Paikka paikka = new Paikka("");
                paikka.parse(rivi);
                lisaa(paikka);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
    * Lukee tiedostosta
    * @throws SailoException jos lukemisessa tulee ongelmia.
    */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
    * Get-metodi tiedoston perusnimelle
    * @return palauttaa tiedoston perusnimen String muodossa
    */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
    * Palauttaa tiedoston nimen .dat muodossa
    * @return Palauttaa merkkijonona tiedoston .dat muotoisen nimen.
    */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
    * Tallentaa paikkarekisterin tiedostoon.
    * TODO ei ole viel‰ valmis!
    * @throws SailoException jos talletus ep‰onnistuu
    */
    public void talletaTiedosto() throws SailoException {
        throw new SailoException(
                "Ei osata viel‰ tallettaa tiedostoa, + tiedostonNimi");
    }


    @Override
    public Iterator<Paikka> iterator() {
        // TODO Auto-generated method stub
        return paikat.iterator();
    }


    /**
    * Asettaa tiedostolle perusnimen.
    * @param nimi Asetettava nimi
    */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;

    }


    /**
     * Metodi joka etsii ID:t‰ vastaavan paikan ja palauttaa sen nimen
     * @param paikkaId ID jota vastaava paikka halutaan etsi‰
     * @return Palauttaa halutun paikan nimen
     */
    public String getPaikka(int paikkaId) {
        for (Paikka i : paikat) {
            if (i.getId() == paikkaId)
                return i.getNimi();
        }
        return "";
    }


    /**
     * Metodi joka etsii merkkijonoa vastaavan paikan ID:n
     * @param s Merkkijono jota vastaavaa paikkaa etsit‰‰n
     * @return Palauttaa ID:n joka vastaa merkkijonoa
     */
    public int annaPaikkaId(String s) {
        for (Paikka i : paikat) {
            if (i.getNimi().equals(s))
                return i.getId();
        }
        Paikka uusi = new Paikka(s);
        lisaa(uusi);
        return uusi.getId();
    }

}
