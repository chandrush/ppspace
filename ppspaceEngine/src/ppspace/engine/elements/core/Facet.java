package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Face;
import ppspace.geometry.Vector3d;

public class Facet  {

	private String name;
	
	private Face face;
	
	private Vector3d normalVector;
	
	private Voxel voxel;
	
	private Facet twinFacet;
	
	private Arrow outerPolygonArrow;
	
	private Arrow[] innerPolygonsArrows;
	
	public Facet(String name, Face face, Vector3d normalVector) {
		
		this.name = name;
		this.face = face;
		this.normalVector = normalVector;
	}

	public Face getFace() {

		return this.face;
	}

	public Facet getTwinFacet() {
		
		return this.twinFacet;
	}
	
	public void setTwinFacet(Facet facet)
	{
		if (this.twinFacet != null)
			throw new IllegalStateException("The Facet already has a twin facet assigned.");
		
		this.twinFacet = facet;
	}
	
	public Voxel getVoxel() {

		return this.voxel;
	}
	
	public void setVoxel(Voxel voxel)
	{
		if (this.voxel != null)
			throw new IllegalStateException("The Facet already has a voxel assigned.");
		
		this.voxel = voxel;
	}

	public Arrow getOuter() {
		
		return this.outerPolygonArrow;
	}
	
	public void setOuterPolygonArrow(Arrow arrow)
	{
		if (this.outerPolygonArrow != null)
			throw new IllegalStateException("The Facet already has an outer polygon arrow assigned.");
		
		this.outerPolygonArrow = arrow;
	}

	public Arrow[] getInner() {
		
		return this.innerPolygonsArrows;
	}

	public void setInner(Arrow[] innerPolygonArrows)
	{
		if (this.innerPolygonsArrows != null)
			throw new IllegalStateException("The Facet already has inner polygons assigned.");
		
		this.innerPolygonsArrows = innerPolygonArrows;
	}
	
	public Vector3d getNormal() {
		
		return this.normalVector;
	}

	public String getName() {
		
		return this.name;
	}

}
