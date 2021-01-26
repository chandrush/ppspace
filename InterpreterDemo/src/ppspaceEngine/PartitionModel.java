package ppspaceEngine;

import java.util.HashMap;
import java.util.Map;

import ppspaceEngine.elements.user.Edge;
import ppspaceEngine.elements.user.Node;
import ppspaceEngine.partitionModel.IPartitionModelInput;
import ppspaceEngine.partitionModel.IPartitionModelWorkStep;
import ppspaceEngine.workStepModel.ErrorWorkStep;
import ppspaceEngine.workStepModel.SplitEdgeWorkStep;
import ppspaceEngine.workStepModel.WorkStep;

/**
 * Central class of program model.
 * @author andrej.chanturidze
 */
public class PartitionModel implements IPartitionModelInput, IPartitionModelWorkStep {

	private Map<String, Node> nodes;
	
	private Map<String, Edge> edges;
	
	public PartitionModel() {

		this.nodes = new HashMap<String, Node>();
	}
	
	@Override
	public WorkStep split(String oldElementName, String newElementName1, String newElementName2) {

		if (this.edges.containsKey(oldElementName))
			return new SplitEdgeWorkStep(this);
		//else if (this.faces.containsKey(oldElementName))
		//...
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
