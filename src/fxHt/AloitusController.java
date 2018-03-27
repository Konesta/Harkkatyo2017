package fxHt;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Controlleri aloitusikkunalle
 * @author Konsta Suutari, Laura Rantonen, Tuomo Saloranta
 * @version 23.4.2017
 *
 */
public class AloitusController implements ModalControllerInterface<String>{

    @FXML
    private TextField textNimi;

    @FXML
    private Button OK;
    
    private String vastaus = null;
    

    /**
     * K‰sitell‰‰n OK-nappi joka sulkee ikkunan.
     */
    @FXML
    void handleOK() {
        vastaus = textNimi.getText();
        ModalController.closeStage(textNimi);
    }
    

	@Override
	public String getResult() {
		return vastaus;
	}

	@Override
	public void handleShown() {
		textNimi.requestFocus();
		
	}

	@Override
	public void setDefault(String oletus) {
		textNimi.setText(oletus);
		
	}
	
	/**
	 * Luodaan dialogi joka kysyy nimen ja palautetaan se.
	 * @param modalityStage Mille ollaan modaalisia, null = sovellukselle
	 * @param oletus Nimi jota k‰ytet‰‰n oletuksena
	 * @return Palauttaa kirjoitetun nimen
	 */
	public static String kysyNimi(Stage modalityStage, String oletus) {
	    return ModalController.showModal(
	            AloitusController.class.getResource("aloitusView.fxml"),
	            "paivakirja",
	            modalityStage, oletus);
	}
    


}
