package ppspace.engine.elements.shadow;

import java.util.HashSet;
import java.util.Set;

import ppspace.engine.elements.user.Face;

public class SSurface {

	public Set<SRimPolygon> rimPolygonSet;// rim polygons of this surface
	public Set<Face> faceSet; // faces of the surface

	public SSurface() {
		this.faceSet = new HashSet<Face>();
		this.rimPolygonSet = new HashSet<SRimPolygon>();
	}

}