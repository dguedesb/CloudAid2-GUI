package controller;

import java.net.URL;
import java.util.ResourceBundle;

import models.CLOUDEnumMiddleMan;
import models.Criterion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class QualCritController implements Initializable{
	
	@FXML
	CheckBox qualcritimportancecheckbox;
	@FXML
	CheckBox thresholdscheckbox;
	
	@FXML 
	Label qualtype;
	@FXML
	Label importancevaluelabel;
	@FXML
	ComboBox<String> qualcritdircombobox;
	
	@FXML
	Slider importanceslider;
	
	@FXML
	TextField indifferencevalue;
	@FXML
	TextField preferencevalue;
	@FXML
	TextField vetovalue;
	
	@FXML
	Button closePopupbtn;
	
	@FXML
	AnchorPane anchorpane;
	
	@FXML
	ImageView qualcrithelp1;
	
	@FXML
	ImageView qualcrithelp2;
	
	@FXML
	ImageView qualcrithelp3;
	@FXML
	ImageView qualcrithelp4;
	
	@FXML
	TextField qualcritpreferval;
	
	private String type;
	@SuppressWarnings("unused")
	private int method;
	private MainPanelController father;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		closePopupbtn.getStylesheets().add("/css/buttonscss.css");
		
		Tooltip t = new Tooltip("Importance tooltip");
        Tooltip.install(qualcrithelp1, t);
        
        Tooltip t2 = new Tooltip("direction tooltip");
        Tooltip.install(qualcrithelp2, t2);
        
        Tooltip t3 = new Tooltip("thresholds tooltip");
        Tooltip.install(qualcrithelp3, t3);
        
        Tooltip t4 = new Tooltip("preference value tooltip");
        Tooltip.install(qualcrithelp4, t4);
		
        importancevaluelabel.setText(Double.toString(importanceslider.getValue()));
        importancevaluelabel.setVisible(false);
        
        qualcritimportancecheckbox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				CheckBox source = (CheckBox) event.getSource();
				if(source.isSelected()) {
					try {
						importanceslider.setDisable(false);
						importancevaluelabel.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					importanceslider.setDisable(true);
					importancevaluelabel.setVisible(false);
				}
			}});
        
        thresholdscheckbox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				CheckBox source = (CheckBox) event.getSource();
				if(source.isSelected()) {
					try {
						indifferencevalue.setDisable(false);
						preferencevalue.setDisable(false);
						vetovalue.setDisable(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					indifferencevalue.setDisable(true);
					preferencevalue.setDisable(true);
					vetovalue.setDisable(true);
				}
			}});
        
        qualcritdircombobox.getItems().addAll(
			    "Maximize",
			    "Minimize"
			);
        qualcritdircombobox.setValue("Maximize");
        
        importanceslider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    importancevaluelabel.setText(String.format("%.2f", new_val));
            }
        });
        
	}
	
	public void setType(String t) {
		type = t;
		String[] data = t.split("_");
		qualtype.setText(data[1]);
	}
	
	public void setMethod(int t) {
		method = t;
	}
	
	public void loadCrit(Criterion crit) {
		// TODO Auto-generated method stub
		if(crit.getWeight() >= 0) {
			importanceslider.setDisable(false);
			importanceslider.setValue(crit.getWeight());
			importancevaluelabel.setVisible(true);
			importancevaluelabel.setText(String.valueOf(importanceslider.getValue()));
			qualcritimportancecheckbox.setSelected(true);
		}
		
		if(crit.getPreference() != null)
			qualcritpreferval.setText(crit.getPreference());
		
		if(crit.getPreferenceDirection().equals("min"))
			qualcritdircombobox.setValue("Minimize");
		else
			qualcritdircombobox.setValue("Maximize");
		
		if(crit.getIndifference_threshold() >= 0 || crit.getPreference_threshold() >= 0 || crit.getVeto_threshold() >= 0){
			thresholdscheckbox.setSelected(true);
			
			if(crit.getIndifference_threshold() >= 0) {
				indifferencevalue.setDisable(false);
				indifferencevalue.setText( String.valueOf(  crit.getIndifference_threshold()   ));
			}
			
			if(crit.getPreference_threshold() >= 0) {
				preferencevalue.setDisable(false);
				preferencevalue.setText( String.valueOf(  crit.getPreference_threshold()   ));
			}
			
			if(crit.getVeto_threshold() >= 0) {
				vetovalue.setDisable(false);
				vetovalue.setText( String.valueOf(  crit.getVeto_threshold()   ));
			}
		}
	}
	

	
	public void setFather(MainPanelController f) {
		father = f;
	}
	
	public void closePopup() {
		System.out.println("Closing QualCrit Popup");
		
		Criterion crit = new Criterion();
		CLOUDEnumMiddleMan mm = new CLOUDEnumMiddleMan();
		String[] data = type.split("_");

		crit.setName(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])));
		crit.setType(1);

		
//		if(this.method == 0 || this.method == 1 || this.method == 2) {
//			
//		}
		
		if(qualcritimportancecheckbox.isSelected()) {
			crit.setWeight(importanceslider.getValue());
		}
		
		boolean flag = false;
		if(qualcritpreferval.getText().equals(""))
			flag=true;
		else
			crit.setPreference(qualcritpreferval.getText());
		
		if(qualcritdircombobox.getValue().equalsIgnoreCase("maximize"))
			crit.setPreferenceDirection("max");
		else
			crit.setPreferenceDirection("min");
		
		boolean indif=false,pref=false,veto=false;
		if(thresholdscheckbox.isSelected()) {
			if(!indifferencevalue.getText().equals("")){
				crit.setIndifference_threshold(Double.parseDouble(indifferencevalue.getText()));
				indif=true;
			}
			if(!preferencevalue.getText().equals("")) {
				pref=true;
				crit.setPreference_threshold(Double.parseDouble(preferencevalue.getText()));
			}
			if(!vetovalue.getText().equals("")) {
				veto=true;
				crit.setVeto_threshold(Double.parseDouble(vetovalue.getText()));
			}
		}
		
		if((indif == false && pref == false && veto == true))
			flag=true;
		
		
		if(flag == false)
			father.closeQualCritPopup(anchorpane.getScene(),crit,Integer.parseInt(data[0]) - 1);
		else
			System.out.println("An error with parameters..");
	}

}
