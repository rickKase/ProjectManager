package com.kaselabs.projman.Model;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import org.w3c.dom.DocumentType;
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
import java.io.IOException;

/**
 * Created by Rick on 6/12/2017.
 */
public class ProjectDao {

	private DataFolder dataFolder;
	private DocumentBuilderFactory factory;
	private ErrorHandler errorHandler;

	public ProjectDao() {
		dataFolder = new DataFolder();
		errorHandler = new ParserErrorHandler();
		factory = DocumentBuilderFactory.newInstance();

		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setValidating(true);
		factory.setNamespaceAware(true);
	}

	/**
	 * Parses and XML file into a Document object that represents
	 * the same data.
	 * @param file
	 * @return Document Object
	 * @throws IOException
	 */
	private Document getXMLDocument(File file) {
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(errorHandler);
			return builder.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Document[] getXMLDocuments() {
		Document[] documents = new Document[dataFolder.getDataFilesCount()];
		File[] files = dataFolder.getProjectFiles();

		for (int i = 0; i < files.length; i++) {
			documents[i] = getXMLDocument(files[i]);
		}
		return documents;
	}

	public void writeXMLDocument(Document doc, String fileName) {
		try {
			File file = dataFolder.createProjectFile(fileName);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dataFolder.getProjectSchemaDTD().getPath());
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");

//			DOMImplementation domImpl = doc.getImplementation();
//			DocumentType doctype = domImpl.createDocumentType("doctype",
//					"-//Oberon//YOUR PUBLIC DOCTYPE//EN",
//					"project.dtd");
//			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
//			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}


	}


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
