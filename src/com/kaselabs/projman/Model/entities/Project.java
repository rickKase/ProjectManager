package com.kaselabs.projman.Model.entities;

/**
 * Created by Rick on 6/10/2017.
 */
public class Project {

	//private ArrayList<Idea> influences; // add when more relevant
	private String title;
	private String summary;
	private ToDoList list;

	public Project(String title) {
		this.title = title;
	}

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

	public ToDoList getList() {
		return list;
	}

	public void setList(ToDoList list) {
		this.list = list;
	}

}