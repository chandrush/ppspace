package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Edge;

public class Arrow {
	
	private Edge edge;
	
	//this attribute is never changes after construction
	private Vertex origin;
	
	private Arrow twinArrow;
	
	private Arrow nextArrow;
	
	private Facet facet;
	
	private Arrow rot;
	
	public Arrow(Edge edge, Vertex origin, Arrow twinArrow) {
		
		this.edge = edge;
		this.origin = origin;
		this.twinArrow = twinArrow;
	}
	
	public Arrow(String name, Edge edge, Vertex origin) {
		
		this(edge, origin, null);
	}

	public Edge getEdge() {
		
		return this.edge;
	}

	public Vertex getOrigin() {
		
		return this.origin;
	}

	public Facet getFacet() {
		
		return this.facet;
	}

	public void setFacet(Facet facet)
	{
		this.facet = facet;
	}
	
	public Arrow getTwinArrow() {
		
		return this.twinArrow;
	}
	
	public void setTwinArrow(Arrow twinArrow) {
		
		this.twinArrow = twinArrow;
	}

	public Arrow getNext() {
		
		return this.nextArrow;
	}
	
	public void setNext(Arrow arrow)
	{
		this.nextArrow = arrow;
	}

	public Arrow getRot() {
		
		return this.rot;
	}

	public void setRot(Arrow arrow)
	{
		this.rot = arrow;
	}

}

