package com.kaselabs.projman.View;

import com.kaselabs.projman.Model.entities.ToDoTask;
import com.kaselabs.projman.Model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * Created by Rick on 6/19/2017.
 */
public class Controller {

	private User user;
	@FXML private ListView<ToDoTask> toDoView;

	public void initialize() {
		user = new User();
		toDoView.getItems().addAll(user.getToDoTaskList());
	}


	public void chooseToDo(MouseEvent mouseEvent) {
	}

	public void deleteToDo(ActionEvent actionEvent) {
	}

	public void createToDo(ActionEvent actionEvent) {
	}
}
