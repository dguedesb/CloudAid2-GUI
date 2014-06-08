package controller;

import java.net.URL;
import java.util.ResourceBundle;

import models.Criterion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class EditORDeleteCritController implements Initializable{

	@FXML
	Button editbtn;
	
	@FXML
	Button deletebtn;
	@FXML
	AnchorPane anchorp;
	
	private MainPanelController father;
	private Criterion crit;
	private String type;
	private CheckBox source;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		deletebtn.getStylesheets().add("/css/buttonscss.css");
		editbtn.getStylesheets().add("/css/buttonscss.css");
	}
	
	public void editCrit(){
		source.setSelected(true);
		father.editCrit(anchorp.getScene(),crit,type);
	}
	
	public void deleteCrit(){
		source.setSelected(false);
		father.deleteCrit(anchorp.getScene(),crit,type);
	}
	
	public void setType (String t, CheckBox sourceCheckB) {
		type =t;
		source = sourceCheckB;
	}
	
	public void setFather(MainPanelController f){
		father = f;
	}
	
	public void setCriterion(Criterion c) {
		crit = c;
	}

}
