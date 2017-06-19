package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;
import org.w3c.dom.Document;

/**
 * Created by Rick on 6/18/2017.
 */
public class EntityParser {

	private ProjectParser projParser;
	private Dao dao;

	private static EntityParser instance = new EntityParser();

	private EntityParser() {
		projParser = new ProjectParser();
		dao = new Dao();
	}

	public static EntityParser getInstance() {
		return instance;
	}


	//// Save and load Projects with ease
	public void saveProject(Project project) {
		dao.writeProject(projParser.projectToDocument(project), project.getTitle());
	}

	public Project[] loadProjects() {
		Document[] docs = dao.readProjects();
		Project[] projs = new Project[docs.length];
		for (int i = 0; i < docs.length; i++) {
			projs[i] = projParser.documentToProject(docs[i]);
		}
		return projs;
	}
}
