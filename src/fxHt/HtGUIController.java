package fxHt;


import paivakirja.Tapahtuma;
import paivakirja.Tili;

import paivakirja.Paivakirja;
import paivakirja.SailoException;

import java.io.PrintStream;
import java.net.URL;

import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import static fxHt.TapahtumaController.getFieldId;

/**
 * 
 * @author Konsta Suutari, Tuomo Saloranta, Laura Rantonen
 * @version 23.4.2017
 *
 */
public class HtGUIController implements Initializable {
    
    @FXML
    private ListChooser<Tapahtuma> chooserTapahtumat;

    @FXML
    private ScrollPane panelTapahtuma;
    

    @FXML
    private TextField editNimi;

    @FXML
    private TextField editSumma;

    @FXML
    private TextField editPaikka;

    @FXML
    private TextField editAika;

    @FXML
    private TextField editTiedot;
    
    @FXML
    private TextField editTili;
    
    @FXML
    private GridPane gridTapahtuma;
    
    private String paivakirjanimi = "paivakirja";

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    

    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        handleTallenna();
        return true;
    }
    
/**
 * Tallentaja
 */
    private void handleTallenna() {
        tallenna();
        
    }
    
    @FXML
    private void handleLajittele() {
        lajittele();
    }
    
    /**
     * Muuttaa tapahtuman tilin k‰yttˆtiliksi
     */
    @FXML
    void handleLisaaTili() {
        lisaaTili(1);
    }
    
    /**
     * Muuttaa tapahtuman tilin s‰‰stˆtiliksi
     */
    @FXML
    void handleLisaaSaastoTili() {
        lisaaTili(2);
    }
    
    /**
     * Muuttaa tapahtuman tilin ulkoiseksi tiliksi
     */
    @FXML
    void handleLisaaUlkoTili() {
        lisaaTili(3);
    }



/**
 * K‰sittelee "About"-painikkeen painalluksen.
 */
    @FXML
    void handleAbout() {
        ModalController.showModal(HtGUIController.class.getResource("aboutView.fxml"), "About", null, "");
    }
    
    /**
     * K‰sittelee "Avaa"-painikkeen painalluksen.
     */

    @FXML
    void handleAvaa() {
        avaa();
    }
    

    @FXML
    void handlePoista() {
        poista();
    }

    /**
     * K‰sittelee "Lis‰‰"-painikkeen painalluksen
     * @throws SailoException Jos uuden lis‰‰misess‰ tulee ongelmia
     */
    
    @FXML
    private void handleAvaaLisaa() throws SailoException {
        uusiTapahtuma();
    }

    /**
     * K‰sittelee "Muokkaa"-painikkeen painalluksen
     */
    @FXML
    void handleAvaaMuokkaa() {
        muokkaa(1);
    }

    /**
     * K‰sittelee "Yhteenveto"-painikkeen painalluksen
     */
    @FXML
    void handleAvaaYhteenveto() {
        ModalController.showModal(HtGUIController.class.getResource("yhteenvetoView.fxml"), "Yhteenveto", null, "");
    }
    /**
     * K‰sittelee "Lopeta" painikkeen painalluksen
     */
    @FXML
    void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    //==================================================================================================================
    
    private Paivakirja paivakirja;
    private TextField edits[];
    private Tapahtuma tapahtumaKohdalla;
    private int kentta = 0;
    
    /**
     * Tekee tarvittavat alustukset
     * Asetetaan fontti ja kuuntelija tapahtumalistalle.
     */
    protected void alusta() {
        
        panelTapahtuma.setFitToHeight(true);
        
        chooserTapahtumat.clear();
        chooserTapahtumat.addSelectionListener(e -> naytaTapahtuma());
        edits = TapahtumaController.luoKentat(gridTapahtuma);
        for (TextField edit: edits) {
            if (edit != null) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); } );
                edit.focusedProperty().addListener((a, o, n) -> kentta = getFieldId(edit, kentta));
            }
        }
    }
    
    /**
     * Lajittelee tapahtumat p‰iv‰m‰‰r‰n mukaan
     */
    public void lajittele() {
        paivakirja.lajittele();
        int index = chooserTapahtumat.getSelectedIndex();
        chooserTapahtumat.clear();
        hae(0);
        chooserTapahtumat.setSelectedIndex(index);
        
    }
    
    /**
     * Tallentaa tiedot kun ohjelma suljetaan
     * @return Palauttaa virheilmoituksen jos sellainen tapahtuu
     */
    private String tallenna() {
        Dialogs.showMessageDialog("Tallennetaan!");
        try {
            paivakirja.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia!" + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    /**
     * Hoitaa tapahtuman muokkauksen
     * @param k Kentt‰ johon focus asetetaan aloittaessa muokkaus
     */
    private void muokkaa(int k) {
        if (tapahtumaKohdalla == null) return;
        
        try {
            Tapahtuma tapahtuma;
            tapahtuma = TapahtumaController.kysyTapahtuma(null, tapahtumaKohdalla.clone(), k);
            if ( tapahtuma == null ) return;
            
            paivakirja.korvaaTaiLisaa(tapahtuma);
            hae(tapahtuma.getTunnus());
            } catch (SailoException e) {
                Dialogs.showMessageDialog(e.getMessage());
            
        } catch (CloneNotSupportedException e) {
            // 
        }
    }
    
    /**
     * Poistaa valitun tapahtuman
     */
    private void poista() {
        if (tapahtumaKohdalla == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko tapahtuma: " + tapahtumaKohdalla.getNimi(),
                "Kyll‰", "Ei") ) return;
        paivakirja.poista(tapahtumaKohdalla);
        int index = chooserTapahtumat.getSelectedIndex();
        hae(0);
        chooserTapahtumat.setSelectedIndex(index);
    }
    
    /**
     * N‰ytt‰‰ valitun j‰senen tekstialueella
     */
    protected void naytaTapahtuma() {
        tapahtumaKohdalla = chooserTapahtumat.getSelectedObject();
        
        if (tapahtumaKohdalla == null) return;
        
        TapahtumaController.naytaTapahtuma(edits, tapahtumaKohdalla);
    }
    
    /**
     * Tulostaa tapahtuman tiedot
     * @param os tietovirta johon tulostetaan
     * @param tapahtuma tapahtuma joka tulostetaan
     */
    public void tulosta(PrintStream os, final Tapahtuma tapahtuma) {
        os.println("--------------------------------------");
        tapahtuma.tulosta(os);

        os.print("----------------------------------------");
        os.println("Paikka: " + paivakirja.getPaikka(tapahtuma.getPaikkaId()));
        os.println("\n" +  "Tili: " + paivakirja.annaTili(tapahtuma.getTili()));
    }
    
    /**
     * Alustetaan p‰iv‰kirja
     * @param paivakirja P‰iv‰kirja joka alustetaan t‰m‰n kontrollerin k‰ytett‰v‰ksi.
     */
    public void setPaivakirja(Paivakirja paivakirja) {
        this.paivakirja = paivakirja;
        naytaTapahtuma();
    }
    
    /**
     * Haetaan tapahtuma
     * @param tnro Tapahtuman tunnusnumero joka halutaan hakea.
     */
    protected void hae (int tnro) {
        chooserTapahtumat.clear();
        
        int index = 0;
        for (int i = 0; i < paivakirja.getTapahtumia(); i++) {
            Tapahtuma tapahtuma = paivakirja.annaTapahtuma(i);
            if (tapahtuma.getTunnus() == tnro) index = i;
            chooserTapahtumat.add(tapahtuma.getNimi(), tapahtuma);
        }
        chooserTapahtumat.setSelectedIndex(index);
    }
    
    /**
     * Lis‰t‰‰n uusi tapahtuma
     * @throws SailoException Jos lis‰‰misen kanssa tulee ongelmia
     */
    protected void uusiTapahtuma() throws SailoException {
            try {
                Tapahtuma uusi = new Tapahtuma();
                uusi = TapahtumaController.kysyTapahtuma(null, uusi, 1);
                if (uusi == null) return;
                uusi.rekisteroi();
                paivakirja.lisaa(uusi);
                hae(uusi.getTunnus());
            } catch (SailoException e) {
                throw new SailoException("Ongelmia lisaamisen kanssa!" + e.getMessage());
            }

    }
    
    /**
     * Lis‰‰ tilin.
     * @param i Lis‰tt‰v‰n tilin ID
     */
    protected void lisaaTili(int i) {
        if (tapahtumaKohdalla == null) return;
        tapahtumaKohdalla.setTili(i);
        paivakirja.setMuutettu(true);
        hae(tapahtumaKohdalla.getTunnus());
    }
    
    /**
    * Kysyt‰‰n tiedoston nimi ja luetaan se
    * @return true jos onnistui, false jos ei 
    */
        public boolean avaa() {
            String uusinimi = AloitusController.kysyNimi(null, paivakirjanimi);
            if (uusinimi == null) return false;
            lueTiedosto(uusinimi);
            if(paivakirja.getTileja() == 0) {
            Tili kayttotili = new Tili("K‰yttˆtili");
            Tili saastotili = new Tili("S‰‰stˆtili");
            Tili ulkoinen = new Tili("Ulkoinen");
            paivakirja.lisaa(kayttotili);
            paivakirja.lisaa(saastotili);
            paivakirja.lisaa(ulkoinen);
            }
            return true;
        }


     /**
      * Luetaan tiedosto, ei osaa viel‰ oikeasti lukea tiedostoa
      * @param nimi Paivakirjan nimi.
      * @return Palauttaa virheen jos sellainen tapahtuu
      */
    protected String lueTiedosto(String nimi) {
        paivakirjanimi = nimi;
        setTitle(paivakirjanimi);
        try {
            paivakirja.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if(virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    private void setTitle(String title) {
        ModalController.getStage(panelTapahtuma).setTitle(title);
    }


}
