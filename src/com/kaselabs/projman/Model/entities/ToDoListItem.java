package com.kaselabs.projman.Model.entities;

/**
 * Created by Rick on 6/10/2017.
 */
public interface ToDoListItem {

	public boolean isCompleted();
	public void setTitle(String title);
	public String getTitle();
}
