package com.kaselabs.projman.view;

import com.kaselabs.projman.model.entities.ToDoTask;
import javafx.event.Event;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

/**
 * Created by Rick on 7/5/2017.
 */
public class ToDoTaskTreeItem extends CheckBoxTreeItem<ToDoTask> {

	public ToDoTaskTreeItem(ToDoTask value) {
		setValue(value);
		setExpanded(true);
		setIndependent(true);
	}

}
