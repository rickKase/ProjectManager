package com.kaselabs.projman.model;

import com.kaselabs.projman.model.entities.Status;
import com.kaselabs.projman.model.entities.ToDoTask;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;

/**
 * Handles the parsing of data from an XML file representing a
 * ToDoTask to its object representation and back again.
 * Created by Rick on 7/3/2017.
 */
public class ToDoParser {

	private static final String TODO_NODE_NAME = "todo";
	private static final String TITLE_NODE_NAME = "title";
	private static final String STATUS_NODE_NAME = "status";

	private DocumentBuilder builder;

	/**
	 * Constructor for the parser that utilizes a DocumentBuilder
	 * to handle writing of data.
	 * @param builder
	 */
	public ToDoParser(DocumentBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Converts a toDoTask into an DOM structure that can
	 * be further stored as XML.
	 * @param toDoTask to be encoded
	 * @return Document object representing the ToDoTask
	 */
	public Document createDocument(ToDoTask toDoTask) {
		Document doc = builder.newDocument();
		doc.appendChild(createToDoElement(doc, toDoTask));
		return doc;
	}

	/**
	 * Handles the individual task of creating a Node that represents
	 * the ToDoTask.
	 * @param doc for which the Node should belong
	 * @param toDoTask to be represented as a Node
	 * @return Node that represents the ToDoTask
	 */
	private Node createToDoElement(Document doc, ToDoTask toDoTask) {
		Node toDoNode = doc.createElement(TODO_NODE_NAME);
		toDoNode.appendChild(createTitleNode(doc, toDoTask.getTitle()));
		toDoNode.appendChild(createCompletedNode(doc, toDoTask.getStatus()));
		for (ToDoTask sublist : toDoTask.getItems())
			toDoNode.appendChild(createToDoElement(doc, sublist));
		return toDoNode;
	}

	/**
	 * Handles the individual task of creating a Node that represents
	 * the title of a ToDoTask.
	 * @param doc for which the Node should belong
	 * @param title to be represented as a Node
	 * @return Node that represents the title
	 */
	private Node createTitleNode(Document doc, String title) {
		Node titleNode = doc.createElement(TITLE_NODE_NAME);
		titleNode.appendChild(doc.createTextNode(title));
		return titleNode;
	}

	/**
	 * Handles the individual task of creating a Node that represents
	 * the status of a ToDoTask.
	 * @param doc for which the Node should belong
	 * @param status to be represented as a Node
	 * @return Node that represents the status
	 */
	private Node createCompletedNode(Document doc, Status status) {
		Node statusNode = doc.createElement(STATUS_NODE_NAME);
		statusNode.appendChild(doc.createTextNode(status.toString()));
		return statusNode;
	}


	/**
	 * Parses a document into the ToDoTask it represents.
	 * @param doc to parsed into a ToDoTask
	 * @return ToDoTask represented by the Document
	 */
	public ToDoTask createToDo(Document doc) {
		return parseToDoFromNode(doc.getDocumentElement());
	}

	/**
	 * method that handles the parsing of the ToDoTask from
	 * the Node element and can is called recursively.
	 * @param todoNode to be parsed into a ToDoTask
	 * @return toDoTask represented by the Node
	 */
	private ToDoTask parseToDoFromNode(Node todoNode) {
		NodeList nodes = todoNode.getChildNodes();
		ToDoTask todo = new ToDoTask();
		todo.setTitle(nodes.item(0).getTextContent());
		todo.setStatus(Status.valueOf(nodes.item(1).getTextContent()));
		for (int i = 2; i < nodes.getLength(); i++)
			todo.add(parseToDoFromNode(nodes.item(i)));
		return todo;
	}
}
