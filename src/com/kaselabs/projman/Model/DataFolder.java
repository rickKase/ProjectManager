package com.kaselabs.projman.Model;

import java.io.File;
import java.io.IOException;

/**
 * Created by Rick on 6/13/2017.
 */
public class DataFolder {

	private static final File BASE_DIRECTORY = new File(".");
	private static final File DATA_DIRECTORY = new File(BASE_DIRECTORY, File.separator + "data");
	private static final File SCHEMA_DIRECTORY = new File(DATA_DIRECTORY, "schema");

	private static final File PROJECT_DTD = new File(SCHEMA_DIRECTORY, "project.dtd");
	public static final File PROJECTS_DIRECTORY = new File(DATA_DIRECTORY, "projects");

	private static final DataFolder instance = new DataFolder();

	private DataFolder() {}

	public DataFolder getInstance() {
		return instance;
	}

	public void createFile(File folder, String fileName) {
		try {
			new File(folder, fileName).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(File folder, String fileName) {
		new File(folder, fileName).delete();
	}

	public boolean hasFile(File folder, String fileName) {
		return new File(folder, fileName).exists();
	}

	public File[] getFiles(File folder) {
		return folder.listFiles();
	}

	public int getFileCount(File folder) {
		return folder.list().length;
	}
}