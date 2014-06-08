package controller;

import java.net.URL;
import java.util.ResourceBundle;

import models.CLOUDEnumMiddleMan;
import models.Requirement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class QuantReqController  implements Initializable{
	
	@FXML
	Button closePopupbtn;
	@FXML
	AnchorPane QuantReqAnchorPane;
	
	@FXML
	Label quantreqpoptype;
	@FXML
	CheckBox quantreqlimitcheckbox;
	@FXML
	ComboBox<String> quantreqlimitbox;
	@FXML
	TextField quantreqlimit;
	
	@FXML
	CheckBox quantreqexcludecheckbox;
	
	@FXML
	ImageView quantreqpopuphelp1;
	
	private boolean aggregation=false;
	private MainPanelController father;
	private String type = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		quantreqlimitbox.getItems().addAll(
			    "Maximum",
			    "Minimum"
			);
		quantreqlimitbox.setValue("Maximum");
		
		closePopupbtn.getStylesheets().add("/css/buttonscss.css");
		
		quantreqlimitcheckbox.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					CheckBox source = (CheckBox) event.getSource();
					if(source.isSelected()) {
						try {
							quantreqlimitbox.setDisable(false);
							quantreqlimit.setDisable(false);
							quantreqexcludecheckbox.setSelected(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						quantreqlimitbox.setDisable(true);
						quantreqlimit.setDisable(true);
					}
				}});
	}
	
	
	
	public void setAggregation(boolean val) {
		aggregation = val;
		if(aggregation){
			quantreqlimitcheckbox.setSelected(true);
			quantreqexcludecheckbox.setSelected(true);
			quantreqlimitbox.setDisable(false);
			quantreqlimit.setDisable(false);
		}
	}
	
	
	public void setType(String t) {
		type = t;
		String data[] = t.split("_");
		quantreqpoptype.setText(data[1]);
	}
	
	public void setFather(MainPanelController f) {
		father = f;
	}
	
	public void loadReq(Requirement r) {
		if(r.getMax() >= 0) {
			quantreqlimitcheckbox.setSelected(true);
			quantreqlimit.setDisable(false);
			quantreqlimit.setText(String.valueOf(r.getMax()));
			quantreqlimitbox.setValue("Maximum");
			quantreqlimitbox.setDisable(false);
		}
		else if (r.getMin() >= 0) {
			quantreqlimitcheckbox.setSelected(true);
			quantreqlimit.setDisable(false);
			quantreqlimit.setText(String.valueOf(r.getMin()));
			quantreqlimitbox.setValue("Minimum");
			quantreqlimitbox.setDisable(false);
		}
		
		if(r.isExclusive()) {
			quantreqexcludecheckbox.setSelected(true);
		}
		
		if(r.getMaxAgST() >= 1){
			quantreqlimitcheckbox.setSelected(true);
			quantreqexcludecheckbox.setSelected(true);
			quantreqlimitbox.setDisable(false);
			quantreqlimit.setDisable(false);
		}
			
			
	}
	
	public void closePopup() {
		System.out.println("Closing QuantReq Popup");
		
		Requirement req = new Requirement();
		CLOUDEnumMiddleMan mm = new CLOUDEnumMiddleMan();
		String[] data = type.split("_");
		
		if (data[1].equalsIgnoreCase("price"))
		{
			req.setType(2);
			req.setCloudtype("price");
		}
		else
		{
			req.setCloudtype(mm.getQuantMiddleMan().get(mm.getPrettyPrint().get(data[1])));
			req.setType(0);
		}
		boolean flag = false;
		if(quantreqlimitcheckbox.isSelected()) {
			if(quantreqlimitbox.getValue().equalsIgnoreCase("maximum")) {
				double k = -1;
					try {
						k = Double.parseDouble(quantreqlimit.getText());
					} catch (NumberFormatException e) {
						System.out.println("Not a valid number... try again");
						flag=true;
					}
				req.setMax((float)k);
				req.setExclusivityMax(true);
			}
			else {
				double k = -1;
					try {
						k = Double.parseDouble(quantreqlimit.getText());
					} catch (NumberFormatException e) {
						System.out.println("Not a valid number... try again");
						flag=true;
					}
				req.setMin((float)k);
				req.setExclusivityMax(false);
			}
			req.setExclusive(true);	
		}
		
		if(quantreqexcludecheckbox.isSelected()) {
			req.setNeeded(true);
			req.setExclusive(true);
		}
		else {
			if(!quantreqlimitcheckbox.isSelected()) {
				req.setNeeded(false);
				req.setExclusive(false);
			}
		}
		
		
		if(!aggregation && flag == false)
			father.closeQuantReqPopup(QuantReqAnchorPane.getScene(),req,Integer.parseInt(data[0]) - 1);
		else if(flag == false)
			father.closeQuantReqPopup(QuantReqAnchorPane.getScene(),req, -1);
		else
			System.out.println("Something is wrong with your parameters.");
		
	}

}
