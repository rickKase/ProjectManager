package com.kaselabs.projman.Model;

import java.io.File;
import java.io.IOException;

/**
 * Meant to represent the in program data folder in order to
 * allow easy access and manipulation of its contents.
 * Created by Rick on 6/13/2017.
 */
public class DataFolder {

	/* internal Constants for all important folders in program */
	private static final File BASE_DIRECTORY = new File(".");
	private static final File DATA_DIRECTORY = new File(BASE_DIRECTORY, File.separator + "data");
	private static final File SCHEMA_DIRECTORY = new File(DATA_DIRECTORY, "schema");

	/* Reference to Folder and schema for all data types in program */
	public static final File PROJECT_DTD = new File(SCHEMA_DIRECTORY, "project.dtd");
	public static final File PROJECTS_DIRECTORY = new File(DATA_DIRECTORY, "projects");

	/* file extension used for stored data */
	public static final String EXTENSION = ".xml";

	/* Utilizes the Singleton model to ensure only one copy of the object */
	private static final DataFolder instance = new DataFolder();

	private DataFolder() {}

	public static DataFolder getInstance() {
		return instance;
	}


	/**
	 * Creates a file of the given name within the respective folder.
	 * Extension is added onto the filename within this method so
	 * file names passed to it should not include the extension.
	 * @param folder
	 * @param fileName
	 */
	public void createFile(File folder, String fileName) {
		try {
			new File(folder, fileName + EXTENSION).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a file of the given name within the respective folder.
	 * Extension is added onto the filename within this method so
	 * file names passed to it should not include the extension.
	 * @param folder
	 * @param fileName
	 * @return boolean whether or not the file was deleted
	 */
	public boolean deleteFile(File folder, String fileName) {
		return new File(folder, fileName + EXTENSION).delete();
	}

	/**
	 * Checks whether a file of the given name exists within
	 * the folder being searched. Extension is added onto the
	 * filename within this method so file names passed to it
	 * should not include the extension.
	 * @param folder
	 * @param fileName
	 * @return whether or not the file exists
	 */
	public boolean hasFile(File folder, String fileName) {
		return new File(folder, fileName + EXTENSION).exists();
	}

	/**
	 * returns an array of all the files that exist in the folder passed
	 * as an argument.
	 * @param folder
	 * @return
	 */
	public File[] getFiles(File folder) {
		return folder.listFiles();
	}

	/**
	 * returns the amount of files that are currently stored in the
	 * folder passed as an argument.
	 * @param folder
	 * @return
	 */
	public int getFileCount(File folder) {
		return folder.list().length;
	}
}