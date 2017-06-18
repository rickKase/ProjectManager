package com.kaselabs.projman.Model.entities;

/**
 * Created by Rick on 6/10/2017.
 */
public class Project {

	// private ArrayList<Idea> influences;
	private String title;
	private String summary;
	private ToDoList list;

	public Project(String title) {
		this.title = title;
		list = new ToDoList("To Do:");
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

	public void addItem(ToDoItem item) {
		list.addItem(item);
	}

	public void removeItem(ToDoItem item) {
		list.removeItem(item);
	}

	public void removeItem(int item) {
		list.removeItem(item);
	}

	public ToDoItem getItem(int index) {
		return list.getItem(index);
	}

	public ToDoItem[] getItems() {
		return list.getItems();
	}

	public ToDoTask[] getTasks() {
		return list.getTasks();
	}

	public int getItemCount() {
		return list.getItemCount();
	}

	public int getTaskCount() {
		return list.getTaskCount();
	}
}