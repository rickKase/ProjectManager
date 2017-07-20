package com.kaselabs.projman.view1;

import com.kaselabs.projman.model.entities.ToDoTask;
import javafx.scene.control.CheckBoxTreeItem;

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
