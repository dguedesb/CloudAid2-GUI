package guiModels;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class AgSolTView {
	private final SimpleStringProperty name;
	
	private List<List<String>> features;
	private List<List<String>> fvals;
    public AgSolTView(String name) {
        this.name = new SimpleStringProperty(name);
    }

	public String getName() {
		return name.get();
	}

	public List<List<String>> getFeatures() {
		return features;
	}

	public void setFeatures(List<List<String>> features) {
		this.features = features;
	}

	public List<List<String>> getFvals() {
		return fvals;
	}

	public void setFvals(List<List<String>> fvals) {
		this.fvals = fvals;
	}

    
}
