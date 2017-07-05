package com.kaselabs.projman.Model.entities;

import com.kaselabs.projman.Model.Dao;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rick on 7/4/2017.
 */
public class User {

	private List<ToDoTask> toDoTaskList;
	private Dao dao;

	public User() {
		dao = new Dao();
		toDoTaskList = Arrays.asList(dao.readToDos());
	}

	public List<ToDoTask> getToDoTaskList() {
		return toDoTaskList;
	}
}
