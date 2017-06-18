package com.kaselabs.projman.Model;

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

/**
 * Created by Rick on 6/12/2017.
 */
public class ProjectDao {

	private DataFolder dataFolder;

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

	public Document readProject(String title) {
		return readDocument(new File(DataFolder.PROJECTS_DIRECTORY, title + DataFolder.EXTENSION));
	}


	public Document[] readProjects() {
		File[] files = dataFolder.getFiles(DataFolder.PROJECTS_DIRECTORY);
		Document[] docs = new Document[files.length];
		for (int i = 0; i < files.length; i++) {
			docs[i] = readDocument(files[i]);
		}
		return docs;
	}




	private void setTransformerSchema(File file) {
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, file.getPath());
	}

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

	public void writeProject(Document doc, String fileName) {
		File file = new File(DataFolder.PROJECTS_DIRECTORY, fileName + DataFolder.EXTENSION);
		setTransformerSchema(DataFolder.PROJECT_DTD);
		writeDocument(doc, file);
	}





//	/**
//	 * Parses and XML file into a Document object that represents
//	 * the same data.
//	 * @param file
//	 * @return Document Object
//	 * @throws IOException
//	 */
//	private Document getXMLDocument(File file) {
//		DocumentBuilder builder;
//		try {
//			builder = dFactory.newDocumentBuilder();
//			builder.setErrorHandler(errorHandler);
//			return builder.parse(file);
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Document[] getXMLDocuments() {
//		Document[] documents = new Document[dataFolder.getDataFilesCount()];
//		File[] files = dataFolder.getProjectFiles();
//
//		for (int i = 0; i < files.length; i++) {
//			documents[i] = getXMLDocument(files[i]);
//		}
//		return documents;
//	}
//
//	public void writeXMLDocument(Document doc, String fileName) {
//		try {
//			File file = dataFolder.createProjectFile(fileName);
//			TransformerFactory tFactory = TransformerFactory.newInstance();
//			Transformer transformer = tFactory.newTransformer();
//			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dataFolder.getProjectSchemaDTD().getPath());
//			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(file);
//			transformer.transform(source, result);
//		} catch (TransformerConfigurationException e) {
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		}
//	}


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
