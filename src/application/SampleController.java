package application;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import bean.City;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboGiorno;

    @FXML
    private Button btnCitta;

    @FXML
    private Button btnMax;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCitta(ActionEvent event) {
    	txtResult.clear();
    	String giorno = comboGiorno.getValue();
    	
    	if(comboGiorno.getValue()==null){
    		txtResult.appendText("Seleziona un giorno!\n");
    		return;
    	}
     
    	int numGiorno = m.getNumeroGioro(giorno);
    	List<City> all = m.getCity(numGiorno);
    	txtResult.appendText("Le 20 citta sono : \n");
    	
    	if(all.isEmpty()){
    		txtResult.appendText("Non sono presenti citta!\n");
    		return;
    	}
    	
    	for(City c : all){
    	    txtResult.appendText(c + " \n");
    	   }
    	txtResult.appendText("\n");
    	
    	List<String> elenco = m.getElenco(numGiorno);
    	txtResult.appendText("Elenco brani piu ascoltati: \n");
    	for(String s : elenco){
    	    txtResult.appendText(s+" \n");
    	}
    }

    @FXML
    void doMax(ActionEvent event) {
       String giorno = comboGiorno.getValue();
    	
    	if(comboGiorno.getValue()==null){
    		txtResult.appendText("Seleziona un giorno!\n");
    		return;
    	}
    	int numGiorno = m.getNumeroGioro(giorno);
    	
    	m.buildGraph(numGiorno);
    	
    	Map<Integer, City> mappaTreCitta =m.getMappe(); 
    	txtResult.appendText(mappaTreCitta.toString());

    }

    
    
    @FXML
    void initialize() {
        assert comboGiorno != null : "fx:id=\"comboGiorno\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCitta != null : "fx:id=\"btnCitta\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnMax != null : "fx:id=\"btnMax\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

        comboGiorno.getItems().addAll(m.getGiorniCombo());
    }
}
