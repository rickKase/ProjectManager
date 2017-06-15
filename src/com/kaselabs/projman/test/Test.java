package com.kaselabs.projman.test;

import com.kaselabs.projman.Model.ProjectDao;

/**
 * Created by Rick on 6/10/2017.
 */
public class Test {

	public static void main(String[] args) {

		ProjectDao dao = new ProjectDao();
		DOMWriter domWriter = new DOMWriter(4);

		domWriter.printNodeContent(dao.getXMLDocuments()[0]);
	}

}
