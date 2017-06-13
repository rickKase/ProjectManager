package com.kaselabs.projman.Model.entities;

/**
 * Created by Rick on 6/10/2017.
 */
public class ToDoItem implements ToDoListItem {

	private String title;
	private boolean completed = false;

	public ToDoItem(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
