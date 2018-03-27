package fxHt;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import paivakirja.Paivakirja;
import paivakirja.Tapahtuma;

/**
 * Tapahtuman tiedot n‰ytt‰v‰n ikkunan kontrolleri
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class TapahtumaController implements ModalControllerInterface<Tapahtuma>,Initializable {
	
    
    @FXML
    private GridPane gridTapahtuma;
    
    @FXML
    private Label labelVirhe;
	
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    /**
     * K‰sitell‰‰n Peruuta napin painaminen.
     */
    @FXML
    void handlePeruuta() {
        tapahtumaKohdalla = null;
    	ModalController.closeStage(gridTapahtuma);
    }

    /**
     * K‰sitell‰‰n Tallenna napin painaminen.
     */
    @FXML
    void handleTallenna() {
        String virhe = null;
        for (TextField edit : edits) {
            if (edit != null ){
                virhe = tarkistaMuutosTapahtumaan(edit);
                if (virhe == null) {
                    Dialogs.setToolTipText(edit, "");
                    edit.getStyleClass().removeAll("virhe");
                    naytaVirhe(virhe);
                }
                else {
                    Dialogs.setToolTipText(edit, virhe);
                    edit.getStyleClass().add("virhe");
                    naytaVirhe(virhe);
                    return;
                }
            }
        }
        for (TextField edit : edits) {
            if (edit != null ){
                
                kasitteleMuutosTapahtumaan(edit);
            }
        }
    	ModalController.closeStage(gridTapahtuma);
    }


	@Override
	public void handleShown() {
	    kentta = Math.max(aputapahtuma.ekaKentta(), Math.min(kentta, aputapahtuma.getKenttia()-1));
	    edits[kentta].requestFocus();
		
	}
	

    


    @Override
    public Tapahtuma getResult() {
        return tapahtumaKohdalla;
    }
    
    @Override
    public void setDefault(Tapahtuma oletus) {
        ModalController.getStage(labelVirhe).initStyle(StageStyle.UNDECORATED);
        tapahtumaKohdalla = oletus;
        naytaTapahtuma(edits, tapahtumaKohdalla);
        
    }
    
    //===============================================================================
    private static Paivakirja paivakirja;
    private Tapahtuma tapahtumaKohdalla;
    private static Tapahtuma aputapahtuma = new Tapahtuma();
    private TextField[] edits;
    private int kentta = 0;

    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    /**
     * Asetetaan kontrollerille p‰iv‰kirja
     * @param paivakirjap Asetettava p‰iv‰kirja
     */
    public void setPaivakirja(Paivakirja paivakirjap) {
        paivakirja = paivakirjap;
    }
    
    /**
     * N‰ytt‰‰ virheen jos syˆtetyt tiedot ei mene tarkistajasta l‰pi.
     * @param virhe Virhe joka n‰ytet‰‰n
     */
    private void naytaVirhe(String virhe)  {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
	
    
    /**
     * N‰ytt‰‰ tapahtuman attribuutin sille kuuluvassa kent‰ss‰
     * @param edits Taulukko jossa on kent‰t johon tiedot n‰ytet‰‰n.
     * @param tapahtuma Tapahtuma josta tieto haetaan.
     */
    public static void naytaTapahtuma(TextField[] edits, Tapahtuma tapahtuma){
        if (tapahtuma == null) return;
        for (int k = tapahtuma.ekaKentta(); k < tapahtuma.getKenttia(); k++) {
            if (k == 5){
                edits[k].setText(paivakirja.getPaikka(tapahtuma.getPaikkaId()));
            }
            else if (k == 6){
                edits[k].setText(paivakirja.annaTili(tapahtuma.getTili()));
            }
            else edits[k].setText(tapahtuma.anna(k));
        }
    }
    
    /**
     * Aliohjelma jolta haetaan haluttu tapahtuma.
     * @param modalityStage Modaalinen ikkuna
     * @param oletus K‰sitelt‰v‰ tapahtuma
     * @param kentta Kentt‰ johon focus asetetaan.
     * @return Palauttaa halutun tapahtuman
     */
	public static Tapahtuma kysyTapahtuma(Stage modalityStage, Tapahtuma oletus, int kentta) {
	    return ModalController.<Tapahtuma, TapahtumaController>showModal(
	                TapahtumaController.class.getResource("muokkaaView.fxml"),
	                "Paivakirja",
	                modalityStage, oletus, ctrl -> ctrl.setKentta(kentta));
	}
	
	/**
	 * Luo ikkunan kaikki kent‰t dynaamisesti
	 * @param gridTapahtuma Gridi johon kent‰t luodaan.
	 * @return Palauttaa luodun TextField taulukon.
	 */
	public static TextField[] luoKentat(GridPane gridTapahtuma){
	    gridTapahtuma.getChildren().clear();
	    TextField[] edits = new TextField[aputapahtuma.getKenttia()];
	    
	    for  (int i = 0, k = aputapahtuma.ekaKentta(); k < aputapahtuma.getKenttia(); k++, i++) {
	        Label label = new Label(aputapahtuma.getKysymys(k));
	        gridTapahtuma.add(label, 0, i);
	        TextField edit = new TextField();
	        edits[k] = edit;
	        edit.setId("e"+k);
	        gridTapahtuma.add(edit, 1, i);
	        if(k == 6) edit.setEditable(false);
	    }
	    return edits;
	}
	
	/**
	 * Hoitaa kontrollerin alustuksen, luo TextField-taulukon tapahtuman kentist‰.
	 */
    private void alusta() {
        edits = luoKentat(gridTapahtuma);

        
        // panelTapahtuma.setFitToHeight(true);
    }
    

    /**
     * Asettaa tapahtumalle uudet arvot jos sit‰ muokataan
     * @param edit TextField jonka tietoa on muokattu.
     */
    private void kasitteleMuutosTapahtumaan(TextField edit) {
        
        
         int k = getFieldId(edit,aputapahtuma.ekaKentta());
         String s = edit.getText();
          
         if (k == 5) {
             int id =paivakirja.annaPaikkaId(s);
             s = Integer.toString(id);
         }
         
         tapahtumaKohdalla.aseta(k,s);

        
        }
    
    /**
     * Tarkastaa onko kentt‰‰n syˆtetty arvo validi arvo kyseiselle attribuutille
     * @param edit Kentt‰ jonka sis‰ltˆ‰ tarkistetaan
     * @return Palauttaa virheen.
     */
    private String tarkistaMuutosTapahtumaan(TextField edit) {
        
        
        int k = getFieldId(edit,aputapahtuma.ekaKentta());
        String s = edit.getText();

        String virhe = tapahtumaKohdalla.tarkista(k,s);
        
        return virhe;

       
       }
    
    /**
     * Antaa TextFieldin tunnisteen.
     * @param obj TextField jonka Id haetaan, k‰sitell‰‰n Objektina ja castataan nodeksi
     * @param oletus Oletus Id joka palautetaan
     * @return Palauttaa textfieldin tunnisteen.
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    }
	
