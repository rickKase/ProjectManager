package com.kaselabs.projman.controller;

import com.kaselabs.projman.model.entities.ToDoTask;
import com.kaselabs.projman.model.entities.User;
import com.kaselabs.projman.view1.ToDoTaskListCell;
import com.kaselabs.projman.view1.ToDoTaskTreeCell;
import com.kaselabs.projman.view1.ToDoTaskTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * Created by Rick on 6/19/2017.
 */
public class Controller {

	private User user;
	@FXML private ListView<ToDoTask> listView;
	@FXML private TreeView<ToDoTask> todoView;

	public void initialize() {
		user = new User();
		listView.setCellFactory((todo) -> new ToDoTaskListCell());
		listView.getItems().addAll(user.getToDoTaskList());

		todoView.setCellFactory((todo) -> new ToDoTaskTreeCell());
		todoView.setShowRoot(false);
		todoView.setEditable(true);

	}

	@FXML
	public void chooseToDo(MouseEvent mouseEvent) {
		ToDoTask selected = listView.getSelectionModel().getSelectedItem();
			if (selected != null)
				todoView.setRoot(create(selected));
	}

	private ToDoTaskTreeItem create(ToDoTask task) {
		ToDoTaskTreeItem node = new ToDoTaskTreeItem(task);
		for (ToDoTask subTask : task.getItems())
			node.getChildren().add(create(subTask));
		return node;
	}

	@FXML
	public void deleteToDo(ActionEvent actionEvent) {

	}

	@FXML
	public void createToDo(ActionEvent actionEvent) {

	}

	@FXML
	public void printModel(ActionEvent actionEvent) {
		ToDoTask task = listView.getSelectionModel().getSelectedItem();
		task.printFullToDo();
	}
}
