package ppspace.engine.elements.user;

import ppspace.engine.elements.core.Vertex;

public class Node {

	private String name;

	private boolean isEmpty;

	private Vertex vertex;

	private double[] x;

	public Node(String name, boolean isEmpty, double... x) {

		this.name = name;
		this.isEmpty = isEmpty;
		this.x = x;
	}

	public String getName() {

		return this.name;
	}

	public boolean isEmpty() {

		return this.isEmpty;
	}

	public void setVertex(Vertex vertex) {

		if (this.vertex != null)
			throw new IllegalStateException("The Node already has a Vertex setuped.");

		this.vertex = vertex;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public double[] getX() {
		return x;
	}

}
