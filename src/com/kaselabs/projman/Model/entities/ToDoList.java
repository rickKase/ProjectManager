package com.kaselabs.projman.Model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A List that has a title and a list of items that
 * can be checked off as completed or not.
 * Created by Rick on 6/10/2017.
 */
public class ToDoList implements ToDoItem {

	private String title;
	private List<ToDoItem> listOfItems;

	//// Constructors ////

	public ToDoList(String title) {
		this.title = title;
		listOfItems = new ArrayList<>();
	}

	//// Getters and Setters ////

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns complete if every Item within the list has also been
	 * marked as complete. False otherwise.
	 * @return
	 */
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

	//// ToDoList manipulation methods ////

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

	public void addItems(Collection<ToDoItem> items) {
		if (containsAny(items))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.addAll(items);
	}

	public void addItems(int index, Collection<ToDoItem> items) {
		if (containsAny(items))
			throw new IllegalArgumentException("cannot add duplicate item");
		listOfItems.addAll(index, items);
	}


	public void removeItem(ToDoItem items) {
		listOfItems.remove(items);
	}

	public void removeItem(int index) {
		listOfItems.remove(index);
	}

	/* These Methods provide functionality for accessing the contents
	 * of the list of items and checking those contents against
	  * other objects. */

	/**
	 * Checks to see whether or not any of the items passed as argument
	 * appear in the ToDoList. returns true if some of them appear and
	 * false otherwise.
	 * @param items
	 * @return
	 */
	private boolean containsAny(Collection<ToDoItem> items) {
		for (ToDoItem item : items)
			if (listOfItems.contains(items))
				return true;
		return false;
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

	/**
	 * returns a list of all the tasks in this ToDoList regardless
	 * whether they are nested inside other ToDoLists.
	 * @return
	 */
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
