package com.kaselabs.projman.test;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * Created by Rick on 6/15/2017.
 */
public class DOMWriter {

	int indent = 0;
	String baseIndent;

	public DOMWriter(int indentWidth) {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < indentWidth ;i++)
			build.append(" ");
		this.baseIndent = build.toString();
	}



	public void printNodeContent(Node node) {
		outputIndentation();
		int type = node.getNodeType();

		switch (type) {
			case Node.DOCUMENT_NODE:
				System.out.print("DOC:");
				printlnCommon(node);
				break;
			case Node.ELEMENT_NODE:
				System.out.print("ELEM:");
				printlnCommon(node);

				NamedNodeMap atts = node.getAttributes();
				indent += 2;
				for (int i = 0; i < atts.getLength(); i++)
					printNodeContent(atts.item(i));
				indent -= 2;
				break;
			case Node.TEXT_NODE:
				System.out.print("TEXT:");
				printlnCommon(node);
				break;
			case Node.ATTRIBUTE_NODE:
				System.out.print("ATTR:");
				printlnCommon(node);
				break;
			default:
				System.out.print("UNSUPPORTED NODE " + type + ":");
				printlnCommon(node);
				break;
		}

		indent++;
		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling())
			printNodeContent(child);
		indent--;
	}

	public void printlnCommon(Node node) {
		System.out.print("nodeName=\"" + node.getNodeName() + "\"");

		String val = node.getNamespaceURI();
		if (val != null)
			System.out.print("uri=\"" + val +"\"");

		val = node.getPrefix();
		if (val != null)
			System.out.print("pre=\"" + val +"\"");

		val = node.getLocalName();
		if (val != null)
			System.out.print("local=\"" + val +"\"");

		val = node.getNodeValue();
		if (val != null) {
			System.out.print(" nodeValue=");
			if (val.trim().equals(""))
				System.out.print("[WS]");
			else
				System.out.print("\"" + node.getNodeValue() + "\"");
		}
		System.out.println();
	}

	public void outputIndentation() {
		for (int i = 0; i < indent; i++) {
			System.out.print(baseIndent);
		}
	}
}