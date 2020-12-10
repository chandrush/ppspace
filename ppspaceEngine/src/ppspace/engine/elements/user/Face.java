package ppspace.engine.elements.user;

import ppspace.engine.database.INamedElement;
import ppspace.engine.elements.core.Facet;

public class Face implements INamedElement {

	private String name;
	
	private Boolean isEmpty;
	
	private Facet facet;
	
	private Edge[] edges;
	
	public Face(String name, Edge[] edges, Facet facet) {
		
		boolean isEmpty = false;
		for	(int i = 0; i < edges.length; i++)
			isEmpty |= edges[i].isEmpty();
		
		this.name = name;
		this.isEmpty = isEmpty;
		this.facet = facet;
		this.edges = edges;
	}
	
	public Face(String name, Edge[] edges) {
		
		this(name, edges, null);
	}
	
	public String getName() {

		return this.name;
	}

	public boolean isEmpty() {

		return this.isEmpty;
	}

	public Facet getFacet() {
		
		return this.facet;
	}
	
	public void setFacet(Facet facet) {
		
		if (this.facet != null)
			throw new IllegalStateException("The Face already has a Facet assigned.");
		
		this.facet = facet;
	}

	public Edge[] getEdges() {
		
		return this.edges;
	}

}
