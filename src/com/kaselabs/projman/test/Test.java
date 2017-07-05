package com.kaselabs.projman.test;

import com.kaselabs.projman.Model.Dao;
import com.kaselabs.projman.Model.entities.ToDoTask;


/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {
//		EntityParser parser = EntityParser.getInstance();
//
//		Project proj = parser.loadProjects()[0];
//
//		System.out.println(proj.getSummary());
//
//		parser.saveProject(proj);

		Dao dao = new Dao();

		ToDoTask toDoTask = dao.readToDos()[0];
		toDoTask.printFullToDo();

		System.out.println();
		toDoTask.getItem(0).getItem(1).getItem(2).setStatus(ToDoTask.Status.COMPLETE);
		System.out.println();

		toDoTask.printFullToDo();

		dao.writeToDo(toDoTask);

//		ToDoTask todo1 = new ToDoTask();
//		ToDoTask todo2 = new ToDoTask();
//		ToDoTask todo3 = new ToDoTask();
//		ToDoTask todo4 = new ToDoTask();
//
//		todo1.setTitle("todo1");
//		todo2.setTitle("todo2");
//		todo3.setTitle("todo3");
//		todo4.setTitle("todo4");
//
//		todo1.add(todo2);
//		todo2.add(todo3);
//		System.out.println(todo1.getItems()[0].getTitle());
//		System.out.println(todo3.getAncestors()[1].getTitle());
	}

}
