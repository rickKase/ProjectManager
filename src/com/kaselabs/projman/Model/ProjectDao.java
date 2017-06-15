package com.kaselabs.projman.Model;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Rick on 6/12/2017.
 */
public class ProjectDao {

	private DataFolder dataFolder;

	public ProjectDao() {
		dataFolder = new DataFolder();
	}

	public Document getDocument(File file) throws IOException, SAXException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;

			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);

			builder = factory.newDocumentBuilder();
			return builder.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
