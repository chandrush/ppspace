package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Edge;

public class Arrow {

	private String name;
	private Edge edge;
	private Vertex origin;

	public Arrow next;
	public Arrow twin;
	public Arrow rot;

	public Facet facet;

	public Arrow(String name, Edge edge, Vertex origin) {

		this.name = name;
		this.edge = edge;
		this.origin = origin;
	}

	public String getName() {

		return this.name;
	}

	public Vertex getOrigin() {

		return this.origin;
	}

	public Edge getEdge() {

		return this.edge;
	}

}