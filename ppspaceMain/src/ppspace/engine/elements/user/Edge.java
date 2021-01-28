package ppspace.engine.elements.user;

import java.util.Arrays;
import java.util.Set;

import ppspace.engine.elements.core.Arrow;

public class Edge {

	private String name;
	private Set<Node> nodes;
	private boolean isImaginary;
	private boolean isUnBounded;

	public Arrow arrow;

	public Edge(String name, Node node1, Node node2) {

		this.name = name;

		this.nodes.addAll(Arrays.asList(node1, node2));

		this.isImaginary = node1.isEmpty() && node2.isEmpty();
		this.isUnBounded = node1.isEmpty() || node2.isEmpty();
	}

	public String getName() {

		return this.name;
	}

	public boolean isEmpty() {

		return this.isImaginary;
	}

	public boolean isUnBounded() {

		return this.isUnBounded;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

}
