package com.kaselabs.projman.Model;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Represents the data folder and provides methods to easily obtain
 * reference to all data stored in the application.
 *
 * Created by Rick on 6/13/2017.
 */
public class DataFolder {

	public static final String BASE_DIRECTORY = ".";
	private static String projectDTD = "project.dtd";

	private File dataFile;
	private File projectFolder;

	public DataFolder() {
		dataFile = new File(BASE_DIRECTORY, "data");
		projectFolder = new File(dataFile, "projects");
	}

	public File getProjectFolder() {
		return projectFolder;
	}

	public File[] getProjectFiles() {
		return projectFolder.listFiles(new xmlFileFilter());
	}

	public File getProjectSchemaDTD() {
		return new File(projectFolder, projectDTD);
	}

	public int getDataFilesCount() {
		return projectFolder.list().length - 1;
	}

	public void deleteProjectFile(String fileName) {
		File file = new File(projectFolder, fileName);
		if (!file.exists())
			throw new IllegalArgumentException("File does not exist");
		file.delete();
	}

	public File createProjectFile(String fileName) {
		File file = new File(projectFolder, fileName);
		if (file.exists())
			throw new IllegalArgumentException("File already exist");
		try {
			file.createNewFile();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private class xmlFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			return file.getName().endsWith("xml");
		}
	}
}