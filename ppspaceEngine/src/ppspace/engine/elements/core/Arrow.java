package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Edge;

public class Arrow {
	
	private String name;
	
	private Edge edge;
	
	private Vertex origin;
	
	private Arrow twinArrow;
	
	private Arrow nextArrow;
	
	private Facet facet;
	
	private Arrow rot;
	
	public Arrow(String name, Edge edge, Vertex origin, Arrow twinArrow) {
		
		this.name = name;
		this.edge = edge;
		this.origin = origin;
		this.twinArrow = twinArrow;
	}
	
	public Arrow(String name, Edge edge, Vertex origin) {
		
		this(name, edge, origin, null);
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
		if (this.facet != null)
			throw new IllegalStateException("The Arrow already has a facet assigned.");
		
		this.facet = facet;
	}
	
	public Arrow getTwinArrow() {
		
		return this.twinArrow;
	}
	
	public void setTwinArrow(Arrow twinArrow) {
		
		if (this.twinArrow != null)
			throw new IllegalStateException("The Twin Arrow already setuped.");
		
		this.twinArrow = twinArrow;
	}

	public Arrow getNext() {
		
		return this.nextArrow;
	}
	
	public void setNext(Arrow arrow)
	{
		if (this.nextArrow != null)
			throw new IllegalStateException("The Arrow already has a next arrow assigned.");
		
		this.nextArrow = arrow;
	}

	public Arrow getRot() {
		
		return this.rot;
	}

	public void setRot(Arrow arrow)
	{
		if (this.rot != null)
			throw new IllegalStateException("The Arrow already has a rot arrow assigned.");
		
		this.rot = arrow;
	}

	public String getName() {
		
		return this.name;
	}	
}

