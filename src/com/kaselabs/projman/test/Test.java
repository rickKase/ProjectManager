package com.kaselabs.projman.test;

import com.kaselabs.projman.Model.EntityManager;
import com.kaselabs.projman.Model.ProjectDao;
import com.kaselabs.projman.Model.entities.Project;
import com.kaselabs.projman.Model.entities.ToDoItem;
import com.kaselabs.projman.Model.entities.ToDoList;
import org.w3c.dom.Document;


/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {
		EntityManager man = EntityManager.getInstance();
		DOMWriter writer = new DOMWriter(4);

		Project proj = man.getSavedProjects()[0];

		proj.setTitle("Testing2");
		proj.getList().addItem(man.createToDoItem("add list item through my program", true));
		man.saveProject(proj);
	}

}
