package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.ServiceTemplate;
import secondaryDataModels.PriceVariable;
import secondaryDataModels.PricingVariables;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class VariablesRequestController implements Initializable{

	
	@FXML
	AnchorPane anpane;
	@FXML
	Label stname;
	@FXML
	Button donebutton;
	@FXML
	ScrollPane spane;
	@FXML
	private AnchorPane anchor;
	
	private ProcessSTDataRequestController father;
	private PricingVariables variables;
	private ServiceTemplate ST;
	private ArrayList<TextField> values;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		donebutton.getStylesheets().add("/css/buttonscss.css");
		values = new ArrayList<TextField>();
		donebutton.setLayoutX(donebutton.getLayoutX()+150);
	}

	public void setFather(ProcessSTDataRequestController f) {
		// TODO Auto-generated method stub
		father = f;
	}
	
	public void setServiceTemplate(ServiceTemplate d) {
		ST=d;
		System.out.println(ST.getName());
		stname.setText(ST.getName());
	}
	public void setVariables(PricingVariables vars) {
		// TODO Auto-generated method stub
		variables = vars;
		
		int variablenamey=20;
		for(PriceVariable var : variables.getVars())
		{
			System.out.println(var.getName());
			Label variablename = new Label();
			variablename.setText("Variable Name: " + var.getName().replaceAll("TIME\\d+.*", ""));
			variablename.setFont(new Font("Comic Sans MS Bold", 12));
			variablename.setLayoutX(37);
			variablename.setLayoutY(variablenamey);
			
			TextField variablevalue = new TextField();
			variablevalue.setLayoutX(400);
			variablevalue.setLayoutY(variablenamey-5);
			values.add(variablevalue);
			
			Label details = new Label();
			details.setText("Details:\n"+var.getDetails());
			details.setFont(new Font("Comic Sans MS Bold", 12));
			details.setLayoutX(37);
			details.setLayoutY(variablenamey+35);
			
			Separator s = new Separator();
			s.setPrefWidth(500);
			s.setLayoutX(32);
			s.setLayoutY(variablenamey+90);
			
			variablenamey+=120;
			
			anpane.getChildren().addAll(variablename,variablevalue,details,s);
		}
	}
	
	public void finish() {
		int i=0;
		boolean flag = false;
		for(TextField val : values) {
			if(!val.getText().equals("")){
				try{
					variables.getVars().get(i).setVal(Double.parseDouble(val.getText()));
				}catch(NumberFormatException e) {
					flag = true;
					break;
				}
			}
			i++;
		}
		
		if(!flag) {
			father.closeVariablesPopup(anchor.getScene(), variables);
		}else{
			System.out.println("Something is wrong with your parameters..");
		}
	}

}
