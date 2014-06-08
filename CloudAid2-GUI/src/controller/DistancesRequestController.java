package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.ServiceTemplate;
import secondaryDataModels.ConceptDistance;
import secondaryDataModels.DistancesContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;



public class DistancesRequestController implements Initializable{
	private ProcessSTDataRequestController father;
	private DistancesContainer distances;
	private ServiceTemplate ST;
	@FXML
	Label labelst;
	
	@FXML
	Label criteriontype;
	
	@FXML
	Button donebutton;
	
	@FXML
	AnchorPane anchor;
	@FXML
	AnchorPane anchorboss;
	
	private ArrayList<Slider> sliders;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		donebutton.getStylesheets().add("/css/buttonscss.css");
		sliders = new ArrayList<Slider>();
		
	}

	public void setFather(ProcessSTDataRequestController f) {
		// TODO Auto-generated method stub
		father = f;
	}

	public void setVariables(DistancesContainer dists) {
		// TODO Auto-generated method stub
		distances = dists;
		int y=0;
		for(ConceptDistance c : distances.getConcepts()) {
			Label concepts = new Label();
			concepts.setText(distances.getPrefered()+ "  --   " + c.getName());
			concepts.setFont(new Font("Comic Sans MS Bold", 12));
			concepts.setLayoutX(215);
			concepts.setLayoutY(y);
			
			Slider s = new Slider();
			s.setMax(10);
			s.setBlockIncrement(0.1);
			s.setPrefWidth(320);
			s.setLayoutX(100);
			s.setLayoutY(y+45);
			sliders.add(s);
			
			Label identical = new Label();
			identical.setText("Identical");
			identical.setFont(new Font("Comic Sans MS Bold", 12));
			identical.setLayoutX(5);
			identical.setLayoutY(y+45);
			
			Label opposite = new Label();
			opposite.setText("Opposite");
			opposite.setFont(new Font("Comic Sans MS Bold", 12));
			opposite.setLayoutX(490);
			opposite.setLayoutY(y+38);
			
			Separator sep = new Separator();
			sep.setPrefWidth(500);
			sep.setLayoutX(32);
			sep.setLayoutY(y+85);
			
			y+=120;
			
			anchor.getChildren().addAll(s,identical,opposite,sep,concepts);
		}
		
		criteriontype.setText("Criterion:  "+distances.getCriterionType());
		
	}

	public void setServiceTemplate(ServiceTemplate sTName) {
		// TODO Auto-generated method stub
		ST=sTName;
		labelst.setText("Concepts Distances:  "+ST.getName());
	}
	
	public void finish() {
		int i=0;
		for(Slider s : sliders) {
			distances.getConcepts().get(i).setDistVal(s.getValue());
			i++;
		}
		
		father.closeConceptsPopup(anchorboss.getScene(), distances);
	}

}
