package ppspaceEngine;

import java.util.ArrayList;
import java.util.Map;

import ppspaceEngine.elements.user.Edge;
import ppspaceEngine.elements.user.Node;
import ppspaceEngine.shadowObjects.SEdge;
import ppspaceEngine.shadowObjects.SNode;

/**
 * Central class of program model.
 * @author andrej.chanturidze
 */
public class PartitionModel {

	private Map<String, Node> userModelNodes;
	
	private Map<String, Edge> userModelEdges;
	
	private ArrayList<SNode> shadowNodes;
	
	private ArrayList<SEdge> shadowEdges;
	
	public PartitionModel() {
		
		// TODO Auto-generated constructor stub
	}
	
	public AcceptResult split(String oldElementName, String newElementName1, String newElementName2) {

		//check state machine: is split operation allowed
		
		if (this.userModelEdges.containsKey(oldElementName))
		{
			// check state machine: is split allowed
			//if yes
			return AcceptResult.Success();
			//if not
			//return AcceptResult.Error(0, "wrong state");
		}
		//else if (this.faces.containsKey(oldElementName))
		//...
		else
			return AcceptResult.Error(0, "Can't find any element by name [<oldElementName>]");
	}
	
	public AcceptResult addNode(String nodeName, double x1, double x2, double x3)
	{
		//check state machine: is addNode command allowed
		
		//create SNode
		SNode snode = new SNode();
		this.shadowNodes.add(snode);
		
		return AcceptResult.Success();
		//or
		//return AcceptResult.Error(0, "wrong state");
	}
	
	public AcceptResult addEdge(String nodeName, double x1, double x2, double x3)
	{
		//check state machine: is addEdge command allowed
		
		//create SEdge
		SEdge sedge = new SEdge();
		this.shadowEdges.add(sedge);
		
		return AcceptResult.Success();
		//or
		//return AcceptResult.Error(0, "wrong state");
	}
	
	public AcceptResult doCommand() {
		
		//check state machine: is do command allowed
		
		// if yes
		// ... perform work step logic
		// ... ... operate with shadow elements
		
		return AcceptResult.Success();
		//or
		//return AcceptResult.Error(0, "wrong state"); 
	}
}
