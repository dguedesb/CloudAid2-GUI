package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.Criterion;
import models.ServiceTemplate;

import org.apache.commons.io.FileUtils;

import secondaryDataModels.DistancesContainer;
import secondaryDataModels.PricingVariables;
import application.Main;

import com.google.gson.Gson;

import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProcessSTDataRequestController implements Initializable{
	
	@FXML
	ProgressBar progressbar;
	@FXML
	AnchorPane anchor;
	@FXML
	Label statusval;
	@FXML 
	Label statuslabel;


	private MainPanelController father;
	private ServiceTemplate STName;
	private int qualcrit=0;
	private int currentSTNum;
	Timeline task;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		    
		    progressbar.getStylesheets().add(
		        getClass().getResource(
		            "/css/progressbar.css"
		        ).toExternalForm()
		    );
	}
	
	protected void setVarResponse(PricingVariables ans) throws Exception {
		//process PricingVariables
		
		Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);
        
		ArrayList<Object> obs = loadFXML("VariablesRequest.fxml");
		Scene myDialogScene = (Scene) obs.get(0);
		VariablesRequestController cont = (VariablesRequestController) obs.get(1);
		cont.setFather(this);
		cont.setVariables(ans);
		cont.setServiceTemplate(STName);
		myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
	    
	}
	
	protected void setConceptResponse(DistancesContainer dists) throws Exception {
		Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);
        
		ArrayList<Object> obs = loadFXML("DistancesRequest.fxml");
		Scene myDialogScene = (Scene) obs.get(0);
		DistancesRequestController cont = (DistancesRequestController) obs.get(1);
		cont.setFather(this);
		cont.setVariables(dists);
		cont.setServiceTemplate(STName);
		myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
	}
	
	public void closeVariablesPopup(Scene s, PricingVariables vars) {
		Stage a = (Stage) s.getWindow();
		a.close();
		Gson gson = new Gson();
		//SEND VARS BACK TO THE SERVER
		String jsonvars = gson.toJson(vars);
		
		String directoryb = "./[Server]JSONPricingVariables";
		
		try {
			FileUtils.writeStringToFile(new File(directoryb + "/Vars"+"-"+System.nanoTime()+".json"), jsonvars);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("QUALITATIVE CRITERIA SIZE: " + qualcrit);
		if(qualcrit > 0)
			listConceptDistResponse();
		else
			father.closeProcessSTController(anchor.getScene(), currentSTNum);
	}
	
	private int countQualCrit() {
		int n = 0;
		if(STName.getCriteria() != null)
			for(Criterion c : STName.getCriteria())
				if(c.getType() == 1)
					n++;
		return n;
	}
	
	private void listConceptDistResponse() {
		// TODO Auto-generated method stub
		statusval.setText("Waiting "+STName.getName()+"'s concept distances.");
		statusval.setFont(new Font("Comic Sans MS Bold", 12));

		Task<DistancesContainer> task = new Task<DistancesContainer>() {
			@SuppressWarnings({ "unused", "rawtypes" })
			@Override
			public DistancesContainer call() {
				
				Gson gson = new Gson();
				Path faxFolder = Paths.get("./[Client]JSONRequests-ConceptDistances/");
				WatchService watchService = null;
				try {
					watchService = FileSystems.getDefault().newWatchService();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					faxFolder.register(watchService,StandardWatchEventKinds.ENTRY_CREATE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				boolean valid = true;
				DistancesContainer varsb = null;
				do {
					WatchKey watchKey = null;
					try {
						watchKey = watchService.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (WatchEvent<?> event : watchKey.pollEvents()) {
						WatchEvent.Kind kind = event.kind();

						if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
							String fileName = event.context().toString();
							System.out.println("File Created:" + fileName);
							try {
								
								varsb = gson.fromJson(FileUtils.readFileToString(new File("./[Client]JSONRequests-ConceptDistances/" + fileName)), DistancesContainer.class);
								
								System.out.println(varsb.getConcepts().size());
								valid=false;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} while (valid);
				
				return varsb;
			}
		};
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
					DistancesContainer dist = (DistancesContainer)event.getSource().getValue();
					qualcrit--;
					try {
						setConceptResponse(dist);
						statusval.setText("Please, insert data.");
						statusval.setFont(new Font("Comic Sans MS Bold", 12));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

//		progressbar.progressProperty().bind(task.progressProperty());

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		
	}

	private void listResponse() {
		// TODO Auto-generated method stub
		
		statusval.setText("Waiting "+STName.getName()+"'s pricing variables.");
		statusval.setFont(new Font("Comic Sans MS Bold", 12));
		progressbar.setProgress(0);
		Task<PricingVariables> task = new Task<PricingVariables>() {
			
			@SuppressWarnings({ "rawtypes", "unused" })
			@Override
			public PricingVariables call() {
				
				Gson gson = new Gson();
				Path faxFolder = Paths.get("./[Client]JSONRequests-Variables/");
				WatchService watchService = null;
				try {
					watchService = FileSystems.getDefault().newWatchService();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					faxFolder.register(watchService,StandardWatchEventKinds.ENTRY_CREATE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				boolean valid = true;
				PricingVariables vars = null;
				do {
					WatchKey watchKey = null;
					try {
						watchKey = watchService.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (WatchEvent<?> event : watchKey.pollEvents()) {
						WatchEvent.Kind kind = event.kind();

						if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
							String fileName = event.context().toString();
							System.out.println("File Created:" + fileName);
							try {
								String s = FileUtils.readFileToString(new File("./[Client]JSONRequests-Variables/" + fileName));
								System.out.println(s);
								vars = gson.fromJson(FileUtils.readFileToString(new File("./[Client]JSONRequests-Variables/" + fileName)), PricingVariables.class);
								
								System.out.println(vars.getVars().size());
								valid=false;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} while (valid);
				
				
				return vars;
			}
		};
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
					PricingVariables vars = (PricingVariables)event.getSource().getValue();
					try {
						setVarResponse(vars);
						if(qualcrit == 0)
							progressbar.setProgress(0.75);
						else
							progressbar.setProgress(20);
						statusval.setText("Please, insert data.");
						statusval.setFont(new Font("Comic Sans MS Bold", 12));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

//		progressbar.progressProperty().bind(task.progressProperty());

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	public void setFather(MainPanelController f) {
		father = f;
	}
	
	private ArrayList<Object> loadFXML(String fxml) throws Exception {
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
        if(fxml.equals("VariablesRequest.fxml"))
        	scene = new Scene(page, 650, 500);
        else if(fxml.equals("DistancesRequest.fxml"))
        	scene = new Scene(page, 650, 500);
        
        ArrayList<Object> returns = new ArrayList<Object>();
        returns.add(scene);
        returns.add((Initializable) loader.getController());
        
        return returns ;
    }

	public void setSTName(ServiceTemplate st) {
		// TODO Auto-generated method stub
		STName=st;
	}
	public void closeConceptsPopup(Scene scene, DistancesContainer distances) {
		// TODO Auto-generated method stub
		Stage a = (Stage) scene.getWindow();
		a.close();
		Gson gson = new Gson();
		//SEND VARS BACK TO THE SERVER
		String jsonvars = gson.toJson(distances);
		
		String directoryb = "./[Server]JSONConceptDistances";
		
		try {
			FileUtils.writeStringToFile(new File(directoryb + "/Concepts"+"-"+System.nanoTime()+".json"), jsonvars);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(qualcrit > 0){
			listConceptDistResponse();

			progressbar.setProgress(1-(qualcrit/countQualCrit())*100);
		}
		else{
			
			father.closeProcessSTController(anchor.getScene(),currentSTNum);
//			progressbar.setProgress(95);
//			
//			statusval.setText("Awaiting results..!");
//			statusval.setFont(new Font("Comic Sans MS Bold", 12));
			
//			listenToResults();
		}
	}

	public void setSTNum(int i) {
		// TODO Auto-generated method stub
		currentSTNum=i;
	}

	public void setQualCritNumber(int countQualCrit) {
		// TODO Auto-generated method stub
		qualcrit=countQualCrit;
		
	}
	
	public void start() {
		listResponse();
	}

}
