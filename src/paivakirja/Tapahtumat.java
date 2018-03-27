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
 * Tapahtumat-luokka
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class Tapahtumat implements Iterable<Tapahtuma> {
    private static final int MAX_TAPAHTUMAT = 100;
    private int lkm = 0;
    private String tiedostonPerusNimi = "";
    boolean muutettu = false;
    private Tapahtuma tapahtumat[] = new Tapahtuma[MAX_TAPAHTUMAT];


    /**
     * Lisaa-metodi joka l‰hett‰‰ k‰skyn tietorakenteelle lis‰t‰ sinne tapahtuma
     * @param tapahtuma Tapahtuma joka halutaan lis‰t‰ tietorakenteeseen
     * @throws SailoException Jos tietorakanne t‰ynn‰
     * @example
     * <pre name="test">
     * 
     * Tapahtuma tapahtuma1 = new Tapahtuma();
     * tapahtuma1.taytaTapahtuma();
     * tapahtuma1.getNimi() === "Kauppareissu";
     * tapahtuma1.parse("1|Hevostalli|121.36510308800352|S-Market|10.02.2017|0|Maito oli loppu kaupasta");
     * tapahtuma1.getNimi() === "Hevostalli";
     * 
     * Tapahtumat tapahtumat = new Tapahtumat();
     * try {
     *   tapahtumat.lisaa(tapahtuma1);
     * } catch (SailoException e) {
     *   System.out.println("Virhe");
     * } 
     * tapahtumat.getLkm() === 1;
     * 
     * Tapahtuma tapahtuma2 = new Tapahtuma();
     * tapahtuma2.parse("2|Koiratalli|121.36510308800352|S-Market|10.02.2017|0|Maito oli loppu kaupasta");
     * try {
     *   tapahtumat.korvaaTaiLisaa(tapahtuma2);
     * } catch (SailoException e) {
     *   System.out.println("Vihe");
     * } 
     * tapahtumat.getLkm() === 2;
     * 
     * Tapahtuma tapahtuma3 = new Tapahtuma();
     * tapahtuma3.parse("2|Kissatalli|121.36510308800352|S-Market|10.02.2017|0|Maito oli loppu kaupasta");
     * try {
     *   tapahtumat.korvaaTaiLisaa(tapahtuma3);
     * } catch (SailoException e) {
     *   System.out.println("Virhe");
     * } 
     * tapahtumat.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Tapahtuma tapahtuma) throws SailoException {
        if (lkm >= tapahtumat.length) {
            Tapahtuma[] uusi = new Tapahtuma[lkm*2];
            for (int i = 0; i < lkm; i++) {
                uusi[i] = tapahtumat[i];
            }
            tapahtumat = uusi;
        }
        tapahtumat[lkm] = tapahtuma;
        lkm++;

        muutettu = true;
    }


    /**
     * Korvataan tai lis‰t‰‰n tapahtuma, riippuen ollaanko muokkaamassa vai lis‰‰m‰ss‰.
     * @param tapahtuma Tapahtuma joka halutaan lis‰t‰ tai jolla halutaan korvata olemassaoleva.
     * @throws SailoException Jos toimenpiteiss‰ tapahtuu virheit‰.
     */
    public void korvaaTaiLisaa(Tapahtuma tapahtuma) throws SailoException {
        int id = tapahtuma.getTunnus();
        for (int i = 0; i < lkm; i++) {
            if (tapahtumat[i].getTunnus() == id) {
                tapahtumat[i] = tapahtuma;
                muutettu = true;
                return;
            }
        }
        lisaa(tapahtuma);
    }


    /**
     * Poista-metodi
     * @param tunnus poistettavan tapahtuman tunnus
     * @return Palautetaan taulukon uusi koko.
     */
    public int poista(int tunnus) {
        int ind = etsiId(tunnus);
        if (ind < 0)
            return 0;
        lkm--;
        for (int i = ind; i < lkm; i++) {
            tapahtumat[i] = tapahtumat[i + 1];
        }
        tapahtumat[lkm] = null;
        muutettu = true;
        return 1;
    }


    /**
     * Etsii onko annettua Id:t‰ vastaava tapahtuma olemassa.
     * @param id ID jota etsit‰‰n Tapahtumista.
     * @return Jos vastaava tapahtuma lˆytyy, palautetaan sen ID, jos ei lˆydy palautetaan -1.
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == tapahtumat[i].getTunnus())
                return i;
        }
        return -1;
    }


    /**
     * Laskee suoritettujen maksujen m‰‰r‰n k‰yttˆililt‰
     * @return palauttaa maksujen m‰‰r‰n
     */
    public int annaMenoMaara() {
        int count = 0;
        for (Tapahtuma i : this) {
            if (i.getTili() == 1 && i.getRaha() <= 0)
                count++;
        }
        return count;
    }


    /**
     * Laskee saatujen tulojen m‰‰r‰n k‰yttˆtilille.
     * @return Palauttaa tulojen m‰‰r‰n.
     */
    public int annaTuloMaara() {
        int count = 0;
        for (Tapahtuma i : this) {
            if (i.getTili() == 1 && i.getRaha() >= 0)
                count++;
        }
        return count;
    }


    /**
     * Laskee keskim‰‰r‰isen maksun koon.
     * @return Palauttaa keskim‰‰r‰isen maksun koon.
     */
    public double maksutKA() {
        int summa = 0;
        int count = 0;
        for (Tapahtuma i : this) {
            if (i.getTili() == 1 && i.getRaha() <= 0) {
                summa += i.getRaha();
                count++;
            }
        }
        if (summa == 0 || count == 0) return 0;
        return summa / count;
    }


    /**
     * Metodi joka palauttaa tapahtuman halutusta indeksist‰
     * @param i Indeksi josta tapahtuma halutaan
     * @return Palauttaa tapahtuman
     * @throws IndexOutOfBoundsException Poikkeus joka heitet‰‰n kun yritet‰‰n hakea tapahtumaa yli taulukon koon.
     */
    public Tapahtuma anna(int i) {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("i=" + i);
        return tapahtumat[i];
    }


    /**
     * Lukee tallennetut tapahtumat tiedostosta
     * @throws SailoException Jos lukemisessa tulee ongelmia
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
    }


    /**
     * Lukee tallennetut tapahtumat tiedostosta.
     * @param tied Asetettava perusnimi tiedostolle
     * @throws SailoException Jos lukemisessa tapahtuu ongelma
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(
                new FileReader(getTiedostonNimi()))) {

            String rivi;

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                Tapahtuma tapahtuma = new Tapahtuma();
                tapahtuma.parse(rivi);
                lisaa(tapahtuma);
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
     * Tallennetaan luokan tiedot tiedostoon.
     * @throws SailoException Jos tallentamisessa tulee ongelmia.
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
            for (Tapahtuma tapahtuma : this) {
                fo.println(tapahtuma.toString());
            }
        } catch (IOException e) {
            throw new SailoException("Tiedoston " + ftied.getName()
                    + " kirjoittamisessa ongelmia!");
        }
        muutettu = false;
    }


    /**
     * Antaa tiedoston .bak muodossa
     * @return palauttaa merkkijonona tiedoston nimen .bak muodossa
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
     * Palauttaa tiedoston nimen .dat muodossa
     * @return Palauttaa merkkijonona tiedoston .dat muotoisen nimen
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Asettaa tiedostolle nimen
     * @param tied asetettava nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;

    }


    /**
     * Antaa tapahtumat taulukkona
     * @return Palauttaa tapahtuma-taulukon
     */
    public Tapahtuma[] getTapahtumat() {
        return tapahtumat;
    }


    /**
     * Asetetaan arvo "Muutettu"-attribuutille
     * @param a Asetettava arvo
     */
    public void setMuutettu(boolean a) {
        muutettu = a;
    }


    /**
     * Antaa halutun tilin saldon.
     * @param tili Tili jonka saldo halutaan tarkistaa
     * @return Halutun tilin saldo palautetaan
     */
    public double annaSaldo(int tili) {
        double saldo = 0;
        for (Tapahtuma i : this) {
            if (i.getTili() == tili) {
                saldo += i.getRaha();
            }
        }
        return saldo;
    }


    /**
     * Antaa paikan ID:n jota on k‰ytetty eniten.
     * @return Paikan ID jota on k‰ytetty eniten.
     */
    public int annaEnitenPaikka() {
        ArrayList<Integer> paikkaEsiintymat = new ArrayList<Integer>();
        for (Tapahtuma i : this) {
            paikkaEsiintymat.add(i.getPaikkaId());
        }
        int yleisin = etsiYleisin(paikkaEsiintymat);

        return yleisin;
    }


    private int etsiYleisin(ArrayList<Integer> paikkaEsiintymat) {
        if (paikkaEsiintymat.isEmpty())
            return 0;
        Collections.sort(paikkaEsiintymat);
        int yleisin = 0;
        int edellinen = 0;
        int mostCount = 0;
        int lastCount = 0;

        for (int i : paikkaEsiintymat) {
            if (i == edellinen) {
                lastCount++;
            }
            if (lastCount > mostCount) {
                mostCount = lastCount;
                yleisin = edellinen;
            }
            edellinen = i;
        }
        return yleisin;
    }


    /**
     * Lajitellaan tapahtumat p‰iv‰m‰‰r‰n mukaan, ei viel‰ k‰ytˆss‰.
     */
    public void lajittele() {
        Tapahtuma[] lajiteltavat = new Tapahtuma[lkm];
        for (int i = 0; i < lkm; i++) {
            lajiteltavat[i] = tapahtumat[i];
        }
        Arrays.sort(lajiteltavat);
        tapahtumat = lajiteltavat;
        
    }


    /**
     * Palauttaa tapahtumien lukum‰‰r‰n.
     * @return tapahtumien lukum‰‰r‰
     */
    public int getLkm() {

        return lkm;
    }

    /**
     * Iteraattori tapahtumille.
     */
    public class TapahtumatIterator implements Iterator<Tapahtuma> {
        private int kohdalla = 0;


        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        @Override
        public Tapahtuma next() {
            if (!hasNext())
                throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }
    }


    @Override
    public Iterator<Tapahtuma> iterator() {
        return new TapahtumatIterator();
    }

}
