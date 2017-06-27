package com.kaselabs.projman.View;

import com.kaselabs.projman.Model.EntityManager;
import com.kaselabs.projman.Model.entities.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Created by Rick on 6/19/2017.
 */
public class Controller {

	@FXML private ListView<Project> projectView;
	@FXML private TextField projectNameField;
	@FXML private Label summaryLabel;

	public void initialize() {
		Project proj = new Project("Test Project");
		proj.setSummary("This is a summary of the test project.");
		projectView.getItems().add(proj);
		summaryLabel.setText("");
	}

	@FXML
	public void addProject() {
		projectView.getItems().add(new Project(projectNameField.getText()));
	}

	@FXML
	public void deleteProject() {
		Project proj = projectView.getSelectionModel().getSelectedItem();
		if (proj == null)
			return;
		projectView.getItems().remove(proj);
	}

	@FXML
	public void chooseProject() {
		Project proj = projectView.getSelectionModel().getSelectedItem();
		summaryLabel.setText(proj.getSummary());
	}
}
