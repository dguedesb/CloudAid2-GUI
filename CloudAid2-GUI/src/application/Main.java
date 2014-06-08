package application;
	
import java.io.InputStream;

import models.CSAData;
import controller.LoginController;
import controller.MainPanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class Main extends Application {
	private Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		stage.setResizable(false);
		LoginController mc = (LoginController) replaceSceneContent("Login.fxml");
		mc.setApp(this);
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void SuccessfulLogin() throws Exception {
		gotoMainPanel();
	}
	
	private void gotoMainPanel() throws Exception {
		MainPanelController pc = (MainPanelController) replaceSceneContent("MainPanel.fxml");
		pc.setApp(this);
	}
	
	public void loadNewPanel(CSAData s) throws Exception {
		MainPanelController pc = (MainPanelController) replaceSceneContent("MainPanel.fxml");
		pc.setApp(this);
		pc.loadNewCSA(s);
	}
	
	public void loadNewEmptyPanel() throws Exception {
		MainPanelController pc = (MainPanelController) replaceSceneContent("MainPanel.fxml");
		pc.setApp(this);
	}
	
	public void logout() throws Exception {
		gotoLogin();
	}


	private void gotoLogin() throws Exception {
		LoginController mc = (LoginController) replaceSceneContent("Login.fxml");
		mc.setApp(this);
	}


	private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene =null;
        if(fxml.equals("Login.fxml"))
        	scene = new Scene(page, 800, 600);
        else
        	scene = new Scene(page,1024,700);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
