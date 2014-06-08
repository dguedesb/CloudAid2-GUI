package controller;

import java.net.URL;
import java.util.ResourceBundle;

import models.CLOUDEnumMiddleMan;
import models.Requirement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class QualReqController implements Initializable{
	@FXML
	Button closePopupbtn;
	@FXML
	AnchorPane QualReqAnchorPane;
	
	@FXML
	Label qualreqpoptype;
	@FXML
	CheckBox qualreqvaluecheckbox;
	
	@FXML
	CheckBox qualreqexcludecheckbox;
	@FXML
	CheckBox qualreqNOTcheckbox;

	@FXML
	TextField qualreqvalue;
	
	@FXML
	ImageView qualreqpopuphelp1;
	
	@FXML
	ImageView qualreqpopuphelp2;
	
	@FXML
	Slider agslider;
	@FXML
	Label agsliderval;
	@FXML
	Label agtext;
	
	private boolean aggregation = false;
	private MainPanelController father;
	private String type = "";
	private int maxAg;
	public void setFather(MainPanelController f) {
		father = f;
	}

	public void setMaxAgg(int maxAgST) {
		
		maxAg = maxAgST;
		agslider.setMax(maxAg);
		agslider.setMin(1);
		agsliderval.setText(String.valueOf(1));
		if(maxAg == 1)
			agslider.setDisable(true);
		else {
			agslider.setValue(1);
		}
		System.out.println(maxAg);
	}
	
	public void setAggregation(boolean val) {
		aggregation = val;
		
		if(val) {
			agslider.setVisible(true);
			agsliderval.setVisible(true);
			agtext.setVisible(true);
			qualreqpopuphelp1.setVisible(false);
			qualreqpopuphelp2.setVisible(false);
			qualreqNOTcheckbox.setVisible(false);
			qualreqexcludecheckbox.setVisible(false);
			qualreqvaluecheckbox.setSelected(true);
			qualreqvalue.setDisable(false);
		}
		
	}
	
	public void closePopup() {
		System.out.println("Closing QualReq Popup");
		Requirement req = new Requirement();
		req.setType(1);
		CLOUDEnumMiddleMan mm = new CLOUDEnumMiddleMan();
		
		String[] data = type.split("_");
		
		req.setCloudtype(mm.getQualMiddleMan().get(mm.getPrettyPrint().get(data[1])));
		boolean flag=false;
		if(!qualreqvalue.getText().equals("")) {
			req.setQualValue(qualreqvalue.getText());
			req.setNeeded(true);
		}
		else if(qualreqvalue.getText().equals("") && !qualreqexcludecheckbox.isSelected())
			flag=true;
		
		if(qualreqexcludecheckbox.isSelected()) {
			req.setNeeded(true);
			req.setExclusive(true);
		}
		else {
			req.setNeeded(false);
			req.setExclusive(false);
		}
		
		if(qualreqNOTcheckbox.isSelected()) {
			req.setPositive(false);
		}
		else{
			req.setPositive(true);
		}
		
		if(aggregation)
			req.setMaxAgST(Integer.parseInt(agsliderval.getText()));
		
		if(!aggregation && flag == false)
			father.closeQualReqPopup(QualReqAnchorPane.getScene(),req,Integer.parseInt(data[0]) - 1);
		else if(flag==false)
			father.closeQualReqPopup(QualReqAnchorPane.getScene(),req, -1);
		else
			System.out.println("Something is wrong with your parameters.");
	}
	
	public void setType(String t) {
		type = t;
		String data[] = t.split("_");
		qualreqpoptype.setText(data[1]);
	}
	
	public void loadReq(Requirement r) {
	
		if(r.getQualValue() != null){
			qualreqvaluecheckbox.setSelected(true);
			qualreqvalue.setDisable(false);
			qualreqvalue.setText(r.getQualValue());
		}
		
		if(r.isExclusive())
			qualreqexcludecheckbox.setSelected(true);
		
		if(!r.isPositive())
			qualreqNOTcheckbox.setSelected(true);
		
		if(r.getMaxAgST() >= 1){
			agtext.setVisible(true);
			agslider.setVisible(true);
			agslider.setValue(r.getMaxAgST());
			agsliderval.setText(String.valueOf(r.getMaxAgST()));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		agslider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    agsliderval.setText(String.format("%d", new_val.intValue()));
            }
        });
		
		// TODO Auto-generated method stub
		Tooltip t = new Tooltip("Exclude services that don't have this feature.");
        Tooltip.install(qualreqpopuphelp1, t);
        
        Tooltip t2 = new Tooltip("E.g: Give me all services that are NOT from Tokyo.");
        Tooltip.install(qualreqpopuphelp2, t2);
        
        closePopupbtn.getStylesheets().add("/css/buttonscss.css");
        
        qualreqvaluecheckbox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				CheckBox source = (CheckBox) event.getSource();
				if(source.isSelected()) {
					try {
						qualreqvalue.setDisable(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					qualreqvalue.setDisable(true);
				}
			}});
	}
}
