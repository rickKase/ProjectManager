package com.kaselabs.projman.test;

import com.kaselabs.projman.Model.Dao;
import com.kaselabs.projman.Model.EntityParser;
import com.kaselabs.projman.Model.entities.Project;
import org.w3c.dom.Document;


/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {
		EntityParser parser = EntityParser.getInstance();

		Project proj = parser.loadProjects()[0];

		System.out.println(proj.getSummary());

		parser.saveProject(proj);
	}

}
