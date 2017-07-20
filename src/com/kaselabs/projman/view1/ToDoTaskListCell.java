package com.kaselabs.projman.view1;

import com.kaselabs.projman.model.entities.ToDoTask;
import javafx.scene.control.ListCell;

/**
 * Created by Rick on 7/5/2017.
 */
public class ToDoTaskListCell extends ListCell<ToDoTask>{

	public void updateItem(ToDoTask item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null)
			setText(item.getTitle());
	}

}
