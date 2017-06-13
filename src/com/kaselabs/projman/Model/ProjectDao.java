package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;

import java.io.File;

/**
 * Created by Rick on 6/12/2017.
 */
public class ProjectDao {

	public static final String BASE_DIRECTORY = ".";
	public static final String DATA_DIRECTORY = "." + File.separator + "data";
	public static final String PROJECT_DATA_DIRECTORY =
			"." + File.separator + "data" + File.separator + "projects";

	/**
	 * input is an xml file and output is the object that the
	 * file represents.
	 * @param file
	 * @return
	 */
	public Project getProject(File file) {
		return null;
	}

	public Project getPrject(String fileName) {
		getProject(new File(PROJECT_DATA_DIRECTORY, fileName));
		return null;
	}

}
