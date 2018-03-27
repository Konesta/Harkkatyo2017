package paivakirja;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Paikka
 * @author Laura
 * @version 23.4.2017
 *
 */
public class Paikka {

    private int id = 0;
    private String nimi = "";

    private static int seuraavaNro = 1;


    /**
     * Alustetaan paikka.
     * @param nimi Paikalle annettava nimi
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
     * </pre>
     */
    public Paikka(String nimi) {
        this.nimi = nimi;
        this.id = seuraavaNro;
        seuraavaNro++;

    }


    /**
     * Parametriton konstruktori Paikalle
     */
    public Paikka() {
        this.id = seuraavaNro;
        seuraavaNro++;
    }


    /**
     * Palautetaan paikan oma id
     * @return paikan oma id
     */
    public int getId() {
        return id;
    }


    /**
     * Palautetaan paikan nimi
     * @return paikan nimi
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Asettaa Id:n ja samalla varmistaa ett‰ seuraavaNro
     * on aina suurempi kuin t‰h‰n menness‰ suurin
     * @param nro asetettava id-numero
     */
    private void setId(int nro) {
        id = nro;
        if (id >= seuraavaNro)
            seuraavaNro = id + 1;
    }


    @Override
    public String toString() {
        return "" + id + "|" + nimi;
    }


    /**
     * Selvitt‰‰ tapahtuman tiedot |-merkeill‰ erotetusta merkkijonosta
     * Pit‰‰ huolen ett‰ seuraavaNro on suurempi kuin tuleva Id
     * @param rivi rivi josta j‰senen tiedot haetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);

    }
}
