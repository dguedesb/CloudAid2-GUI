package controller;

import java.net.URL;
import java.util.ResourceBundle;

import models.Requirement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class EditORDeleteReqController implements Initializable{

	@FXML
	Button editbtn;
	
	@FXML
	Button deletebtn;
	@FXML
	AnchorPane anchorp;
	
	private MainPanelController father;
	private Requirement req;
	private boolean aggregation;
	private String type;
	private CheckBox source;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		deletebtn.getStylesheets().add("/css/buttonscss.css");
		editbtn.getStylesheets().add("/css/buttonscss.css");
	}
	
	public void setAggregation(boolean s) {
		aggregation = s;
	}
	
	public void editReq(){
		source.setSelected(true);
		father.editReq(anchorp.getScene(),req,aggregation,type);
	}
	
	public void deleteReq(){
		source.setSelected(false);
		father.deleteReq(anchorp.getScene(),req,aggregation,type);
	}
	
	public void setType (String t, CheckBox sourceCheckB) {
		type =t;
		source = sourceCheckB;
	}
	
	public void setFather(MainPanelController f){
		father = f;
	}
	
	public void setRequirement(Requirement r) {
		req = r;
	}

}
