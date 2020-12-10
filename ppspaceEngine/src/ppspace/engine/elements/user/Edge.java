package ppspace.engine.elements.user;

import ppspace.engine.database.INamedElement;
import ppspace.engine.elements.core.Arrow;

public class Edge implements INamedElement  {

	private String name;
	
	private boolean isEmpty;
	
	private boolean isUnBounded;
	
	private Arrow arrow;
	
	private Node node1;
	
	private Node node2;
	
	public Edge(String name, Node node1, Node node2, Arrow arrow) {
		
		this.name = name;
		this.isUnBounded = node1.isEmpty() || node2.isEmpty();
		this.isEmpty = node1.isEmpty() && node2.isEmpty();
		this.node1 = node1;
		this.node2 = node2;
		this.arrow = arrow;
	}
	
	public Edge(String name, Node node1, Node node2) {
		
		this(name, node1, node2, null);
	}
	
	public String getName() {

		return this.name;
	}

	public boolean isEmpty() {

		return this.isEmpty;
	}
	
	public boolean isUnBounded() {

		return this.isUnBounded;
	}

	public Arrow getArrow() {

		return this.arrow;
	}
	
	public void setArrow(Arrow arrow) {
		
		if (this.arrow != null)
			throw new IllegalStateException("The Edge already has an Arrow assigned.");
		
		this.arrow = arrow;
	}

	public Node getNode1() {
		
		return this.node1;
	}

	public Node getNode2() {
		
		return this.node2;
	}
}
