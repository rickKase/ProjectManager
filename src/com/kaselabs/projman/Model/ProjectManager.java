package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;
import com.kaselabs.projman.Model.entities.ToDoItem;
import com.kaselabs.projman.Model.entities.ToDoList;
import com.kaselabs.projman.Model.entities.ToDoListItem;

/**
 * Created by Rick on 6/12/2017.
 */
public class ProjectManager {

	private ProjectManager manager = new ProjectManager();
	private ProjectDao dao = new ProjectDao();

	private ProjectManager() {
	}

	public ProjectManager getInstance() {
		return manager;
	}

	public Project createProject(String title) {
		return new Project(title);
	}

	public Project createProject(String title, String summary) {
		Project proj = new Project(title);
		proj.setSummary(summary);
		return proj;
	}

	public Project createProject(String title, String summary, ToDoList list) {
		Project proj = new Project(title);
		proj.setSummary(summary);
		proj.setList(list);
		return proj;
	}

	public ToDoList createToDoList(String title) {
		return new ToDoList(title);
	}

	public ToDoList createToDoList(String title, ToDoListItem list) {
		ToDoList theList = new ToDoList(title);
		theList.addItem(list);
		return theList;
	}

	public ToDoItem createToDoItem(String title) {
		return new ToDoItem(title);
	}

	public ToDoItem createToDoItem(String title, boolean completed) {
		ToDoItem item = new ToDoItem(title);
		item.setCompleted(completed);
		return item;
	}

	/**
	 * Should load all files from the save directory and turn out
	 * an array of the Projects that those files represent. Should
	 * take the save directory as an argument and load all files
	 * in the directory. Will return one Project for each file.
	 * @return
	 */
	public Project[] loadProjects() {
		return null;
	}
}
