package paivakirja;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tili, jolla on ominaisuutena id ja nimi.
 * 
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta 
 * @version 23.4.2017
 *
 */
public class Tili {

    private int id = 0;
    private String nimi = "";
    private String lisatiedot = "";

    private static int seuraavaNro = 1;



    /**
     * Alustetaan tili.
     * @param nimi Tilille asetettava nimi
     * @example
     * <pre name="test">
     * Tili tili = new Tili();
     * tili.getId() === 1;
     * 
     * Tili tili2 = new Tili();
     * tili2.getId() === 2;
     * 
     * tili.parse("3|Käyttötili|Ei ole rahaa");
     * tili.getNimi() === "Käyttötili";
     * 
     * Tili tili3 = new Tili();
     * tili3.getId() === 4;
     * </pre>
     */
    public Tili(String nimi) {
        this.nimi = nimi;
        this.id = seuraavaNro;
        seuraavaNro++;

    }


    /**
     * Parametriton konstruktori Tilille.
     */
    public Tili() {
        this.id = seuraavaNro;
        seuraavaNro++;
    }


    /**
     * Täyttää tilin tiedot.
     */
    public void taytaTiedot() {
        this.nimi = "Käyttötili";
        this.lisatiedot = "Käytössä olevat rahat";
    }


    /**
     * Palautetaan tilin oma id
     * @return tilin oma id
     */
    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "" + id + "|" + nimi + "|" + lisatiedot;
    }


    /**
     * Asettaa Id:n ja samalla varmistaa että seuraavaNro
     * on aina suurempi kuin tähän mennessä suurin
     * @param nro asetettava id-numero
     */
    private void setId(int nro) {
        id = nro;
        if (id >= seuraavaNro)
            seuraavaNro = id + 1;
    }


    /**
     * Selvittää tapahtuman tiedot |-merkeillä erotetusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva Id
     * @param rivi rivi josta jäsenen tiedot haetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        nimi = Mjonot.erota(sb, '|', nimi);
        lisatiedot = Mjonot.erota(sb, '|', lisatiedot);

    }


    /**
     * Palautetaan tilin nimi
     * @return tilin nimi
     */
    public String getNimi() {
        return nimi;
    }

}
