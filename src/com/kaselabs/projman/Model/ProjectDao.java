package com.kaselabs.projman.Model;

import org.w3c.dom.Document;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Provides functionality for reading files into memory and then
 * parsing them into DOM objects for internal use and back again.
 * Created by Rick on 6/12/2017.
 */
public class ProjectDao {

	private DataFolder dataFolder;

	/* Stores a reference to factories, builders, and transformer */
	private DocumentBuilderFactory dFactory;
	private DocumentBuilder builder;

	private TransformerFactory tFactory;
	private Transformer transformer;

	public ProjectDao() {
		dataFolder = DataFolder.getInstance();

		dFactory = DocumentBuilderFactory.newInstance();
		tFactory = TransformerFactory.newInstance();

		initializeConfiguration();
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
			builder.setErrorHandler(new ParserErrorHandler());
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a single project, determined by that Projects title, into
	 * memory in the form of DOM object. Throws an error if the file
	 * does not exist.
	 * @param title
	 * @return a Document object representing the xml in the save file
	 */
	public Document readProject(String title) {
		return readDocument(new File(DataFolder.PROJECTS_DIRECTORY, title + DataFolder.EXTENSION));
	}

	/**
	 * Reads all files in the projects folder and returns them in the form
	 * of a Document array.
	 * @return
	 */
	public Document[] readProjects() {
		File[] files = dataFolder.getFiles(DataFolder.PROJECTS_DIRECTORY);
		Document[] docs = new Document[files.length];
		for (int i = 0; i < files.length; i++) {
			docs[i] = readDocument(files[i]);
		}
		return docs;
	}

	/**
	 * Provides the functionality for writing a Document object
	 * representing a project into a textFile of the given name.
	 * The file will be stored in the projects folder.
	 * @param doc
	 * @param fileName
	 */
	public void writeProject(Document doc, String fileName) {
		File file = new File(DataFolder.PROJECTS_DIRECTORY, fileName + DataFolder.EXTENSION);
		setTransformerSchema(DataFolder.PROJECT_DTD);
		writeDocument(doc, file);
	}

	/**
	 * Checks whether or not the data file exists in the folder
	 * or not.
	 * @param fileName
	 * @return
	 */
	public boolean projectFileExists(String fileName) {
		return dataFolder.hasFile(DataFolder.PROJECTS_DIRECTORY, fileName);
	}

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
	 * Shorthand method for setting telling the transformer which dtd file
	 * is to be used to write the document.
	 * @param file
	 */
	private void setTransformerSchema(File file) {
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, file.getPath());
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
