package controller;

import guiModels.AgSolTView;

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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import secondaryDataModels.AggregatedSolution;
import secondaryDataModels.AggregationComponent;
import secondaryDataModels.AggregationSolutions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import models.CSAData;
import models.Criterion;
import models.Requirement;
import models.ServiceTemplate;
import models.CLOUDEnumMiddleMan;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


public class MainPanelController implements Initializable {

	private Main app;
	
	@FXML
	private Button logoutbtn;
	@FXML 
	private Button clearformbtn;
	@FXML
	private Button helpbtn1;
	@FXML 
	private Button searchbtn;
	@FXML
	private Button newST;
	@FXML
	private Button loadbutton;
	@FXML
	private TabPane tabpane;
	@FXML
	private Tab resultstab;
	@FXML
	private Tab searchsheettab;
	@FXML
	private AnchorPane ssanchorpane;
	@FXML
	TextField searchsheetname;
	@FXML
	ComboBox<String> historycombobox;
	@FXML
	ComboBox<String> methodcombobox;
	@FXML
	TableView<AgSolTView> tableview;
	@FXML
	AnchorPane anchorres;
	@FXML
	AnchorPane aggresanchorboss;
	CLOUDEnumMiddleMan mm;
	
	private Stage wresultspopup;
	
	private ArrayList<ArrayList<CheckBox>> reqsChecks;
	private ArrayList<ArrayList<CheckBox>> critsChecks;
	private ArrayList<CheckBox> agreqs;
	
	/****************************************/
	
	private CSAData data ;
	private ArrayList<ServiceTemplate> serviceTemplates ;
	private ArrayList<Requirement> reqs ;
	private ObservableList<AgSolTView> currentTVdata=null;
	// decision Method codes
	public static final int SAW = 0;
	public static final int ELECTRE = 1;
	public static final int PROMETHEE = 2;
	public static final int SMAA2 = 3;
	
	/*****************************************/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reqsChecks = new ArrayList<ArrayList<CheckBox>>();
		critsChecks = new ArrayList<ArrayList<CheckBox>>();
		agreqs = new ArrayList<CheckBox>();
		resultstab.setDisable(true);

		data = new CSAData();
		serviceTemplates = new ArrayList<ServiceTemplate>();
		reqs = new ArrayList<Requirement>();
		data.setServiceTemplates(new ArrayList<ServiceTemplate>());
		
		methodcombobox.getItems().addAll(
			    "PROMETHEE I",
			    "ELECTRE III",
			    "SMAA-2",
			    "SAW"
			);
		methodcombobox.setValue("PROMETHEE I");

		init();
	}
	
	private static int stx=320;
	private static int sty=40;
	private static String stTitle = "Service Template";
	private static int ID=1;
	private void init() {
		mm=new CLOUDEnumMiddleMan();
		resultstab.setDisable(true);
		
		setCSS();
		setCloudProperties();
		setToolTips();
//		setNewST();
		setHistoryValues();
	}
	
	private void setHistoryValues() {
		// TODO Auto-generated method stub
		File folder = new File("./[Client]JSONRequests");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if(!historycombobox.getItems().contains(listOfFiles[i].getName()))
					historycombobox.getItems().add(listOfFiles[i].getName());
			} 
		}
	}

	public void addNewST() {
		System.out.println("Adding new Service Template");
		setNewST(true);
	}
	
	public void clearForm() {
		System.out.println("Clear button pressed!");
		stx=320;
		sty=40;
		ID=1;
		try {
			app.loadNewEmptyPanel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load() throws JsonSyntaxException, IOException{
		
		String val = historycombobox.getValue();
		Gson gson = new Gson();
		CSAData   data = gson.fromJson(FileUtils.readFileToString(new File("./[Client]JSONRequests/" + val)), CSAData.class);
		if(!data.getName().equals(""))
			searchsheetname.setText(data.getName());
		
		
		stx=320;
		sty=40;
		ID=1;
		try {
			app.loadNewPanel(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void search() throws Exception {
		System.out.println("Search button pressed!");
		data.setMethod(getMethod());

		data.setServiceTemplates(serviceTemplates);
		data.setRequirements(reqs);
		
		if(!searchsheetname.getText().equals(""))
			data.setName(searchsheetname.getText());
		else
			data.setName("Request"+"-"+System.nanoTime());//dafault name for CSA
		
		searchsheettab.setDisable(true);
		searchsheettab.getContent().setDisable(true);
		sendRequest();
	}
	
	
	private void sendRequest() throws Exception {
		Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("SendRequest.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        SendRequestController cont = (SendRequestController) res.get(1);
        cont.setFather(this);
        cont.setCSA(this.data);
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
	}

	@SuppressWarnings("unused")
	private void setNewST(boolean newST) {
		// TODO Auto-generated method stub
		TitledPane st = new TitledPane();
		if(serviceTemplates.size() >=1){
			searchbtn.setDisable(false);
			
			for(CheckBox c: agreqs){
				c.setDisable(false);
			}
		}
		st.setText(stTitle + " " +ID);
		st.setFont(new Font("Comic Sans MS Bold", 12));
		st.setCollapsible(false);
		st.setLayoutX(stx);
		st.setLayoutY(sty);
//		st.setAlignment(Pos.CENTER);
		st.setPrefWidth(120);
		System.out.println(stx);
		Pane t = new Pane();

		int boxy=25;
		int boxx=20;
		List<String> l = new ArrayList<String>(mm.getPrettyPrint().keySet());
		Collections.sort(l);
		
		ArrayList<CheckBox> reqs = new ArrayList<CheckBox>();
		ArrayList<CheckBox> crits = new ArrayList<CheckBox>();
		for(String key : l) {
				CheckBox R = new CheckBox();
				R.setText("Req.");
				R.setFont(new Font("Comic Sans MS Bold", 12));
				R.setLayoutX(30);
				R.setId(ID + "_" +key);
				R.setLayoutX(boxx);
				R.setLayoutY(boxy);
				
				CheckBox C = new CheckBox();
				C.setText("Crit.");
				C.setFont(new Font("Comic Sans MS Bold", 12));
				C.setId(ID + "_"+key);
				C.setLayoutX(boxx+60);
				C.setLayoutY(boxy);
				
				reqs.add(R);
				crits.add(C);
				if(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(key)) != null) {
					R.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							CheckBox source = (CheckBox) event.getSource();
							Stage s;
							
							if(source.isSelected()) {
								try {
									QualReqPopup( source.getId(),false);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								try {
									deleteOReditReq(source.getId(),source);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}});
					
					C.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							CheckBox source = (CheckBox) event.getSource();
							Stage s;
							
							if(source.isSelected()) {
								try {
									QualCritPopup( source.getId());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								try {
									deleteOReditCrit(source.getId(),source);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}});
					
					
				}
				else {
					R.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							CheckBox source = (CheckBox) event.getSource();
							Stage s;
							
							if(source.isSelected()) {
								try {
									QuantReqPopup( source.getId(),false);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								try {
									deleteOReditReq(source.getId(),source);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}});
					
					
					C.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							CheckBox source = (CheckBox) event.getSource();
							Stage s;
							
							if(source.isSelected()) {
								try {
									QuantCritPopup( source.getId());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								try {
									deleteOReditCrit(source.getId(),source);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}});
				}
				
				t.getChildren().addAll(R,C);
				
				boxy+=50;
			}
		
		reqsChecks.add(reqs);
		critsChecks.add(crits);
		st.getStylesheets().add("/css/titledpane.css");
		st.setContent(t);

		ssanchorpane.getChildren().add(st);
		stx+=170;
		ID++;
		
		if(newST){
			ServiceTemplate st_j = new ServiceTemplate();
			st_j.setCriteria(new ArrayList<Criterion>());
			st_j.setName(st.getText());
			st_j.setRequirements(new ArrayList<Requirement>());
			serviceTemplates.add(st_j);
		}
	}

	protected void deleteOReditReq(String id2,CheckBox source) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Deleting Requirement: " + id2);
		String[] data = id2.split("_");
		Requirement r=null;
		boolean agg = false;
		if(data[0].equalsIgnoreCase("agreq")) {
			for(Requirement req : reqs) {
				if(req.getCloudtype().equalsIgnoreCase(data[1]) || req.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))    ||  req.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
					r=req;
					agg=true;
					break;
				}	
			}
		}
		else {
			for(Requirement req : serviceTemplates.get(Integer.parseInt(data[0])-1).getRequirements()) {
				if(req.getCloudtype().equalsIgnoreCase(data[1]) || req.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  req.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
					r=req;
					break;
				}	
			}
		}
		
		editORdeletePopup(r,agg,id2,source);
		
	}
	
	private void editORdeletePopup(Requirement r,boolean agg,String source,CheckBox sourceCheckB) throws Exception {
		// TODO Auto-generated method stub
		Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("EditORdeleteReq.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        EditORDeleteReqController cont = (EditORDeleteReqController) res.get(1);
        cont.setFather(this);
        cont.setRequirement(r);
        cont.setAggregation(agg);
        cont.setType(source,sourceCheckB);
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
	}

	protected void deleteOReditCrit(String id2,CheckBox source) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Deleting Criteria: " + id2);
		String[] data = id2.split("_");
		Criterion c=null;
		for(Criterion crit : serviceTemplates.get(Integer.parseInt(data[0])-1).getCriteria()) {
			if(crit.getName().equalsIgnoreCase(data[1]) || crit.getName().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  crit.getName().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
				c=crit;
				break;
			}	
		}
		
		if(c!=null)
			editORdeleteCritPopup(c,id2,source);
		else
			System.out.println("Null....?");
	}
	
	

	private void editORdeleteCritPopup(Criterion c, String id2, CheckBox source) throws Exception {
		// TODO Auto-generated method stub
		Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("EditORdeleteCrit.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        EditORDeleteCritController cont = (EditORDeleteCritController) res.get(1);
        cont.setFather(this);
        cont.setCriterion(c);
        cont.setType(id2,source);
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
	}

	private void QuantReqPopup(String type,boolean aggregationReq) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QuantReqPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QuantReqController cont = (QuantReqController) res.get(1);
        cont.setFather(this);
        cont.setType(type);
        if(aggregationReq)
        	cont.setAggregation(true);
        
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
    }
	
	private void QuantReqPopup(String type,boolean aggregationReq,Requirement r) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QuantReqPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QuantReqController cont = (QuantReqController) res.get(1);
        cont.setFather(this);
        cont.setType(type);
        if(aggregationReq)
        	cont.setAggregation(true);
       
        cont.loadReq(r);
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
        deleteReq(null, r, aggregationReq, type);
    }
	
	private void QualReqPopup(String type,boolean aggregationReq) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QualReqPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QualReqController cont = (QualReqController) res.get(1);
        cont.setFather(this);
        cont.setType(type);
        if(aggregationReq){
        	cont.setAggregation(true);
        	cont.setMaxAgg(serviceTemplates.size()-1);
        }
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
    }
	
	private void QualReqPopup(String type,boolean aggregationReq,Requirement r) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QualReqPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QualReqController cont = (QualReqController) res.get(1);
        
        cont.loadReq(r);
        cont.setFather(this);
        cont.setType(type);
        if(aggregationReq) {
        	cont.setAggregation(true);
        	cont.setMaxAgg(serviceTemplates.size()-1);
        }
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
        
        deleteReq(null, r, aggregationReq, type);
    }
	
	private void QuantCritPopup(String type, Criterion crit) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QuantCritPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QuantCritController cont = (QuantCritController) res.get(1);
        
        cont.loadCrit(crit);
        cont.setFather(this);
        cont.setType(type);
        cont.setMethod(getMethod());
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
        
        deleteCrit(null,crit,type);
    }
	
	private void QualCritPopup(String type,Criterion crit) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QualCritPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QualCritController cont = (QualCritController) res.get(1);
        
        cont.loadCrit(crit);
        cont.setFather(this);
        cont.setType(type);
        cont.setMethod(getMethod());
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
        
        deleteCrit(null,crit,type);
    }
	
	private void QuantCritPopup(String type) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QuantCritPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QuantCritController cont = (QuantCritController) res.get(1);
        cont.setFather(this);
        cont.setType(type);
        cont.setMethod(getMethod());
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
    }
	
	private void QualCritPopup(String type) throws Exception {
        Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);

        ArrayList<Object> res = loadFXML("QualCritPopup.fxml");
        Scene myDialogScene = (Scene) res.get(0);
        QualCritController cont = (QualCritController) res.get(1);
        cont.setFather(this);
        cont.setType(type);
        cont.setMethod(getMethod());
        myDialog.setResizable(false);
        myDialog.setScene(myDialogScene);
        myDialog.show();
    }
	private int getMethod() {
		if(methodcombobox.getValue().contains("PROMETHEE"))
			return PROMETHEE;
		else if(methodcombobox.getValue().contains("ELECTRE")) 
			return ELECTRE;
		else if(methodcombobox.getValue().contains("SMAA")) 
			return SMAA2;
		else 
			return SAW;
	}
	private void setCloudProperties() {
		Image img = new Image(getClass().getResourceAsStream("/images/helpbtnimage.png"),20,20,true,true);

		helpbtn1.setShape(new Circle());
		helpbtn1.setGraphic(new ImageView(img));
		
		
		int buttony=40;
		int recy=45;
		int texty=45;
		int separatory=70;
		
		

		List<String> l = new ArrayList<String>(mm.getPrettyPrint().keySet());
		Collections.sort(l);
		for(String key : l) {
			Rectangle rec = new Rectangle();
//			rec.setArcHeight(20);
//			rec.setArcWidth(20);
			rec.setHeight(19);
			rec.setLayoutX(40);
			rec.setLayoutY(recy+50);//50
			rec.setStroke(Color.BLACK);
			rec.setFill(Color.TRANSPARENT);
			rec.setStrokeType(StrokeType.INSIDE);
			rec.setWidth(200);
			rec.toBack();
			
			CheckBox ag = new CheckBox();
			ag.setLayoutX(250);
			ag.setLayoutY(recy+50);
			ag.setText("Ag.");
			ag.setFont(new Font("Comic Sans MS Bold", 12));
			ag.setId("AgReq" + "_" + key);
			ag.setDisable(true);
			if(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(key)) != null) {
				ag.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						CheckBox source = (CheckBox) event.getSource();
						if(source.isSelected()) {
							try {
								QualReqPopup(source.getId(),true);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							try {
								deleteOReditReq(source.getId(),source);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}});
			}
			else {
				ag.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						CheckBox source = (CheckBox) event.getSource();
						if(source.isSelected()) {
							try {
								QuantReqPopup(source.getId(),true);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							try {
								deleteOReditReq(source.getId(),source);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}});
			}
			
			agreqs.add(ag);
			
			Button help = new Button("", new ImageView(img));
			help.setShape(new Circle());
			help.setLayoutX(5);
			help.setLayoutY(buttony+50);
			help.setMnemonicParsing(false);
			//add tooltip
			
			
			Label text = new Label();
			text.setText(key);
			text.setLayoutX(66);
			text.setLayoutY(texty+50);
			text.setFont(Font.font("Comic Sans MS Bold", 12));
			
			Separator s = new Separator();
			s.setLayoutX(36);
			s.setLayoutY(separatory+50);
			s.setPrefWidth(240);
			s.getStylesheets().add("/css/separator.css");
			
			recy=recy+50;
			texty=texty+50;
			separatory=separatory+50;
			buttony+=50;
			ssanchorpane.getChildren().addAll(rec,help,text,s,ag);
		}
	}
		


	private void setToolTips() {
		helpbtn1.setTooltip(new Tooltip("Deletes the information currently present on the application."));
	}


	private void setCSS() {
		tabpane.getStylesheets().add("/css/tabcss.css");
		logoutbtn.getStylesheets().add("/css/buttonscss.css");
		clearformbtn.getStylesheets().add("/css/buttonscss.css");
		searchbtn.getStylesheets().add("/css/buttonscss.css");
		newST.getStylesheets().add("/css/buttonscss.css");
		loadbutton.getStylesheets().add("/css/buttonscss.css");
	}


	public void setApp(Main application) {
		this.app = application;
	}
	
	public void logout(ActionEvent e) throws Exception {
		System.out.println("Logging out!");
		stx=320;
		app.logout();
		ID=1;
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
	        if(fxml.equals("Login.fxml"))
	        	scene = new Scene(page, 800, 600);
	        else if (fxml.equals("QuantReqPopup.fxml")) {
	        	scene = new Scene(page, 400, 170);
	        }
	        else if (fxml.equals("QualReqPopup.fxml")) {
	        	scene = new Scene(page, 350, 180);
	        }
	        else if (fxml.equals("QuantCritPopup.fxml")) {
	        	scene = new Scene(page, 410, 240);
	        }
	        else if (fxml.equals("QualCritPopup.fxml")) {
	        	scene = new Scene(page, 415, 270);
	        }
	        else if (fxml.equals("EditORdeleteReq.fxml")) {
	        	scene = new Scene(page, 240, 50);
	        }
	        else if (fxml.equals("EditORdeleteCrit.fxml")) {
	        	scene = new Scene(page, 240, 50);
	        }
	        else if (fxml.equals("SendRequest.fxml")) {
	        	scene = new Scene(page, 350, 80);
	        }
	        else if (fxml.equals("ProcessSTRequests.fxml")) {
	        	scene = new Scene(page, 400, 80);
	        }
	        else
	        	scene = new Scene(page,1024,920);
	        
	        ArrayList<Object> returns = new ArrayList<Object>();
	        returns.add(scene);
	        returns.add((Initializable) loader.getController());
	        
	        return returns ;
	    }

		public void closeQuantReqPopup(Scene s, Requirement req, int i) {
			// TODO Auto-generated method stub
			Stage a = (Stage) s.getWindow();
			a.close();
			if(i >=0)
				serviceTemplates.get(i).addReq(req);
			else
				reqs.add(req);
		}
		
		public void closeQualReqPopup(Scene s, Requirement req, int i) {
			// TODO Auto-generated method stub
			Stage a = (Stage) s.getWindow();
			a.close();
			if(i >=0)
				serviceTemplates.get(i).addReq(req);
			else
				reqs.add(req);
		}
		
		public void closeQuantCritPopup(Scene s, Criterion crit, int i) {
			// TODO Auto-generated method stub
			serviceTemplates.get(i).addCrit(crit);
			Stage a = (Stage) s.getWindow();
			a.close();
		}
		
		public void closeQualCritPopup(Scene s, Criterion crit, int i) {
			// TODO Auto-generated method stub
			serviceTemplates.get(i).addCrit(crit);
			Stage a = (Stage) s.getWindow();
			a.close();
		}

		public void editReq(Scene scene, Requirement req, boolean aggregation, String type) {
			// TODO Auto-generated method stub
			
			String[] data = type.split("_");
			
			Requirement r=null;
			if(aggregation) {
				for(Requirement reqq : reqs) {
					if(reqq.getCloudtype().equalsIgnoreCase(data[1]) || reqq.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))    ||  reqq.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
						r=reqq;
						break;
					}	
				}
			}
			else {
				for(Requirement reqq : serviceTemplates.get(Integer.parseInt(data[0])-1).getRequirements()) {
					if(reqq.getCloudtype().equalsIgnoreCase(data[1]) || reqq.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  reqq.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
						r=reqq;
						break;
					}	
				}
			}
			
			Stage a = (Stage) scene.getWindow();
			a.close();
			if(r!=null) {
				if(r.getType() == 0 || r.getType() == 2) {
					try {
						QuantReqPopup(type,aggregation,req);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						QualReqPopup(type,aggregation,req);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				System.out.println("Null..?");
			}
		
				
		}

		public void deleteReq(Scene s,Requirement req,boolean agg,String source) {
			String[] data = source.split("_");

			if(agg) {
				for(Requirement reqq : reqs) {
					if(reqq.getCloudtype().equalsIgnoreCase(data[1]) || reqq.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))    ||  reqq.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
						reqs.remove(reqq);
						break;
					}	
				}
			}
			else {
				for(Requirement reqq : serviceTemplates.get(Integer.parseInt(data[0])-1).getRequirements()) {
					if(reqq.getCloudtype().equalsIgnoreCase(data[1]) || reqq.getCloudtype().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  reqq.getCloudtype().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
						serviceTemplates.get(Integer.parseInt(data[0]) -1  ).getRequirements().remove(reqq);
						break;
					}	
				}
			}
			
			if(s != null) {
				Stage a = (Stage) s.getWindow();
				a.close();
			}
			
		}

		public void editCrit(Scene scene, Criterion crit, String type) {
			// TODO Auto-generated method stub
			String[] data = type.split("_");
			
			Criterion crittemp=null;
			
			for(Criterion c : serviceTemplates.get(Integer.parseInt(data[0])-1).getCriteria()) {
				if(c.getName().equalsIgnoreCase(data[1]) || c.getName().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  c.getName().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
					crittemp = c;
					break;
				}	
			}
			
			Stage a = (Stage) scene.getWindow();
			a.close();
			if(crittemp!=null) {
				if(crittemp.getType() == 0 || crittemp.getType() == 2) {
					try {
						QuantCritPopup(type,crittemp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						QualCritPopup(type,crittemp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				System.out.println("Null..?");
			}
		}

		public void deleteCrit(Scene scene, Criterion crit, String type) {
			
			String[] data = type.split("_");

			for(Criterion c : serviceTemplates.get(Integer.parseInt(data[0])-1).getCriteria()) {
				if(c.getName().equalsIgnoreCase(data[1]) || c.getName().equalsIgnoreCase(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])))  ||  c.getName().equalsIgnoreCase(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))){
					serviceTemplates.get(Integer.parseInt(data[0]) -1  ).getCriteria().remove(c);
					break;
				}	
			}

			
			if(scene != null) {
				Stage a = (Stage) scene.getWindow();
				a.close();
			}
		}

		public void AnswerServerRequests(Scene s,boolean wait, int st) throws Exception {
			// TODO Auto-generated method stub
			setHistoryValues();
			if(s != null) {
				Stage a = (Stage) s.getWindow();
				a.close();
			}
			
			if(wait) {
				Stage myDialog = new Stage();
		        myDialog.initModality(Modality.APPLICATION_MODAL);

		        ArrayList<Object> res = loadFXML("ProcessSTRequests.fxml");
		        Scene myDialogScene = (Scene) res.get(0);
		        ProcessSTDataRequestController cont = (ProcessSTDataRequestController) res.get(1);
		        cont.setFather(this);
		        cont.setSTName(serviceTemplates.get(st));
		        cont.setSTNum(st+1);
		        cont.setQualCritNumber(countQualCrit(serviceTemplates.get(st)));
		        cont.start();
		        myDialog.setResizable(false);
		        myDialog.setScene(myDialogScene);
		        myDialog.show();
			}
			else {
				searchsheettab.setDisable(false);
				searchsheettab.getContent().setDisable(false);
			}
		}
		
		private int countQualCrit(ServiceTemplate STName) {
			int n = 0;
			if(STName.getCriteria() != null)
				for(Criterion c : STName.getCriteria())
					if(c.getType() == 1)
						n++;
			return n;
		}

		public void closeProcessSTController(Scene scene,int currentSTNum) {
			// TODO Auto-generated method stub
			if(scene != null) {
				Stage a = (Stage) scene.getWindow();
				a.close();
			}
			
			if(currentSTNum == data.getServiceTemplates().size()) {
				
				searchsheettab.setDisable(false);
				searchsheettab.getContent().setDisable(false);
				resultstab.setDisable(false);
				SingleSelectionModel<Tab> selectionModel = tabpane.getSelectionModel();
				
				FXMLLoader loader = new FXMLLoader();
		        InputStream in = Main.class.getResourceAsStream("WaitingResults.fxml");
		        loader.setBuilderFactory(new JavaFXBuilderFactory());
		        loader.setLocation(Main.class.getResource("WaitingResults.fxml"));
		        AnchorPane page=null;
		        try {
		            try {
						page = (AnchorPane) loader.load(in);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        } finally {
		            try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        wresultspopup = new Stage();
		        wresultspopup.initModality(Modality.APPLICATION_MODAL);
		        wresultspopup.setResizable(false);

		        wresultspopup.setScene(new Scene(page, 350, 90));
		        wresultspopup.show();
				
				selectionModel.select(1);
				listenToResults();
				
			}
			else {
				try {
					AnswerServerRequests(null,true,currentSTNum);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private void listenToResults() {
			// TODO Auto-generated method stub
			aggresanchorboss.setDisable(true);
			Task<AggregationSolutions> task = new Task<AggregationSolutions>() {
				
				@SuppressWarnings({ "rawtypes", "unused" })
				@Override
				public AggregationSolutions call() {
					
					Gson gson = new Gson();
					Path faxFolder = Paths.get("./[Client]JSONRequests-Results/");
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
					AggregationSolutions vars = null;
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
									String s = FileUtils.readFileToString(new File("./[Client]JSONRequests-Results/" + fileName));
									System.out.println(s);
									if(!s.equals("{}"))
										vars = gson.fromJson(FileUtils.readFileToString(new File("./[Client]JSONRequests-Results/" + fileName)), AggregationSolutions.class);
									else
										vars = new AggregationSolutions();
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
					AggregationSolutions vars = (AggregationSolutions)event.getSource().getValue();
						try {
							presentResults(vars);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});

			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		}

		private void presentResults(AggregationSolutions res) {
			
			if(currentTVdata != null)
				currentTVdata.removeAll(currentTVdata);
			else
				currentTVdata = FXCollections.observableArrayList();
			
			if(res.getSolutions() == null) {
				anchorres.getChildren().clear();//clear everything that is there already
				Label noresults = new Label();
				noresults.setText("No results were found, please reconsider your search parameters.");
				noresults.setFont(new Font("Comic Sans MS Bold", 16));
				noresults.setLayoutX(30);
				noresults.setLayoutY(25);
				anchorres.getChildren().add(noresults);
			}
			else if(res.getSolutions().isEmpty()) {
				anchorres.getChildren().clear();//clear everything that is there already
				Label noresults = new Label();
				noresults.setText("No results were found, please reconsider your search parameters.");
				noresults.setFont(new Font("Comic Sans MS Bold", 16));
				noresults.setLayoutX(30);
				noresults.setLayoutY(25);
				anchorres.getChildren().add(noresults);
			}
			else {
				currentTVdata.addAll(processResults(res));
				
				tableview.setEditable(false);
				 
		        TableColumn<AgSolTView,String> NameCol = new TableColumn<AgSolTView,String>("Aggregated Solutions");
		        NameCol.setPrefWidth(800);
		        NameCol.setCellValueFactory(
		                new PropertyValueFactory<AgSolTView, String>("name"));

				tableview.getColumns().add(NameCol);
				tableview.setItems(currentTVdata);
				
				tableview.setRowFactory(new Callback<TableView<AgSolTView>, TableRow<AgSolTView>>() {
					@Override
					public TableRow<AgSolTView> call(TableView<AgSolTView> p) {
						final TableRow<AgSolTView> row = new TableRow<AgSolTView>();
						row.setOnMouseClicked(new EventHandler<Event>() {

							@Override
							public void handle(Event event) {
								setSelection(row);
							}

						});
						return row;
					}
				});
			}

			aggresanchorboss.setDisable(false);
			Stage a = (Stage) wresultspopup.getScene().getWindow();
			a.close();
			wresultspopup.close();
		}
		
		private void setSelection(IndexedCell<AgSolTView> cell) {
			int y=15;
			if (cell.isSelected()) {
				System.out.println(cell.getItem().getName());
				anchorres.getChildren().clear();//clear everything that is there already
				Iterator<List<String>> features = cell.getItem().getFeatures().iterator();
				Iterator<List<String>> featVals = cell.getItem().getFvals().iterator();
				while(features.hasNext()) {
					List<String> altFeat = features.next();
					List<String> altFeatVals = featVals.next();
					
					Iterator<String> Compfeat = altFeat.iterator();
					Iterator<String> CompfeatVals = altFeatVals.iterator();
					
					while(Compfeat.hasNext()) {
						String featureName = Compfeat.next();
						String featureValue = CompfeatVals.next();
						
						Label featName = new Label();
						featName.setText(featureName.replaceAll("http://rdfs.genssiz.org/CloudTaxonomy#", "") + ":   ");
						featName.setFont(new Font("Comic Sans MS Bold", 12));
						
						Label featVal = new Label();
						featVal.setText(featureValue);
						
						HBox cel = new HBox();
						cel.getChildren().addAll(featName,featVal);
						cel.setLayoutX(20);
						cel.setLayoutY(y);
						anchorres.getChildren().add(cel);
						y+=15;
					}
					
					Separator s = new Separator();
					s.setLayoutX(20);
					s.setLayoutY(y+35);
					s.setPrefWidth(240);
					s.getStylesheets().add("/css/separator.css");
					anchorres.getChildren().add(s);
					y+=60;
				}
			}
		}

		private ObservableList<AgSolTView> processResults(AggregationSolutions res) {
			// TODO Auto-generated method stub
			ObservableList<AgSolTView> data =  FXCollections.observableArrayList();
			
			Iterator<AggregatedSolution> s = res.getSolutions().iterator();
			while(s.hasNext()){
				Iterator<AggregationComponent> it = s.next().getComponents().iterator();
				String sname = "";
				List<List<String>> features = new ArrayList<List<String>>();
				List<List<String>> fvals = new ArrayList<List<String>>();
				while(it.hasNext()) {
					AggregationComponent comp = it.next();
					
					if(sname.equals(""))
						sname+=sname+comp.getFeatureValues().get(0) ;
					else
							sname = sname +"  ;  " +comp.getFeatureValues().get(0) ;
					features.add(comp.getFeatures());
					fvals.add(comp.getFeatureValues());
				}
				AgSolTView t = new AgSolTView(sname);
				t.setFeatures(features);
				t.setFvals(fvals);
				data.add(t);
			}
			
			return data;
		}

		public void loadNewCSA(CSAData s) {
			// TODO Auto-generated method stub
			setNewST(false);
			data=s;
			serviceTemplates.clear();
			reqs.clear();
			
			if(!s.getName().equals(""))
				searchsheetname.setText(s.getName());
			
			for(ServiceTemplate t : s.getServiceTemplates())
				serviceTemplates.add(t);
			
			for(Requirement r : s.getRequirements())
				reqs.add(r);

			for(int i=1;i< s.getServiceTemplates().size();i++) {
				setNewST(false);
			}
			
			if(data.getMethod() == PROMETHEE)
				methodcombobox.setValue("PROMETHEE I");
			else if(data.getMethod() == ELECTRE)
				methodcombobox.setValue("ELECTRE III");
			else if(data.getMethod() == SMAA2)
				methodcombobox.setValue("SMAA-2");
			else
				methodcombobox.setValue("SAW");
			for(int i=0;i< s.getServiceTemplates().size();i++) {
				
				//st reqs
				for(Requirement r : s.getServiceTemplates().get(i).getRequirements()) {
					for(CheckBox c : reqsChecks.get(i)) {
						String[] data = c.getId().split("_");
						
						if(r.getType() == 1) {
							if(r.getCloudtype().equals(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))) {
								c.setSelected(true);
							}
						}
						else if (r.getType() == 0) {
							if(r.getCloudtype().equals(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1]))) ) {
								c.setSelected(true);
							}
						}
						else if(r.getType() == 2) {
							if(r.getCloudtype().toLowerCase().equals( data[1].toLowerCase())) {
								c.setSelected(true);
							}
						}
					}
				}
				
				//st crit
				for(Criterion crit : s.getServiceTemplates().get(i).getCriteria()) {
					for(CheckBox c : critsChecks.get(i)) {
						String[] data = c.getId().split("_");
						
						if(crit.getType() == 1) {
							
							if(crit.getName().equals(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))) {
								c.setSelected(true);
							}
						}
						else if (crit.getType() == 0) {
							if(crit.getName().equals(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1]))) ) {
								c.setSelected(true);
							}
						}
						else if(crit.getType() == 2) {
							if(crit.getName().toLowerCase().equals( data[1].toLowerCase())) {
								c.setSelected(true);
							}
						}
					}
				}
				
				
			}
			//global reqs
			for(Requirement r : s.getRequirements()) {
				for(CheckBox c : agreqs) {
					String[] data = c.getId().split("_");
					
					if(r.getType() == 1) {
						if(r.getCloudtype().equals(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])))) {
							c.setSelected(true);
						}
					}
					else if (r.getType() == 0) {
						if(r.getCloudtype().equals(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1]))) ) {
							c.setSelected(true);
						}
					}
					if(r.getCloudtype().toLowerCase().equals( data[1].toLowerCase())) {
						c.setSelected(true);
					}
				}
			}
		}
}
