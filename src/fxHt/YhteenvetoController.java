package fxHt;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import paivakirja.Paivakirja;

/**
 * Kontrolleri-luokka yhteenveto-ikkunalle
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class YhteenvetoController implements ModalControllerInterface<String>{
    
    @FXML
    private TextField textEnitenPaikka;

    @FXML
    private TextField textMenoMaara;

    @FXML
    private TextField textTuloMaara;

    @FXML
    private TextField textAvgMeno;

    @FXML
    private TextField textSaastot;

    @FXML
    private TextField textKayttoSaldo;
    
    private static Paivakirja paivakirja;


    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        textEnitenPaikka.setText(haeEnitenPaikka());
        textMenoMaara.setText(haeMenoMaara());
        textTuloMaara.setText(haeTuloMaara());
        textAvgMeno.setText(haeAvgMeno());
        textSaastot.setText(haeSaldo(2));
        textKayttoSaldo.setText(haeSaldo(1));
    }
    


    //======================================================================================================
    
    /**
     * Asetetaan kontrollerille p‰iv‰kirja
     * @param paivakirjap P‰iv‰kirja joka asetetaan
     */
    public void setPaivakirja(Paivakirja paivakirjap) {
        paivakirja = paivakirjap;
    }
    /**
     * Hakee paikan jossa on k‰ytetty useimmiten rahaa
     * @return Eniten k‰ytetty paikka
     */
    public String haeEnitenPaikka() {
        return paivakirja.annaEnitenPaikka();
        
    }
    /**
     * Hakee menojen kokonaism‰‰r‰n
     * @return menojen kokonaism‰‰r‰
     */
    public String haeMenoMaara() {
        String s = Integer.toString(paivakirja.annaMenoMaara());
        return s;
    }
    /**
     * Hakee tulojen kokonaism‰‰r‰n
     * @return Tulojen kokonaism‰‰r‰
     */
    public String haeTuloMaara() {
        String s = Integer.toString(paivakirja.annaTuloMaara());
        return s;
    }
    /**
     * Hakee keskim‰‰r‰isen tulon.
     * @return Keskim‰‰r‰inen tulojen m‰‰r‰.
     */
    public String haeAvgMeno() {
        String s = Double.toString(paivakirja.annaAvgMeno());
        return s;
    }
    /**
     * Hakee halutun tilin saldon
     * @param i Tilin ID jonka saldo haetaan
     * @return Palauttaa tilin saldon
     */
    public String haeSaldo(int i) {
        String s = Double.toString(paivakirja.annaSaldo(i));
        return s;
    }


}
