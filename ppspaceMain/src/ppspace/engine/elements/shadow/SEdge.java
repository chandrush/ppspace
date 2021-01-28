package ppspace.engine.elements.shadow;

import ppspace.engine.elements.user.Edge;

public class SEdge {

	public Edge edge; // persistent edge in the interface model
	public boolean isEmpty; // true: point set of edge is empty
	public String node2; // base name of a node
	public String node1; // base name of a node

	public SEdge(Edge edge, boolean isEmpty, String node2, String node1) {
		super();
		this.edge = edge;
		this.isEmpty = isEmpty;
		this.node2 = node2;
		this.node1 = node1;
	}

}