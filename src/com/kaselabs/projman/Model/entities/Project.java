package com.kaselabs.projman.Model.entities;

import java.util.Collection;

/**
 * Represents a potential idea for a project.
 * Has a title and summary of the  project, as well as
 * a To do List that tracks the progress being made
 * towards completing the project.
 * Created by Rick on 6/10/2017.
 */
public class Project {

	// private ArrayList<Idea> influences;
	private String title;
	private String summary;
	private ToDoList list;

	//// Constructor ////

	public Project(String title) {
		this.title = title;
		list = new ToDoList("To Do:");
	}


	///////////////////////////////////////////////////////////////////
	///// These Methods are Getters and Setters ///////////////////
	///////////////////////////////////////////////////////////
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	///////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////
	///// These Methods add Items /////////////////////////////////
	///////////////////////////////////////////////////////////
	public void addItem(ToDoItem item) {
		list.addItem(item);
	}

	public void addItem(int index, ToDoItem item) {
		list.addItem(index, item);
	}

	public void addItems(Collection<ToDoItem> items) {
		list.addItems(items);
	}

	public void addItems(int index, Collection<ToDoItem> items) {
		list.addItems(index, items);
	}
	///////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////
	///// These Methods remove Items //////////////////////////////
	///////////////////////////////////////////////////////////
	public void removeItem(ToDoItem item) {
		list.removeItem(item);
	}

	public void removeItem(int item) {
		list.removeItem(item);
	}
	///////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////
	///// These Methods give content information //////////////////
	///////////////////////////////////////////////////////////
	public ToDoList getToDoList() {
		return list;
	}

	public void setToDoList(ToDoList list) {
		this.list = list;
	}
	///////////////////////////////////////////////////////////////////
}