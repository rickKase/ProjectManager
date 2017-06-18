package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;
import com.kaselabs.projman.Model.entities.ToDoItem;
import com.kaselabs.projman.Model.entities.ToDoList;
import com.kaselabs.projman.Model.entities.ToDoListItem;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Rick on 6/12/2017.
 */
public class EntityManager {

	private static EntityManager instance = new EntityManager();
	private ProjectDao dao = new ProjectDao();

	private EntityManager() {
	}

	public static EntityManager getInstance() {
		return instance;
	}


	/* ***********************************************************
	 * These Methods all pertain to the parsing of a DOM object
	 * into the in program entity it is supposed to represent.
	 * ***********************************************************/
	public Project[] getSavedProjects() {
		Document[] docs = loadData();
		Project[] projs = new Project[docs.length];
		for (int i = 0; i < docs.length; i++) {
			projs[i] = interpretDOMDocument(docs[i]);
		}
		return projs;
	}

	private Document[] loadData() {
		return dao.getXMLDocuments();
	}

	private Project interpretDOMDocument(Document doc) {
		DocumentType type = doc.getDoctype();
		Project proj = null;
		switch (type.getNodeName()) {
			case "project":
				proj = interpretAsProject(doc.getLastChild());
				break;
		}
		return proj;
	}

	private Project interpretAsProject(Node root) {
		return parseProject(root);
	}

	private Project parseProject(Node root) {
		Project proj;
		Node child = root.getFirstChild();
		proj = new Project(parseTitle(child));
		child = child.getNextSibling();
		proj.setSummary(parseSummary(child));
		child = child.getNextSibling();
		proj.setList(parseToDoList(child));
		return proj;
	}

	private ToDoList parseToDoList(Node node) {
		ToDoList list;
		Node child = node.getFirstChild();
		list = new ToDoList(parseTitle(child));
		while ((child = child.getNextSibling()) != null) {
			switch (child.getNodeName()) {
				case "todoList":
					list.addItem(parseToDoList(child));
					break;
				case "todoItem":
					list.addItem(parseToDoItem(child));
					break;
			}
		}
		return list;
	}

	private ToDoItem parseToDoItem(Node node) {
		ToDoItem item;
		Node child = node.getFirstChild();
		item = new ToDoItem(parseTitle(child));
		child = child.getNextSibling();
		item.setCompleted(parseCompleted(child));
		return item;
	}

	private String parseTitle(Node node) {
		return node.getTextContent();
	}

	private String parseSummary(Node node) {
		return node.getTextContent();
	}

	private boolean parseCompleted(Node node) {
		return node.getTextContent().equals("true");
	}


	/* ***********************************************************
	 * These Methods all pertain to the parsing of a DOM object
	 * into the in program entity it is supposed to represent.
	 * ***********************************************************/
	public void saveProject(Project proj) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.newDocument();
			doc.appendChild(createProjectElement(doc, proj));
			dao.writeXMLDocument(doc, proj.getTitle() + ".xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Element createProjectElement(Document doc, Project proj) {
		Element projectNode = doc.createElement("project");

		projectNode.appendChild(createTitleElement(doc, proj.getTitle()));
		projectNode.appendChild(createSummaryElement(doc, proj.getSummary()));
		projectNode.appendChild(createToDoListElement(doc, proj.getList()));

		return projectNode;
	}

	public Element createTitleElement(Document doc, String title) {
		Element titleNode = doc.createElement("title");
		titleNode.appendChild(doc.createTextNode(title));
		return titleNode;
	}

	private Node createSummaryElement(Document doc, String summary) {
		Element summaryNode = doc.createElement("summary");
		summaryNode.appendChild(doc.createTextNode(summary));
		return summaryNode;
	}

	private Node createToDoListElement(Document doc, ToDoList list) {
		Element listNode = doc.createElement("todoList");

		listNode.appendChild(createTitleElement(doc, list.getTitle()));
		ToDoListItem curItem;
		for (int i = 0; i < list.getItemCount(); i++) {
			curItem = list.getItem(i);
			if (curItem instanceof ToDoList)
				listNode.appendChild(createToDoListElement(doc, (ToDoList) curItem));
			else if (curItem instanceof ToDoItem)
				listNode.appendChild(createToDoItemElement(doc, (ToDoItem) curItem));;
		}

		return listNode;
	}

	private Node createToDoItemElement(Document doc, ToDoItem item) {
		Element itemNode = doc.createElement("todoItem");

		itemNode.appendChild(createTitleElement(doc, item.getTitle()));
		itemNode.appendChild(createCompletedElement(doc, item.isCompleted()));

		return itemNode;
	}

	private Node createCompletedElement(Document doc, boolean completed) {
		Element completedNode = doc.createElement("completed");
		completedNode.appendChild(doc.createTextNode(Boolean.toString(completed)));
		return completedNode;
	}

	/* ***********************************************************
	 * These Methods are essentially short hand Constructors for
	 * the various entities in the program.
	 * ***********************************************************/
	public Project createProject(String title) {
		return new Project(title);
	}

	public Project createProject(String title, String summary) {
		Project proj = new Project(title);
		proj.setSummary(summary);
		return proj;
	}

	public Project createProject(String title, String summary, ToDoList list) {
		Project proj = new Project(title);
		proj.setSummary(summary);
		proj.setList(list);
		return proj;
	}

	public ToDoList createToDoList(String title) {
		return new ToDoList(title);
	}

	public ToDoList createToDoList(String title, ToDoListItem list) {
		ToDoList theList = new ToDoList(title);
		theList.addItem(list);
		return theList;
	}

	public ToDoItem createToDoItem(String title) {
		return new ToDoItem(title);
	}

	public ToDoItem createToDoItem(String title, boolean completed) {
		ToDoItem item = new ToDoItem(title);
		item.setCompleted(completed);
		return item;
	}
}