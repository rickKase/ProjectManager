package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.ToDoTask;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 7/3/2017.
 */
public class Dao {

	private DataFolder dataFolder;

	/* Stores a reference to factories, builders, and transformer */
	private DocumentBuilderFactory dFactory;
	private DocumentBuilder builder;
	private TransformerFactory tFactory;
	private Transformer transformer;

	/* Parsers that do the heavy lifting */
	private ToDoParser todoParser;

	public Dao() {
		dataFolder = DataFolder.getInstance();

		dFactory = DocumentBuilderFactory.newInstance();
		tFactory = TransformerFactory.newInstance();

		initializeConfiguration();
		todoParser = new ToDoParser(builder);
	}

	/**
	 * Initializes the many configurations required for the
	 * factory objects.
	 */
	private void initializeConfiguration() {
		dFactory.setIgnoringComments(true);
		dFactory.setIgnoringElementContentWhitespace(true);
		dFactory.setValidating(true);
		dFactory.setNamespaceAware(true);
		try {
			transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			builder = dFactory.newDocumentBuilder();
			builder.setErrorHandler(new Dao.ParserErrorHandler());
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}



	/*
	* To Do Data methods
	*/

	/**
	 * reads all the currently saved ToDoTask files saved on hard
	 * disk into memory and returns them as an array.
	 * @return array of saved ToDoTasks
	 */
	public ToDoTask[] readToDos() {
		List<ToDoTask> todos = new ArrayList<>();
		for (File file : dataFolder.getFiles(DataFolder.TODO_DIRECTORY))
			todos.add(todoParser.createToDo(readDocument(file)));
		return todos.toArray(new ToDoTask[0]);
	}

	/**
	 * Reads the single ToDoTask with the given title into memory
	 * @param title of ToDoTask
	 * @return ToDoTask with the given title
	 */
	public ToDoTask readToDo(String title) {
		return todoParser.createToDo(readDocument(
				dataFolder.getFile(DataFolder.TODO_DIRECTORY, title)));
	}

	/**
	 * Writes the given ToDoTask into a file stored in persistent
	 * memory.
	 * @param toDoTask to be saved
	 */
	public void writeToDo(ToDoTask toDoTask) {
		setTransformerSchema(DataFolder.TODO_DTD);
		writeDocument(todoParser.createDocument(toDoTask),
				dataFolder.getFile(DataFolder.TODO_DIRECTORY, toDoTask.getTitle()));
	}

	/**
	 * Returns a boolean value representing whether or not a ToDoTask
	 * with the provided title is saved or not.
	 * @param title of the ToDoTask being searched for
	 * @return boolean value representing whether or not a ToDoTask with
	 * the given title is saved.
	 */
	public boolean toDoExists(String title) {
		return dataFolder.hasFile(DataFolder.TODO_DIRECTORY, title);
	}

	/**
	 * Returns the number of ToDoTasks that are saved and managed
	 * by the program.
	 * @return the number of ToDoTask files currently saved in
	 * persistent memory
	 */
	public int toDoSaveCount() {
		return dataFolder.getFileCount(DataFolder.TODO_DIRECTORY);
	}



	/*
	* Internal methods for performing basic, nonspecific versions
	* of the public methods
	*/
	/**
	 * Provides the functionality for reading any xml file into
	 * the format of a DOM object regardless of what that xml file
	 * represents.
	 * @param file
	 * @return
	 */
	private Document readDocument(File file) {
		try {
			if (!file.exists())
				throw new FileNotFoundException("File does not exist");
			return builder.parse(file);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Writes a document into the text file supplied as an
	 * argument.
	 * @param doc
	 * @param file
	 */
	private void writeDocument(Document doc, File file) {
		try {
			if (!file.exists())
				file.createNewFile();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shorthand method for telling the transformer which dtd file
	 * to used when validating the document.
	 * @param file
	 */
	private void setTransformerSchema(File file) {
		Path relative = DataFolder.TODO_DIRECTORY.toPath()
				.relativize(file.toPath());
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
				relative.toString());
	}

	/**
	 * Separate inner class used to handle all the parser exceptions that
	 * could be thrown.
	 * TODO handle exceptions likely to be encountered in these methods
	 * TODO add functionality to handle transformer exceptions as well.
	 */
	private class ParserErrorHandler implements ErrorHandler {

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}
	}

}
