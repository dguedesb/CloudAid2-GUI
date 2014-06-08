package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import models.CSAData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SendRequestController implements Initializable{

	@FXML
	Button yesbutton;
	
	@FXML
	Button nobutton;
	@FXML
	AnchorPane anchor;
	
	private MainPanelController father;
	
	private CSAData csa;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		yesbutton.getStylesheets().add("/css/buttonscss.css");
		nobutton.getStylesheets().add("/css/buttonscss.css");
	}
	
	public void yesMethod() throws Exception {
		Gson gson = new Gson();

//		String directory = "./JSONRequests";
		String directory = "./[Server]JSONRequests";
		String directoryb = "./[Client]JSONRequests";
		String json = gson.toJson(csa);
		System.out.println(json);
		
		try {
			FileUtils.writeStringToFile(new File(directory + "/Request"+"-"+System.nanoTime()+".json"), json);
			FileUtils.writeStringToFile(new File(directoryb + "/Request"+"-"+System.nanoTime()+".json"), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		father.AnswerServerRequests(anchor.getScene(),true,0);
	}
	
	public void noMethod() throws Exception {
		father.AnswerServerRequests(anchor.getScene(),false,0);
	}
	
	public void setFather(MainPanelController f) {
		father = f;
	}
	
	public void setCSA(CSAData s) {
		csa = s;
	}

}
