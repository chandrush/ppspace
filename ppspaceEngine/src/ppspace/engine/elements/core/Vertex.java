package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Node;

public class Vertex {

	private Node node;
	
	private Arrow arrow;
	
	public Vertex() {
		
	}
	
	public Node getNode() {
		
		return this.node;
	}

	public void setNode(Node node) {
		
		if (this.node == null)
			throw new IllegalStateException("The vertex already has a node setuped.");
		
		this.node = node;
	}

	public Arrow getArrow() {
		
		return this.arrow;
	}

	public void setArrow(Arrow arrow) {
		
		this.arrow = arrow;
	}

}
