package ppspace.engine;

import java.util.HashMap;
import java.util.Map;

import ppspace.engine.elements.user.Cell;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;

/**
 * Central class of program model.
 * 
 * @author andrej.chanturidze
 */
public class PartitionModel implements IPartitionModelInput, IPartitionModelWorkStep {

	public Map<String, Node> nodes;
	public Map<String, Edge> edges;
	public Map<String, Face> faces;
	public Map<String, Cell> cells;

	public Map<Integer, Integer> objectCounter;

	/**
	 * The Partition in initialized by creating the user object maps.
	 */
	public PartitionModel() {

		this.cells = new HashMap<>();
		this.edges = new HashMap<>();
		this.faces = new HashMap<>();
		this.nodes = new HashMap<>();

		objectCounter = new HashMap<Integer, Integer>();
	}

	@Override
	public InputResult split(String oldElementName, String newElementName1, String newElementName2) {

		if (this.edges.containsKey(oldElementName))
			return new SplitEdgeWorkStep(this);
		// else if (this.faces.containsKey(oldElementName))
		// ...
		else
			return new ErrorWorkStep(this, 0, "Can't find any element by name [<oldElementName>]");
	}

	@Override
	public WorkStep merge(String oldElementName1, String oldElementName2, String newElementName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutoName() {

		return null;
	}

}
