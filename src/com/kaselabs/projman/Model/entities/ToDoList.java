package com.kaselabs.projman.Model.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rick on 6/10/2017.
 */
public class ToDoList implements ToDoItem {

	private String title;
	private List<ToDoItem> listOfItems;

	public ToDoList(String title) {
		this.title = title;
		listOfItems = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCompleted() {
		Iterator<ToDoItem> iterator = listOfItems.iterator();
		boolean completed = false;
		do {
			if (!iterator.hasNext())
				break;
			completed = iterator.next().isCompleted();
		} while (completed);
		return completed;
	}

	public void addItem(ToDoItem item) {
		if (listOfItems.contains(item))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.add(item);
	}

	public void addItem(int index, ToDoItem item) {
		if (listOfItems.contains(item))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.add(index, item);
	}

	public void removeItem(ToDoItem items) {
		listOfItems.remove(items);
	}

	public void removeItem(int index) {
		listOfItems.remove(index);
	}

	public ToDoItem getItem(int index) {
		return listOfItems.get(index);
	}

	public ToDoItem[] getItems() {
		return listOfItems.toArray(new ToDoItem[0]);
	}

	public ToDoTask[] getTasks() {
		return getTasksList().toArray(new ToDoTask[0]);
	}

	private List<ToDoTask> getTasksList() {
		List<ToDoTask> tasks = new ArrayList<>();
		for (ToDoItem item : listOfItems)
			if (item instanceof ToDoTask)
				tasks.add((ToDoTask) item);
			else
				tasks.addAll(((ToDoList) item).getTasksList());
		return tasks;
	}

	public int getItemCount() {
		return listOfItems.size();
	}

	public int getTaskCount()  {
		return getTasksList().size();
	}
}
