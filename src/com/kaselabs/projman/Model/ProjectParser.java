package com.kaselabs.projman.Model;

import com.kaselabs.projman.Model.entities.Project;
import com.kaselabs.projman.Model.entities.ToDoItem;
import com.kaselabs.projman.Model.entities.ToDoList;
import com.kaselabs.projman.Model.entities.ToDoTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Rick on 6/18/2017.
 */
public class ProjectParser {

	private DocumentBuilderFactory factory;

	public ProjectParser() {
		factory = DocumentBuilderFactory.newInstance();
	}

	private static final String PROJECT_NODE_NAME = "project";
	private static final String TITLE_NODE_NAME = "title";
	private static final String SUMMARY_NODE_NAME = "summary";
	private static final String TODOLIST_NODE_NAME = "todoList";
	private static final String TODOTASK_NODE_NAME = "todoTask";
	private static final String COMPLETED_NODE_NAME = "completed";


	/**
	 * Generates a document based on project.
	 * @param proj
	 * @return
	 */
	public Document projectToDocument(Project proj) {
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			doc.appendChild(createProjectElement(doc, proj));
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates an element that represents a project
	 * @param doc
	 * @param proj
	 * @return project element
	 */
	private Element createProjectElement(Document doc, Project proj) {
		Element projectNode = doc.createElement(PROJECT_NODE_NAME);

		projectNode.appendChild(createTitleElement(doc, proj.getTitle()));
		projectNode.appendChild(createSummaryElement(doc, proj.getSummary()));
		projectNode.appendChild(createToDoListElement(doc, proj.getToDoList()));

		return projectNode;
	}

	/**
	 * generates an element that represents a title
	 * @param doc
	 * @param title
	 * @return title element
	 */
	private Element createTitleElement(Document doc, String title) {
		Element titleNode = doc.createElement(TITLE_NODE_NAME);
		titleNode.appendChild(doc.createTextNode(title));
		return titleNode;
	}

	/**
	 * generates an element that represents a Summary
	 * @param doc
	 * @param summary
	 * @return summary element
	 */
	private Node createSummaryElement(Document doc, String summary) {
		Element summaryNode = doc.createElement(SUMMARY_NODE_NAME);
		summaryNode.appendChild(doc.createTextNode(summary));
		return summaryNode;
	}

	/**
	 * Generates an element that represents a To DO List
	 * @param doc
	 * @param list
	 * @return todoListElement
	 */
	private Node createToDoListElement(Document doc, ToDoList list) {
		Element listNode = doc.createElement(TODOLIST_NODE_NAME);

		listNode.appendChild(createTitleElement(doc, list.getTitle()));
		ToDoItem curItem;
		for (int i = 0; i < list.getItemCount(); i++) {
			curItem = list.getItem(i);
			if (curItem instanceof ToDoList)
				listNode.appendChild(createToDoListElement(doc, (ToDoList) curItem));
			else if (curItem instanceof ToDoTask)
				listNode.appendChild(createToDoTaskElement(doc, (ToDoTask) curItem));
		}

		return listNode;
	}

	/**
	 * Generates an element that represents a ToDoITem
	 * @param doc
	 * @param item
	 * @return ToDoItem element
	 */
	private Node createToDoTaskElement(Document doc, ToDoTask item) {
		Element itemNode = doc.createElement(TODOTASK_NODE_NAME);

		itemNode.appendChild(createTitleElement(doc, item.getTitle()));
		itemNode.appendChild(createCompletedElement(doc, item.isCompleted()));

		return itemNode;
	}

	/**
	 * Generates an element that represents a boolean value referred to
	 * as completed.
	 * @param doc
	 * @param completed
	 * @return completed tag.
	 */
	private Node createCompletedElement(Document doc, boolean completed) {
		Element completedNode = doc.createElement(COMPLETED_NODE_NAME);
		completedNode.appendChild(doc.createTextNode(Boolean.toString(completed)));
		return completedNode;
	}





	public Project documentToProject(Document doc) {
		return parseProject(doc.getLastChild());
	}

	/**
	 * parses a project node into the Project object it represents.
	 * @param root
	 * @return
	 */
	private Project parseProject(Node root) {
		Project proj;
		Node child = root.getFirstChild();
		proj = new Project(parseTitle(child));
		child = child.getNextSibling();
		proj.setSummary(parseSummary(child));
		child = child.getNextSibling();
		proj.setToDoList(parseToDoList(child));
		return proj;
	}

	/**
	 * parses a todoList node into the ToDoList Object it represents.
	 * @param node
	 * @return
	 */
	private ToDoList parseToDoList(Node node) {
		ToDoList list;
		Node child = node.getFirstChild();
		list = new ToDoList(parseTitle(child));
		while ((child = child.getNextSibling()) != null) {
			switch (child.getNodeName()) {
				case TODOLIST_NODE_NAME:
					list.addItem(parseToDoList(child));
					break;
				case TODOTASK_NODE_NAME:
					list.addItem(parseToDoTask(child));
					break;
			}
		}
		return list;
	}

	/**
	 * parses a ToDoTask node into the ToDoTask it represents.
	 * @param node
	 * @return
	 */
	private ToDoTask parseToDoTask(Node node) {
		ToDoTask item;
		Node child = node.getFirstChild();
		item = new ToDoTask(parseTitle(child));
		child = child.getNextSibling();
		item.setCompleted(parseCompleted(child));
		return item;
	}

	/**
	 * parses a Title node and returns the text saved.
	 * @param node
	 * @return
	 */
	private String parseTitle(Node node) {
		return node.getTextContent();
	}

	/**
	 * parses a Summary node and returns the text saved.
	 * @param node
	 * @return
	 */
	private String parseSummary(Node node) {
		return node.getTextContent();
	}

	/**
	 * parses a Completed node and returns the value saved.
	 * @param node
	 * @return
	 */
	private boolean parseCompleted(Node node) {
		return node.getTextContent().equals("true");
	}
}
