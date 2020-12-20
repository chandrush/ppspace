package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Node;

public class Vertex {

	private String name;
	
	private Node node;
	
	private Arrow arrow;
	
	public Vertex(String name, Node node) {
		
		this.name = name;
		this.node = node;
	}
	
	public Vertex(String name, Node node, Arrow arrow) {
		
		this(name, node);
		this.arrow = arrow;
	}
	
	public Node getNode() {
		
		return this.node;
	}

	public Arrow getArrow() {
		
		return this.arrow;
	}

	public void setArrow(Arrow arrow) {
		
		this.arrow = arrow;
	}

	public String getName() {

		return this.name;
	}

}
