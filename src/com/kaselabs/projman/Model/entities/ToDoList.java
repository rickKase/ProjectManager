package com.kaselabs.projman.Model.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rick on 6/10/2017.
 */
public class ToDoList implements ToDoListItem {

	private String title;
	private List<ToDoListItem> listOfItems;

	public ToDoList(String title) {
		this.title = title;
		listOfItems = new ArrayList<>();
	}

	public ToDoList(String title, List<ToDoListItem> listOfItems) {
		this.title = title;
		this.listOfItems = listOfItems;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCompleted() {
		Iterator<ToDoListItem> i = listOfItems.iterator();
		boolean completed;
		do {
			completed = i.next().isCompleted();
			if (!i.hasNext())
				break;
		} while (completed);
		return completed;
	}

	public void addItem(ToDoListItem item) {
		if (listOfItems.contains(item))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.add(item);
	}

	public void addItem(int index, ToDoListItem item) {
		if (listOfItems.contains(item))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.add(index, item);
	}

	public void removeItem(ToDoListItem items) {
		listOfItems.remove(items);
	}

	public void removeItem(int index) {
		listOfItems.remove(index);
	}

	public ToDoListItem getItem(int index) {
		return listOfItems.get(index);
	}

	public int getItemCount() {
		return listOfItems.size();
	}
}
