package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Acts as a single location to interact with all the data currently
 * being stored and manipulated in the program.
 * Created by Rick on 6/19/2017.
 */
public class EntityManager {

	private List<Project> projects = new ArrayList<>();

	public EntityManager() {
		EntityParser parser = EntityParser.getInstance();
		projects = Arrays.asList(parser.loadProjects());
	}
}
