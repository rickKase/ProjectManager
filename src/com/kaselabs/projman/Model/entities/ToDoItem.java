package com.kaselabs.projman.Model.entities;

/**
 * An Item that can exist within the context of a
 * ToDoList.
 * Created by Rick on 6/10/2017.
 */
public interface ToDoItem {

	public boolean isCompleted();
	public void setTitle(String title);
	public String getTitle();
}
