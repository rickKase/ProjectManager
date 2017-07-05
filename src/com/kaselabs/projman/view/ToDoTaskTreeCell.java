package com.kaselabs.projman.view;

import com.kaselabs.projman.model.entities.ToDoTask;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Rick on 7/5/2017.
 */
public class ToDoTaskTreeCell extends CheckBoxTreeCell<ToDoTask> {

	private ContextMenu menu = new ContextMenu();
	private CheckBox box;
	private TextField field;

	public ToDoTaskTreeCell() {
		field = new TextField();
		field.setOnKeyPressed((keyEvent) -> {
			if (keyEvent.getCode() == KeyCode.ENTER)
				commitEdit(getItem());
		});
			field.focusedProperty().addListener((value, oldValue, newValue) -> {
				if (!newValue) cancelEdit();
			});

		MenuItem renameItem = new MenuItem("Rename");
		MenuItem commitEdit = new MenuItem("Commit");
		MenuItem cancelEdit = new MenuItem("Cancel");
		MenuItem addItem = new MenuItem("add Item");
		MenuItem removeItem = new MenuItem("remove Item");
		MenuItem becomeUncleItem = new MenuItem("become Uncle");
		MenuItem becomeNephewItem = new MenuItem("become Nephew");
		MenuItem moveUpItem = new MenuItem("move Up");
		MenuItem moveDownItem = new MenuItem("move Down");

		renameItem.setOnAction((actionEvent) -> startEdit());
		commitEdit.setOnAction((actionEvent) -> commitEdit(getItem()));
		cancelEdit.setOnAction((actionEvent) -> cancelEdit());
		addItem.setOnAction((actionEvent) -> addChild());
		removeItem.setOnAction((actionEvent) -> removeThis());
		moveUpItem.setOnAction((actionEvent) -> moveUp());
		moveDownItem.setOnAction((actionEvent) -> moveDown());
		becomeUncleItem.setOnAction((actionEvent) -> becomeUncle());
		becomeNephewItem.setOnAction((actionEvent) -> becomeNephew());

		menu.getItems().addAll(renameItem, commitEdit, cancelEdit,
				addItem, removeItem, moveUpItem, moveDownItem,
				becomeUncleItem, becomeNephewItem);
		setContextMenu(menu);

		setOnDragDetected((dragEvent) -> System.out.println(getText()));
	}

	@Override
	public void updateItem(ToDoTask item, boolean empty) {
		super.updateItem(item, empty);
		if (empty)
			return;
		box = (CheckBox) getGraphic();
		box.setAllowIndeterminate(false);
		box.setSelected(getItemStatus());
		box.selectedProperty().addListener((event) -> {
			setItemStatus(box.isSelected());
		});
		setText(item.getTitle());
	}

	private boolean getItemStatus() {
		if (getItem().getStatus() == ToDoTask.Status.COMPLETE)
			return true;
		else
			return false;
	}

	private void setItemStatus(boolean status) {
		if (status)
			getItem().setStatus(ToDoTask.Status.COMPLETE);
		else
			getItem().setStatus(ToDoTask.Status.INCOMPLETE);
	}

	public void addChild() {
		ToDoTaskTreeItem child =
				new ToDoTaskTreeItem(new ToDoTask());
		getTreeItem().getChildren().add(child);
		getTreeView().getSelectionModel().select(child);
	}

	public void removeThis() {
		TreeItem<ToDoTask> item = getTreeItem();
		item.getParent().getChildren().remove(item);
	}

	public void becomeUncle() {
		TreeItem<ToDoTask> item = getTreeItem();
		TreeItem<ToDoTask> grandParent =
				getTreeItem().getParent().getParent();
		if (grandParent == null)
			return;
		item.getParent().getChildren().remove(item);
		grandParent.getChildren().add(item);
		getTreeView().getSelectionModel().select(item);
	}

	public void becomeNephew() {
		TreeItem<ToDoTask> item = getTreeItem();
		TreeItem<ToDoTask> parent = item.getParent();
		if (parent == null)
			return;
		int index = parent.getChildren().indexOf(item);
		if (index == 0)
			return;
		parent.getChildren().remove(index);
		parent.getChildren().get(index - 1).getChildren().add(item);
		getTreeView().getSelectionModel().select(item);
	}

	public void moveUp() {
		TreeItem<ToDoTask> item = getTreeItem();
		TreeItem<ToDoTask> parent = item.getParent();
		if (parent == null)
			return;
		int index = parent.getChildren().indexOf(item);
		if (index == 0)
			return;
		parent.getChildren().remove(index);
		parent.getChildren().add(index - 1, item);
		getTreeView().getSelectionModel().select(item);
	}

	public void moveDown() {
		TreeItem<ToDoTask> item = getTreeItem();
		TreeItem<ToDoTask> parent = item.getParent();
		if (parent == null)
			return;
		int index = parent.getChildren().indexOf(item);
		if (index == parent.getChildren().size() - 1)
			return;
		parent.getChildren().remove(index);
		parent.getChildren().add(index + 1, item);
		getTreeView().getSelectionModel().select(item);
	}

	@Override
	public void startEdit() {
		super.startEdit();
		field.setText(getText());
		HBox holder = new HBox(box, field);
		HBox.setHgrow(field, Priority.ALWAYS);
		setGraphic(holder);
		setText(null);
		field.requestFocus();
	}

	@Override
	public void commitEdit(ToDoTask item) {
		super.commitEdit(item);
		setGraphic(box);
		setText(field.getText());
		item.setTitle(field.getText());
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setGraphic(box);
		setText(getItem().getTitle());
	}

}
