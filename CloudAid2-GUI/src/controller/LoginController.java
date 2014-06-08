package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LoginController implements Initializable {
	
	private Main app;
	
	@FXML
	private Button loginbtn;
	
	@FXML
	private Button registerbtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loginbtn.getStylesheets().add("/css/buttonscss.css");
		registerbtn.getStylesheets().add("/css/buttonscss.css");
	}
	
	
	
	public void setApp(Main application) {
		this.app = application;
	}
	
	public void checkLogin(ActionEvent e) throws Exception {
		System.out.println("Checking log in!");
		app.SuccessfulLogin();
	}
	

}
