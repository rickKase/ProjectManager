package com.kaselabs.projman.Model;

import java.io.File;
import java.io.IOException;

/**
 * Represents the file system of this project and provides simple
 * functionality for manipulating it.
 * TODO provide more functionality by handling all File instances internally
 * Created by Rick on 7/3/2017.
 */
public class DataFolder {

	/* internal Constants for all important folders in program */
	private static final File BASE_DIRECTORY = new File(".");
	private static final File DATA_DIRECTORY = new File(BASE_DIRECTORY,
			File.separator + "data");
	private static final File SCHEMA_DIRECTORY = new File(DATA_DIRECTORY,
			"schema");

	/* Reference to Folder and schema for all data types in program */
	public static final File TODO_DTD = new File(SCHEMA_DIRECTORY,
			"todo.dtd");
	public static final File TODO_DIRECTORY = new File(DATA_DIRECTORY,
			"todo");

	/* file extension used for stored data */
	public static final String EXTENSION = ".xml";

	/* Utilizes the Singleton model to ensure only one copy of the object */
	private static final DataFolder instance = new DataFolder();

	private DataFolder() {}

	public static DataFolder getInstance() {
		return instance;
	}

	public File getFile(File folder, String name) {
		return new File(folder, name + EXTENSION);
	}

	/**
	 * Creates the file provided as an argument if it does not
	 * already exist, otherwise does nothing.
	 * @param file
	 */
	public void createFile(File file) {
		if (file.exists())
			return;
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the file provided as an argument and returns
	 * a boolean describing if the action was successful.
	 * @param file
	 * @return boolean whether or not the file was deleted
	 */
	public boolean deleteFile(File file) {
		return file.delete();
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
