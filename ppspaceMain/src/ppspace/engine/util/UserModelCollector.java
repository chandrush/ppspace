package ppspace.engine.util;

import java.util.Set;
import java.util.stream.Collectors;

import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;

/**
 * Performers set operations on sub partitions of the user model
 * 
 * @author local
 */
public class UserModelCollector {

	/**
	 * @param face
	 * @return Set of all Nodes in this face
	 */
	public static Set<Node> createNodeSet(Face f) {
		return f.edges.stream().flatMap(e -> e.getNodes().stream()).collect(Collectors.toSet());
	}

}