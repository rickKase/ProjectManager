package com.kaselabs.projman.test;

import com.kaselabs.projman.Model.DataFolder;
import com.kaselabs.projman.Model.ProjectDao;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {

		DataFolder dataFolder = new DataFolder();
		File file = dataFolder.getProjectFiles()[0];

		try {
			System.out.println(new ProjectDao().getDocument(file).getDocumentElement().getNodeName());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

}
