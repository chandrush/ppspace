package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Node;

public class Vertex {

	private String name;
	private Node node;

	public Vertex(String name, Node node) {

		this.name = name;
		this.node = node;
	}

	public Node getNode() {

		return this.node;
	}

	public String getName() {

		return this.name;
	}

}
