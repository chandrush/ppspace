package ppspace.engine.elements.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ppspace.engine.elements.core.Facet;

public class Face {

	private String name;

	public Facet facet;
	public Set<Edge> edges;

	public Face(String name) {
		this.name = name;
		this.edges = new HashSet<>();
	}

	public Face(String name, Edge... edges) {

		this(name);
		this.edges.addAll(Arrays.asList(edges));
	}

	public String getName() {

		return this.name;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

}