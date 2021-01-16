package ppspace.engine.workflow.steps;

import java.util.ArrayList;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.core.Facet;
import ppspace.engine.elements.core.Vertex;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Node;
import ppspace.engine.workflow.ISplitWorkStep;
import ppspace.engine.workflow.SplitWorkStepBase;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.engine.workflow.commands.AddNodeWorkStepCommand;
import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.IPrecisionConfiguration;

/**
 * Split edge work step splits one edge by a node.
 * @author andrej.chanturidze
 *
 */
public class SplitEdgeWorkStep extends SplitWorkStepBase implements ISplitWorkStep {

	private final String oldEdgeName;
	
	private String newEdgeName1;
	
	private String newEdgeName2;
 
	public SplitEdgeWorkStep(String oldEdgeName, String newEdgeName1, String newEdgeName2) {
		
		this.oldEdgeName = oldEdgeName;
		this.newEdgeName1 = newEdgeName1;
		this.newEdgeName2 = newEdgeName2;
	}
	
	@Override
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, IPrecisionConfiguration precisionConfiguration) {
		
		// *** VALIDATION *** START ***
		
		// read old edge from the database
		Edge oldEdge = userModelElements.getEdge(this.oldEdgeName);
		
		// topological condition: an old edge must to be in DB
		if (oldEdge == null)
		{
			return WorkStepInvokeResult.Failure(String.format("The old edge [%s] doesn't exist in the database.", this.oldEdgeName));
		}
		
		// request new names for edges if they are not defined in WorkStep constructor
		if (this.newEdgeName1 == "")
		{
			this.newEdgeName1 = userModelElements.autoName();
		}
		else
		{
			if (userModelElements.getEdge(this.newEdgeName1) != null)
				return WorkStepInvokeResult.Failure(String.format("Name conflict: already there is an edge [%s] in the database.", this.newEdgeName1));
		}
		
		if (this.newEdgeName2 == "")
		{
			this.newEdgeName2 = userModelElements.autoName();
		}
		else
		{
			if (userModelElements.getEdge(this.newEdgeName2) != null)
				return WorkStepInvokeResult.Failure(String.format("Name conflict: already there is an edge [%s] in the database.", this.newEdgeName2));
		}
		
		// input data condition: list of commands must contain one command - addNode
		if (this.commands.size() != 1)
		{
			return WorkStepInvokeResult.Failure(String.format("A WorkStep commands list shuould contains only one command, but it contains [%d] commands.", this.commands.size()));
		}
		
		//topological condition: in the database must not be a node with the name of added node
		AddNodeWorkStepCommand addNodeCommand = (AddNodeWorkStepCommand)this.commands.get(0);
		Node checkNode = userModelElements.getNode(addNodeCommand.getName());
		if (checkNode != null)
		{
			return WorkStepInvokeResult.Failure(String.format("User model already contains a node with name [%s] of the added node.", addNodeCommand.getName()));
		}
		
		//geometric condition: current model doesn't contain a node with the coordinates of added node
		checkNode = userModelElements.getNode(addNodeCommand.getLocation());
		if (checkNode != null)
		{
			return WorkStepInvokeResult.Failure(String.format("User model already contains a node with coordinates [%s] of the added node.", addNodeCommand.getLocation().toString()));
		}
		
		if (oldEdge.isEmpty())
		{
			//TODO geometric condition: the new node lies on the straight line defined by the old node and direction vector of the edge
		} 
		else if (!oldEdge.isUnBounded())
		{
			//TODO geometric condition: the new node is an interior point of the old edge (located after nonEmpty node in the direction of the empty node)
		} 
		else
		{
			//TODO geometric condition: the new node is an interior point of the old edge
		}
		
		// *** VALIDATION *** END ***
		
		// *** SPLIT EDGE LOGIC *** START ***
		
		// already have from the validation stage:
		// - AddNodeWorkStepCommand addNodeCommand - a command that adds a node
		// - IEdge oldEdge - an edge to be split
		
		//add new Node to the User model and corresponding vertex to the Core model
		Node splitNode = new Node(addNodeCommand.getName(), false, addNodeCommand.getLocation());
		Vertex splitNodeVertex = new Vertex("v_" + splitNode.getName(), splitNode);
		splitNode.setVertex(splitNodeVertex);
		
		userModelElements.addNode(splitNode);
		
		//add new Edges to the User model
		Edge newEdge1 = new Edge(this.newEdgeName1, oldEdge.getNode1(), splitNode);
		Edge newEdge2 = new Edge(this.newEdgeName2, oldEdge.getNode2(), splitNode);
		userModelElements.addEdge(newEdge1);
		userModelElements.addEdge(newEdge2);
		
		// *** split every arrow from the dihedral cycle of the old edge *** start ***
		Arrow oldArrow1 = oldEdge.getArrow();
		Arrow oldArrow2 = oldEdge.getArrow().getTwinArrow();
		
		//rearrange oldArrow1 and oldArrow2 variables so
		// the start vertex of the oldArrow1 will belong to the newEdge1
		// the start vertex of the oldArrow2 will belong to the newEdge2
		Vector3d oldArrow1OriginLocation = oldArrow1.getOrigin().getNode().getVector();
		if (!oldEdge.getNode1().getVector().equal(oldArrow1OriginLocation, precisionConfiguration))
		{
			Arrow exchangeArrow = oldArrow2;
			oldArrow2 = oldArrow1;
			oldArrow1 = exchangeArrow;
		}
		//
		
		ArrayList<Arrow> edge1Arrow1 = new ArrayList<Arrow>();
		ArrayList<Arrow> edge2Arrow1 = new ArrayList<Arrow>();
		ArrayList<Arrow> edge1Arrow2 = new ArrayList<Arrow>();
		ArrayList<Arrow> edge2Arrow2 = new ArrayList<Arrow>();
		 
		// run through arrows in dihedral cycle of the oldArrow1 and create 2 new arrows for the every one
		Arrow currentArrow = oldArrow1;
		do {
			
			Arrow a = new Arrow("a1_" + newEdge1.getName(), newEdge1, currentArrow.getOrigin());
			Arrow b = new Arrow("a1_" + newEdge2.getName(), newEdge2, splitNodeVertex);
			edge1Arrow1.add(a);
			edge2Arrow1.add(b);
			currentArrow = currentArrow.getRot();
			
			a.setFacet(currentArrow.getFacet());
			b.setFacet(currentArrow.getFacet());
			
		} while (currentArrow != null && currentArrow.getName() != oldArrow1.getName());
		
		currentArrow = oldArrow2;
		do {
			
			Arrow a = new Arrow("a2_" + newEdge2.getName(), newEdge2, currentArrow.getOrigin());
			Arrow b = new Arrow("a2_" + newEdge1.getName(), newEdge1, splitNodeVertex);
			edge2Arrow2.add(a);
			edge1Arrow2.add(b);
			currentArrow = currentArrow.getRot();
			
			a.setFacet(currentArrow.getFacet());
			b.setFacet(currentArrow.getFacet());
			
		} while (currentArrow != null && currentArrow.getName() != oldArrow2.getName());
		
		// *** split every arrow from the dihedral cycle of the old edge *** end ***
		
		// set attribute "twin" for arrows
		int s = edge1Arrow1.size();
		for (int i = 0; i < s / 2 + s % 2; i++)
		{
			Arrow a = edge1Arrow1.get(i);
			Arrow b = edge1Arrow2.get((s - i) % s);
			
			a.setTwinArrow(b);
			b.setTwinArrow(a);
		}
		
		s = edge2Arrow1.size();
		for (int i = 0; i < s / 2 + s % 2; i++)
		{
			Arrow a = edge2Arrow1.get(i);
			Arrow b = edge2Arrow2.get((s - i) % s);
			
			a.setTwinArrow(b);
			b.setTwinArrow(a);
		}
		
		// set attribute next for arrows
		s = edge1Arrow1.size();
		for (int i = 0; i < s; i++)
		{
			Arrow a = edge1Arrow1.get(i);
			Arrow b = edge2Arrow1.get(i);
			
			a.setNext(b);
			b.setNext(oldArrow1.getNext());
			
			oldArrow1.getTwinArrow().getNext().getTwinArrow().setNext(a);
		}
		
		s = edge2Arrow1.size();
		for (int i = 0; i < s; i++)
		{
			Arrow a = edge2Arrow2.get(i);
			Arrow b = edge1Arrow2.get(i);
			
			a.setNext(b);
			b.setNext(oldArrow2.getNext());
			
			oldArrow2.getTwinArrow().getNext().getTwinArrow().setNext(a);
		}
		
		// set attribute rot for arrows
		java.util.function.Consumer<ArrayList<Arrow>> setRotFunc = (bundle) -> {
			for (int i = 0; i < bundle.size() - 1; i++)
			{
				bundle.get(i).setRot(bundle.get(i + 1));
			}
			bundle.get(bundle.size() - 1).setRot(bundle.get(0));
		};
		setRotFunc.accept(edge1Arrow1);
		setRotFunc.accept(edge2Arrow1);
		setRotFunc.accept(edge1Arrow2);
		setRotFunc.accept(edge2Arrow2);

		// set attribute arrow for vertices
		edge1Arrow1.get(0).getOrigin().setArrow(edge1Arrow1.get(0));
		splitNodeVertex.setArrow(edge2Arrow1.get(0));
		edge2Arrow2.get(0).getOrigin().setArrow(edge2Arrow2.get(0));
		
		// *** process facets references to polygons *** start ***
		
		String oldArrow1Name = oldArrow1.getName();
		Arrow oldArrow1Next = oldArrow1.getNext();
		String oldArrow2Name = oldArrow2.getName();
		Arrow oldArrow2Next = oldArrow2.getNext();
		java.util.function.Consumer<Facet> processFacet = (facet) -> {
			
			if (facet.getOuter().getName() == oldArrow1Name)
			{
				facet.setOuterPolygonArrow(oldArrow1Next);
			}
			if (facet.getOuter().getName() == oldArrow2Name)
			{
				facet.setOuterPolygonArrow(oldArrow2Next);
			}
			
			Arrow[] facetInnerPolygons = facet.getInner();
			if (facetInnerPolygons != null)
			{
				for (int i = 0; i < facetInnerPolygons.length; i++)
				{
					if (facetInnerPolygons[i].getName() == oldArrow1Name)
						facetInnerPolygons[i] = oldArrow1Next;
					if (facetInnerPolygons[i].getName() == oldArrow2Name)
						facetInnerPolygons[i] = oldArrow2Next;
				}
				facet.setInner(facetInnerPolygons);
			}
		};
		
		currentArrow = oldArrow1;
		do {
			
			Facet facet = currentArrow.getFacet();
			
			processFacet.accept(facet);
			processFacet.accept(facet.getTwinFacet());
			
			currentArrow = currentArrow.getRot();
			
		} while (currentArrow != null && currentArrow.getName() != oldArrow1.getName());

		//set references to arrows in edges
		newEdge1.setArrow(edge1Arrow1.get(0));
		newEdge2.setArrow(edge2Arrow1.get(0));
		
		// *** process facets references to polygons *** end ***
		
		//remove old edge
		userModelElements.removeEdge(this.oldEdgeName);
		
		return WorkStepInvokeResult.Success();
	}

	@Override
	protected SplitRank getRangk() {
		
		return SplitRank.Edge;
	}

}
