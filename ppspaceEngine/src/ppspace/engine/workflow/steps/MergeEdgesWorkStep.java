package ppspace.engine.workflow.steps;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Node;
import ppspace.engine.workflow.IMergeWorkStep;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.geometry.precision.PrecisionConfiguration;

/**
 * Work step to merge two edge domains.
 * @author andrej.chanturidze
 *
 */
public final class MergeEdgesWorkStep implements IMergeWorkStep {

	private final String oldEdgeName1;
	
	private final String oldEdgeName2;
	
	private String newEdgeName;
	
	public MergeEdgesWorkStep(String oldEdgeName1, String oldEdgeName2, String newEdgeName) {
		
		this.oldEdgeName1 = oldEdgeName1;
		this.oldEdgeName2 = oldEdgeName2;
		this.newEdgeName = newEdgeName;
	}
	
	@Override
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, PrecisionConfiguration precisionConfiguration) {

		// *** VALIDATION *** START ***
		
		// read old edges from the database
		Edge oldEdge1 = userModelElements.getEdge(this.oldEdgeName1);
		Edge oldEdge2 = userModelElements.getEdge(this.oldEdgeName2);
		
		// topological condition: old edges must be in DB
		if (oldEdge1 == null)
		{
			return WorkStepInvokeResult.Failure(String.format("The old edge [%s] doesn't exist in the database.", this.oldEdgeName1));
		}
		if (oldEdge2 == null)
		{
			return WorkStepInvokeResult.Failure(String.format("The old edge [%s] doesn't exist in the database.", this.oldEdgeName2));
		}
		
		// topological condition: DB doesn't contain an element with the name of the new edge
		if (this.newEdgeName == null || this.newEdgeName == "")
		{
			this.newEdgeName = userModelElements.autoName();
		} 
		else if (userModelElements.getEdge(this.newEdgeName) != null)
		{
			return WorkStepInvokeResult.Failure(String.format("DB contains an element with the name [%s] of the new element.", this.newEdgeName));
		}
		
		// topological condition: old edges have a common node
		Node commonNode = null;
		if (oldEdge1.getNode1().getName() == oldEdge2.getNode1().getName() ||
				oldEdge1.getNode1().getName() == oldEdge2.getNode2().getName())
		{
			commonNode = oldEdge1.getNode1();
		}
		else if (oldEdge1.getNode2().getName() != oldEdge2.getNode1().getName() ||
				oldEdge1.getNode2().getName() != oldEdge2.getNode2().getName())
		{
			commonNode = oldEdge1.getNode2();
		}
		else
		{
			return WorkStepInvokeResult.Failure(String.format("Merging edges don't have a common edge."));
		}
		
		//TODO topological condition: merging edges are the only edges are incident in the common node
		// can be implemented as brute search through all the edges
		// or by exploiting known features of the storage model 
		
		//TODO geometric condition: old edges are located on a straight line
				
		// *** VALIDATION *** END ***
		
		// *** MERGE EDGE LOGIC *** START ***
		
		// already gathered objects: oldEdge1, oldEdge2, commonNode
		
		// create new edge (here can be a motive of adding the edge on base of the existed one, like in splitEdge method)
		Node newEdgeNodeA;
		Node newEdgeNodeB;
		
		if (oldEdge1.getNode1().getName() == commonNode.getName())
			newEdgeNodeA = oldEdge1.getNode2();
		else
			newEdgeNodeA = oldEdge1.getNode1();
		
		if (oldEdge2.getNode1().getName() == commonNode.getName())
			newEdgeNodeB = oldEdge2.getNode2();
		else
			newEdgeNodeB = oldEdge2.getNode1();
		
		Edge newEdge = new Edge(this.newEdgeName, newEdgeNodeA, newEdgeNodeB);
		
		userModelElements.addEdge(newEdge);
		
		// create arrows for the new edge
		
		Arrow newArrow1 = new Arrow("a1_" + newEdge.getName(), newEdge, newEdge.getNode1().getVertex());
		Arrow newArrow2 = new Arrow("a2_" + newEdge.getName(), newEdge, newEdge.getNode2().getVertex());
		
		newEdge.setArrow(newArrow1);
		
		newArrow1.setTwinArrow(newArrow2);
		newArrow2.setTwinArrow(newArrow1);
		
		// restore attribute next for created arrows
		
		Arrow oldEdge1Arrow1 = oldEdge1.getArrow();
		Arrow oldEdge1Arrow2 = oldEdge1.getArrow().getTwinArrow();
		
		Arrow oldEdge2Arrow1 = oldEdge2.getArrow();
		Arrow oldEdge2Arrow2 = oldEdge2.getArrow().getTwinArrow();
		
		// understand directions		
		if (oldEdge1Arrow1.getTwinArrow().getOrigin().getName() == oldEdge2Arrow1.getOrigin().getName())
		{
			//  -- oldEdge1Arrow1 --> -- oldEdge2Arrow1 -->
			// <-- oldEdge1Arrow2 -- <-- oldEdge2Arrow2 --
			
			Arrow oldEdge1Arrow1Prev = oldEdge1Arrow1.getTwinArrow().getNext().getTwinArrow();
			Arrow oldEdge2Arrow2Prev = oldEdge2Arrow2.getTwinArrow().getNext().getTwinArrow();
			
			if (newArrow1.getOrigin().getName() == oldEdge1Arrow1.getOrigin().getName())
			{
				//  -- newArrow1 -->
				// <-- newArrow2 --
				
				newArrow1.setNext(oldEdge2Arrow1.getNext());
				newArrow2.setNext(oldEdge1Arrow2.getNext());
				
				oldEdge1Arrow1Prev.setNext(newArrow1);
				oldEdge2Arrow2Prev.setNext(newArrow2);
			} 
			else
			{
				//  -- newArrow2 -->
				// <-- newArrow1 --
				newArrow2.setNext(oldEdge2Arrow1.getNext());
				newArrow1.setNext(oldEdge1Arrow2.getNext());
				
				oldEdge1Arrow1Prev.setNext(newArrow2);
				oldEdge2Arrow2Prev.setNext(newArrow1);
			}
		}
		else if (oldEdge1Arrow1.getTwinArrow().getOrigin().getName() == oldEdge2Arrow2.getOrigin().getName())
		{
			//  -- oldEdge1Arrow1 --> -- oldEdge2Arrow2 -->
			// <-- oldEdge1Arrow2 -- <-- oldEdge2Arrow1 --
			
			Arrow oldEdge1Arrow1Prev = oldEdge1Arrow1.getTwinArrow().getNext().getTwinArrow();
			Arrow oldEdge2Arrow1Prev = oldEdge2Arrow1.getTwinArrow().getNext().getTwinArrow();
			
			if (newArrow1.getOrigin().getName() == oldEdge1Arrow1.getOrigin().getName())
			{
				//  -- newArrow1 -->
				// <-- newArrow2 --
				
				newArrow1.setNext(oldEdge2Arrow2.getNext());
				newArrow2.setNext(oldEdge1Arrow2.getNext());
				
				oldEdge1Arrow1Prev.setNext(newArrow1);
				oldEdge2Arrow1Prev.setNext(newArrow2);
			} 
			else
			{
				//  -- newArrow2 -->
				// <-- newArrow1 --
				newArrow2.setNext(oldEdge1Arrow1.getNext());
				newArrow1.setNext(oldEdge1Arrow2.getNext());
				
				oldEdge1Arrow1Prev.setNext(newArrow2);
				oldEdge2Arrow1Prev.setNext(newArrow1);
			}
		}
		else
		{
			if (oldEdge2Arrow1.getTwinArrow().getOrigin().getName() == oldEdge1Arrow1.getOrigin().getName()) 
			{
				//  -- oldEdge2Arrow1 --> -- oldEdge1Arrow1 -->
				// <-- oldEdge2Arrow2 -- <-- oldEdge1Arrow2 --
				
				Arrow oldEdge2Arrow1Prev = oldEdge2Arrow1.getTwinArrow().getNext().getTwinArrow();
				Arrow oldEdge1Arrow2Prev = oldEdge1Arrow2.getTwinArrow().getNext().getTwinArrow();
				
				if (newArrow1.getOrigin().getName() == oldEdge2Arrow1.getOrigin().getName())
				{
					//  -- newArrow1 -->
					// <-- newArrow2 --
					
					newArrow1.setNext(oldEdge1Arrow1.getNext());
					newArrow2.setNext(oldEdge2Arrow2.getNext());
					
					oldEdge2Arrow1Prev.setNext(newArrow1);
					oldEdge1Arrow2Prev.setNext(newArrow2);
				} 
				else
				{
					//  -- newArrow2 -->
					// <-- newArrow1 --
					
					newArrow2.setNext(oldEdge1Arrow1.getNext());
					newArrow1.setNext(oldEdge2Arrow2.getNext());
					
					oldEdge2Arrow1Prev.setNext(newArrow2);
					oldEdge1Arrow2Prev.setNext(newArrow1);
				}
			} 
			else
			{
				//  -- oldEdge2Arrow2 --> -- oldEdge1Arrow1 -->
				// <-- oldEdge2Arrow1 -- <-- oldEdge1Arrow2 --
				
				Arrow oldEdge2Arrow2Prev = oldEdge2Arrow2.getTwinArrow().getNext().getTwinArrow();
				Arrow oldEdge1Arrow2Prev = oldEdge1Arrow2.getTwinArrow().getNext().getTwinArrow();
				
				if (newArrow1.getOrigin().getName() == oldEdge2Arrow2.getOrigin().getName())
				{
					//  -- newArrow1 -->
					// <-- newArrow2 --
					
					newArrow1.setNext(oldEdge1Arrow1.getNext());
					newArrow2.setNext(oldEdge2Arrow1.getNext());
					
					oldEdge2Arrow2Prev.setNext(newArrow1);
					oldEdge1Arrow2Prev.setNext(newArrow2);
				} 
				else
				{
					//  -- newArrow2 -->
					// <-- newArrow1 --
					
					newArrow2.setNext(oldEdge1Arrow1.getNext());
					newArrow1.setNext(oldEdge2Arrow1.getNext());
					
					oldEdge2Arrow2Prev.setNext(newArrow2);
					oldEdge1Arrow2Prev.setNext(newArrow1);
				}
			}
		}
		
		// restore attribute rot and facet for created arrows
		if (newArrow1.getOrigin().getName() == oldEdge1Arrow1.getOrigin().getName())
		{
			newArrow1.setRot(oldEdge1Arrow1.getRot());
			newArrow2.setRot(oldEdge1Arrow2.getRot());
			
			newArrow1.setFacet(oldEdge1Arrow1.getFacet());
			newArrow2.setFacet(oldEdge1Arrow2.getFacet());
		}
		else if (newArrow1.getOrigin().getName() == oldEdge1Arrow2.getOrigin().getName())
		{
			newArrow1.setRot(oldEdge1Arrow2.getRot());
			newArrow2.setRot(oldEdge1Arrow1.getRot());
			
			newArrow1.setFacet(oldEdge1Arrow2.getFacet());
			newArrow2.setFacet(oldEdge1Arrow1.getFacet());
		} 
		else if (newArrow1.getOrigin().getName() == oldEdge2Arrow1.getOrigin().getName())
		{
			newArrow1.setRot(oldEdge2Arrow1.getRot());
			newArrow2.setRot(oldEdge2Arrow2.getRot());
			
			newArrow1.setFacet(oldEdge2Arrow1.getFacet());
			newArrow2.setFacet(oldEdge2Arrow2.getFacet());
		}
		else
		{
			newArrow1.setRot(oldEdge2Arrow2.getRot());
			newArrow2.setRot(oldEdge2Arrow1.getRot());
			
			newArrow1.setFacet(oldEdge2Arrow2.getFacet());
			newArrow2.setFacet(oldEdge2Arrow1.getFacet());
		}
		
		//remove old edges
		userModelElements.removeEdge(oldEdgeName1);
		userModelElements.removeEdge(oldEdgeName2);
		
		// remove old node
		userModelElements.removeNode(commonNode.getName());
		
		// *** MERGE EDGE LOGIC *** END ***
		
		return WorkStepInvokeResult.Success();
	}

}
