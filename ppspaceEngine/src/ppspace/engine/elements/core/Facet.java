package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Face;
import ppspace.geometry.Vector3d;

public class Facet  {

	private Face face;
	
	private Vector3d normalVector;
	
	private Voxel voxel;
	
	private Facet twinFacet;
	
	private Arrow outerPolygonArrow;
	
	private Arrow[] innerPolygonsArrows;
	
	public Facet(Face face, Vector3d normalVector) {
		
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
		this.twinFacet = facet;
	}
	
	public Voxel getVoxel() {

		return this.voxel;
	}
	
	public void setVoxel(Voxel voxel)
	{
		this.voxel = voxel;
	}

	public Arrow getOuter() {
		
		return this.outerPolygonArrow;
	}
	
	public void setOuterPolygonArrow(Arrow arrow)
	{
		this.outerPolygonArrow = arrow;
	}

	public Arrow[] getInner() {
		
		return this.innerPolygonsArrows;
	}

	public void setInner(Arrow[] innerPolygonArrows)
	{
		this.innerPolygonsArrows = innerPolygonArrows;
	}	
	
	public Vector3d getNormal() {
		
		return this.normalVector;
	}

}
