package paivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * P‰iv‰kirjan tilit, joka osaa lis‰t‰ uuden tilin,
 * ja pit‰‰ yll‰ varsinaista tilirekisteri‰.
 * 
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class Tilit implements Iterable<Tili> {

    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;

    private final Collection<Tili> tilit = new ArrayList<Tili>();


    /**
     * Tilien alustaminen
     * @example
     * <pre name="test">
     * Tili tili = new Tili();
     * tili.getId() === 1;
     * 
     * Tili tili2 = new Tili();
     * tili2.getId() === 2;
     * 
     * tili.parse("3|K‰yttˆtili|Ei ole rahaa");
     * tili.getNimi() === "K‰yttˆtili";
     * 
     * Tili tili3 = new Tili();
     * tili3.getId() === 4;
     * 
     * Tilit tilit = new Tilit();
     * tilit.lisaa(tili);
     * tilit.lisaa(tili2);
     * tilit.lisaa(tili3);
     * 
     * tilit.annaTili(3) === "K‰yttˆtili";
     * 
     * </pre>
     */
    public Tilit() {
        // Ei tarvitse tehd‰ viel‰ mit‰‰n
    }


    /**
     * Lis‰‰ uuden tilin tietorakenteeseen. Ottaa tilin omistukseensa.
     * @param tili lis‰tt‰v‰ tili.
     */
    public void lisaa(Tili tili) {
        tilit.add(tili);
        muutettu = true;
    }


    /**
     * Antaa halutun tilin
     * @param id Id jota vastaava tili halutaan
     * @return palauttaa tilin joka vastaa parametrina tuotua id:t‰
     */
    public String annaTili(int id) {
        for (Tili i : tilit) {
            if (i.getId() == id)
                return i.getNimi();
        }
        return "";
    }


    /**
     * Palauttaa p‰iv‰kirjan tilien m‰‰r‰n.
     * @return tilien lukum‰‰r‰.
     */
    public int getLkm() {
        return tilit.size();
    }


    /**
     * Tallentaa olioiden tiedot tiedostoon
     * @throws SailoException Jos tallennuksessa tapahtuu ongelmia.
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
            for (Tili tili : tilit) {
                fo.println(tili.toString());
            }
        } catch (IOException e) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia!");
        }
        muutettu = false;
    }


    private String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
     * Lukee tilirekisterin tiedostosta.
     * @param tied tiedoston nimi
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {
            String rivi;

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                Tili tili = new Tili();
                tili.parse(rivi);
                lisaa(tili);
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
     * Lukee tiedot tiedostosta.
     * @throws SailoException jos lukemisessa tapahtuu ongelmia.
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Antaa tiedoston perusnimen.
     * @return tiedoston perusnimi.
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Antaa tiedoston nimen .dat muodossa
     * @return .dat muotoinen tiedostonimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Tallentaa tilirekisterin tiedostoon.
     * TODO ei ole viel‰ valmis!
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void talletaTiedosto() throws SailoException {
        throw new SailoException(
                "Ei osata viel‰ tallettaa tiedostoa, + tiedostonNimi");
    }


    @Override
    public Iterator<Tili> iterator() {
        return tilit.iterator();
    }


    /**
     * Asettaa tiedostolle perusanimen
     * @param nimi Asetettava nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;

    }


    /**
     * Antaa tilien lukum‰‰r‰n
     * @return Tilien lukum‰‰r‰
     */
    public int getTileja() {
        return tilit.size();
    }

}
