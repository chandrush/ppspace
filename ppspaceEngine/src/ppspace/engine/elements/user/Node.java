package ppspace.engine.elements.user;

import ppspace.engine.database.INamedElement;
import ppspace.engine.elements.core.Vertex;
import ppspace.geometry.Vector3d;

public class Node implements INamedElement  {

	private String name;
	
	private boolean isEmpty;
	
	private Vertex vertex;
	
	private Vector3d vector;
	
	public Node(String name, boolean isEmpty, Vector3d vector) {
		
		this.name = name;
		this.isEmpty = isEmpty;
		this.vector = vector;
	}
	
	public Node(String name, boolean isEmpty, Vector3d vector, Vertex vertex) {
		
		this(name, isEmpty, vector);
		this.vertex = vertex;
	}
	
	public String getName() {
		
		return this.name;
	}

	public boolean isEmpty() {
		
		return this.isEmpty;
	}

	public Vertex getVertex() {

		return this.vertex;
	}
	
	public void setVertex(Vertex vertex) {
		
		if (this.vertex != null)
			throw new IllegalStateException("The Node already has a Vertex setuped.");
		
		this.vertex = vertex;
	}

	public Vector3d getVector() {
		
		return this.vector;
	}

}
