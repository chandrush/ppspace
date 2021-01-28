package ppspace.engine.util;

import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.core.Facet;
import ppspace.engine.elements.core.Vertex;

/**
 * Provides static methods to search core model objects in a sub partitioning.
 * 
 * @author local
 */
public class CoreModelCollector {

	/**
	 * Finds an adjacent arrow of a vertex in a facet by traversing the polygons
	 * Firstly, the outer polygon is traversed, second the inner are checked one
	 * after another.
	 */
	public static Arrow findArrow(Facet facet, Vertex vertex) {

		Arrow arrow = findArrowInPolygone(facet.outer, vertex);

		if (arrow == null) {

			for (Arrow inner : facet.inner) {

				arrow = findArrowInPolygone(inner, vertex);

				if (arrow != null) {
					return arrow;
				}
			}
		} else {
			return arrow;
		}
		throw new IllegalArgumentException("Vertex is not part of this Facet");

	}

	/**
	 * Traverses one polygon (referenced by a pivotal arrow) to find a specific
	 * arrow.
	 * 
	 * @param start  - pivotal arrow
	 * @param vertex
	 * @return null if no arrow with this vertex was found in that polygon
	 */
	public static Arrow findArrowInPolygone(Arrow start, Vertex vertex) {
		Arrow current = start;

		do {

			if (current.getOrigin().equals(vertex)) {
				return current;
			} else {
				current = current.next;
			}
		} while (current.equals(start) == false);

		return null;

	}

}