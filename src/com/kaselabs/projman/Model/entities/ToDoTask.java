package com.kaselabs.projman.Model.entities;

/**
 * A single task in a ToDoList that can be marked as
 * completed.
 * Created by Rick on 6/10/2017.
 */
public class ToDoTask implements ToDoItem {

	private String title;
	private boolean completed = false;

	//// Constructor ////

	public ToDoTask(String title) {
		this.title = title;
	}

	//// Setters, Getters, and manipulators ////

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

	public void flipCompleted() {
		completed = !completed;
	}


}
