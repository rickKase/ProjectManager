package com.kaselabs.projman.model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Task on a to do list.
 * Created by Rick on 7/3/2017.
 */
public class ToDoTask {

	private String title;
	private Status status;
	private List<ToDoTask> children;
	private ToDoTask parent;

	/**
	 * Creates a blank ToDoList
	 */
	public ToDoTask() {
		this.status = Status.INCOMPLETE;
		children = new ArrayList<>();
	}

	///// Title Manipulation /////

	/**
	 * Returns the title of this ToDoTask
	 * @return title of this ToDoItem
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this ToDoTask to the argument
	 * provided.
	 * @param title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	///// Status Manipulation /////

	/**
	 * Returns the status of this ToDoItem that was set.
	 * If there are any child toDoItems then the status
	 * of this item is dependent on those children.
	 * A ToDoTask is considered:
	 * FAILED if any of its children are FAILED,
	 * COMPLETED if all of its children are COMPLETED,
	 * or INCOMPLETE otherwise.
	 * @return current status of this ToDoItem
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * sets the status to the status provided as an
	 * argument if the ToDoTask does not have any
	 * children. If it does then the task is set to
	 * incomplete.
	 * @param status
	 */
	public void setStatus(Status status) {
		if (children.size()!= 0)
			return;
		this.status = status;
		if (parent != null)
			parent.evaluateStatus();
	}

	/**
	 * Sets the status equal to what it ought to be
	 * based on the status of all of its children.
	 */
	private void evaluateStatus() {
		if (getItemWithStatusCount(Status.COMPLETE) == children.size())
			status =  Status.COMPLETE;
		else
			status =  Status.INCOMPLETE;
		for (ToDoTask child : children)
			if (child.getStatus() == Status.FAILED)
				status = Status.FAILED;
		if (parent != null)
			parent.evaluateStatus();
	}


	///// List Management methods /////

	/**
	 * adds the item provided as a child.
	 * @param item
	 */
	public void add(ToDoTask item) {
		if (children.size() == 0)
			status = Status.INCOMPLETE;
		children.add(item);
		item.parent = this;

	}

	/**
	 * adds all the items provided as a child.
	 * @param items
	 */
	public void add(ToDoTask... items) {
		if (children.size() == 0 && items.length != 0)
			status = Status.INCOMPLETE;
		for (ToDoTask item : items) {
			children.add(item);
			item.parent = this;
		}
	}

	/**
	 * adds all the items provided as a child.
	 * @param items
	 */
	public void add(List<ToDoTask> items) {
		if (children.size() == 0 && items.size() != 0)
			status = Status.INCOMPLETE;
		for (ToDoTask item : items) {
			children.add(item);
			item.parent = this;
		}
	}

	/**
	 * removes the item at the provided index from the
	 * child list.
	 * @param index
	 */
	public void remove(int index) {
		ToDoTask todo = children.get(index);
		remove(todo);
	}

	/**
	 * removes the ToDoItem provided from the child list.
	 * @param item
	 */
	public void remove(ToDoTask item) {
		children.remove(item);
		item.parent = null;

	}

	/**
	 * Returns the item at the provided index.
	 * @param index
	 * @return item at the index provided
	 */
	public ToDoTask getItem(int index) {
		return children.get(index);
	}

	/**
	 * Returns the number of child items under this item.
	 * @return number of items
	 */
	public int getItemCount() {
		return children.size();
	}

	/**
	 * Returns the number of children under this item that
	 * have the status provided as an argument
	 * @param status to search for
	 * @return number of items with status
	 */
	public int getItemWithStatusCount(Status status) {
		int counter = 0;
		for (ToDoTask item : children)
			if (item.getStatus() == status)
				counter++;
		return counter;
	}

	/**
	 * Returns an array consisting of all the children under
	 * this ToDoItem.
	 * @return array of child ToDoItems
	 */
	public ToDoTask[] getItems() {
		return children.toArray(new ToDoTask[0]);
	}

	/**
	 * Returns an array consisting of all the children under
	 * this ToDoItem with the status provided.
	 * @param status to search for
	 * @return array of child ToDoItems with the given status
	 */
	public ToDoTask[] getItemsWithStatus(Status status) {
		List<ToDoTask> items = new ArrayList<>();
		for (ToDoTask item : children)
			if (item.getStatus() == status)
				items.add(item);
		return items.toArray(new ToDoTask[0]);
	}

	/**
	 * Prints out a recursive list of all the relevant states
	 * of the ToDoTask to the console.
	 */
	public void printFullToDo() {
		printToDo(this, 0);
	}

	/**
	 * Recursive call for printing of relevant values that keeps
	 * track of depth in order to print the status of that
	 * ToDoTask object at the correct indentation
	 * @param toDoTask to be printed
	 * @param indent value to be indented
	 * @return
	 */
	private int printToDo(ToDoTask toDoTask, int indent) {
		for (int i = 0; i < indent; i++)
			System.out.print("\t");
		System.out.println(toDoTask.getTitle()
				+ ": " + toDoTask.getStatus());
		indent++;
		for (ToDoTask child : toDoTask.getItems()) {
			printToDo(child, indent);
		}
		return --indent;
	}


	/**
	 * Defines the status of a ToDoObject
	 */
	public enum Status {
		COMPLETE, INCOMPLETE, FAILED
	}
}
