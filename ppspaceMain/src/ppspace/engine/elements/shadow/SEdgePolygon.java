package ppspace.engine.elements.shadow;

import java.util.LinkedList;
import java.util.List;

import ppspace.engine.elements.user.Edge;

public class SEdgePolygon {

	public List<Edge> polygon; // polygon formed by edges of a face

	public SEdgePolygon() {

		this.polygon = new LinkedList<Edge>();

	}

}