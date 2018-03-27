package fxHt;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import paivakirja.Paivakirja;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Konsta Suutari, Tuomo Saloranta, Laura Rantonen
 * @version 23.4.2017
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	    try {
	        FXMLLoader ldr = new FXMLLoader(getClass().getResource("paaikkunaView.fxml"));
	        final Pane root = (Pane)ldr.load();
	        final HtGUIController paivakirjaCtrl = (HtGUIController)ldr.getController();
	        final TapahtumaController tapahtumaCtrl = new TapahtumaController();
	        final YhteenvetoController yvCtrl = new YhteenvetoController();
	        
	        final Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Rahapäiväkirja");
	        
	        
	        primaryStage.setOnCloseRequest((event) -> {
	            
	            if ( !paivakirjaCtrl.voikoSulkea() ) event.consume();
	        });
	        
	        Paivakirja paivakirja = new Paivakirja();
	        tapahtumaCtrl.setPaivakirja(paivakirja);
	        yvCtrl.setPaivakirja(paivakirja);
	        paivakirjaCtrl.setPaivakirja(paivakirja);
	        
	        
	        primaryStage.show();

	        if (!paivakirjaCtrl.avaa()) Platform.exit();
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Pääohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
