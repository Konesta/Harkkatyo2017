package paivakirja;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja, jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksessa näytettävä viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
