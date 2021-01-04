package ppspace.engine.workflow.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.core.Facet;
import ppspace.engine.elements.core.Vertex;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;
import ppspace.engine.workflow.ISplitWorkStep;
import ppspace.engine.workflow.SplitWorkStepBase;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.engine.workflow.commands.AddEdgeWorkStepCommand;
import ppspace.engine.workflow.commands.AddNodeWorkStepCommand;
import ppspace.engine.workflow.commands.IWorkStepCommand;
import ppspace.engine.workflow.commands.IWorkStepCommand.WorkStepCommandKind;
import ppspace.geometry.Polygon3d;
import ppspace.geometry.Vector3d;
import ppspace.geometry.algorithms.pointToPolygonLocation.PointToPolygonLocationAlgorithmFactory;
import ppspace.geometry.algorithms.pointToPolygonLocation.PointToPolygonLocationAlgorithmResult;
import ppspace.geometry.algorithms.pointToPolygonLocation.PointToPolygonLocationAlgorithmResult.PointToPolygonLocationKind;
import ppspace.geometry.precision.PrecisionConfiguration;

/**
 * Split face work step splits a face. 
 * @author andrej.chanturidze
 *
 */
public class SplitFaceWorkStep extends SplitWorkStepBase implements ISplitWorkStep {

	private String newFaceName1;

	private String newFaceName2;

	private final String oldFaceName;
	
	public SplitFaceWorkStep(String oldFaceName, String newFaceName1, String newFaceName2) {
		
		this.oldFaceName = oldFaceName;
		this.newFaceName1 = newFaceName1;
		this.newFaceName2 = newFaceName2;
	}

	@Override
	protected SplitRank getRangk() {
		
		return SplitRank.Face;
	}
	
	@Override
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, PrecisionConfiguration precisionConfiguration) {
		
		// *** PREPARE DATA *** START ***
		
		//fetch the old face from db
		Face oldFace = userModelElements.getFace(this.oldFaceName);
		
		//gather added nodes
		ArrayList<Node> addedNodes = this.gatherAddedNodes(userModelElements);
		
		//gather added edges
		ArrayList<AddedEdgeContainer> addedEdges = this.gatherAddedEdges(userModelElements, addedNodes);

		// *** PREPARE DATA *** END ***
		
		
		// *** VALIDATION *** START ***
		// *** VALIDATION *** END ***
		
		
		// *** SPLIT FACE LOGIC *** START ***
		
		// * create vertices for added nodes
		this.createVerticesForAddedNodes(userModelElements, addedNodes);
			
		// * find a node from which to start finding cycles
		SearchStart searchStart = this.findSearchStart(addedEdges, oldFace.getFacet().getOuter());
		
		if (!searchStart.isEmpty())
		{
			// * find cycles
			ArrayList<Arrow> cycle1 = this.buildCycle(searchStart.boundaryArrow, false, searchStart.boundaryArrow.getFacet(), addedEdges, 0);
			ArrayList<Arrow> cycle2 = this.buildCycle(searchStart.addedArrow, true, searchStart.boundaryArrow.getFacet(), addedEdges, 1);
			
			// * setup backward next references for cycle 1
			for (int i = 1; i < cycle1.size(); i++)
			{
				cycle1.get(i).getTwinArrow().setNext(cycle1.get(i - 1).getTwinArrow());
			}
			cycle1.get(0).getTwinArrow().setNext(cycle1.get(cycle1.size() - 1).getTwinArrow());
			
			// * setup backward next references for cycle 2
			for (int i = 1; i < cycle2.size(); i++)
			{
				cycle2.get(i).getTwinArrow().setNext(cycle2.get(i - 1).getTwinArrow());
			}
			cycle2.get(0).getTwinArrow().setNext(cycle2.get(cycle2.size() - 1).getTwinArrow());
			
			// * create faces
			Edge[] cycle1Edges = cycle1.stream()
					.map(a -> a.getEdge())
					.toArray(Edge[]::new);
			Edge[] cycle2Edges = cycle2.stream()
					.map(a -> a.getEdge())
					.toArray(Edge[]::new);
			
			Face f1 = new Face(this.newFaceName1, cycle1Edges);
			Face f2 = new Face(this.newFaceName2, cycle2Edges);
			
			// * create facets
			Facet ft1a = new Facet(userModelElements.autoName(), f1, searchStart.boundaryArrow.getFacet().getNormal());
			Facet ft1b = new Facet(userModelElements.autoName(), f1, searchStart.boundaryArrow.getFacet().getNormal().mult(-1));
			Facet ft2a = new Facet(userModelElements.autoName(), f2, searchStart.boundaryArrow.getFacet().getNormal());
			Facet ft2b = new Facet(userModelElements.autoName(), f2, searchStart.boundaryArrow.getFacet().getNormal().mult(-1));
			
			// * setup outer polygons
			ft1a.setOuterPolygonArrow(cycle1.get(0));
			ft1b.setOuterPolygonArrow(cycle1.get(0).getTwinArrow());
			
			ft2a.setOuterPolygonArrow(cycle2.get(0));
			ft2b.setOuterPolygonArrow(cycle2.get(0).getTwinArrow());
			
			// * for every inner polygon of the old face determine its belonging to the new faces
			Polygon3d p1a = this.makePolygon(ft1a);
			ArrayList<Arrow> ft1aInnerPolygons = new ArrayList<Arrow>();
			ArrayList<Arrow> ft1bInnerPolygons = new ArrayList<Arrow>();
			ArrayList<Arrow> ft2aInnerPolygons = new ArrayList<Arrow>();
			ArrayList<Arrow> ft2bInnerPolygons = new ArrayList<Arrow>();
			
			Arrow[] oldFaceInnerPolygons = searchStart.boundaryArrow.getFacet().getInner();
			PointToPolygonLocationAlgorithmFactory pointToPolygonLocationAlgorithmFactory = new PointToPolygonLocationAlgorithmFactory(precisionConfiguration);
			
			for (int i = 0; i < oldFaceInnerPolygons.length; i++)
			{
				Arrow innerPolygon = oldFaceInnerPolygons[i];
				
				//test against p1a
				PointToPolygonLocationAlgorithmResult result = pointToPolygonLocationAlgorithmFactory
						.create(p1a, innerPolygon.getOrigin().getNode().getVector())
						.calculate();
				
				if (result.getLocationCode() == PointToPolygonLocationKind.Interior)
				{
					ft1aInnerPolygons.add(innerPolygon);
					ft1bInnerPolygons.add(innerPolygon.getTwinArrow());
				}
				else
				{
					ft2aInnerPolygons.add(innerPolygon);
					ft2bInnerPolygons.add(innerPolygon.getTwinArrow());
				}
			}
			
			ft1a.setInner(ft1aInnerPolygons.toArray(new Arrow[ft1aInnerPolygons.size()]));
			ft1b.setInner(ft1bInnerPolygons.toArray(new Arrow[ft1bInnerPolygons.size()]));
			ft2a.setInner(ft2aInnerPolygons.toArray(new Arrow[ft2aInnerPolygons.size()]));
			ft2b.setInner(ft2bInnerPolygons.toArray(new Arrow[ft2bInnerPolygons.size()]));
			
			//TODO setup rot for boundary edges
			//TODO setup for edges references to arrows
		}
		else
		{
			//the face is splitted by a hole
		}
		// *** SPLIT FACE LOGIC *** END ***
		
		return null;
	}
	
	private Polygon3d makePolygon(Facet ft)
	{
		ArrayList<Vector3d> polygonVertices = new ArrayList<Vector3d>();
		
		Arrow s = ft.getOuter();
		Arrow ia = s;
		do 
		{
			polygonVertices.add(ia.getOrigin().getNode().getVector());
			ia = ia.getNext();
			
		} while (ia != s);
		
		return new Polygon3d(polygonVertices.toArray(new Vector3d[polygonVertices.size()]), ft.getNormal());
	}
	
	private ArrayList<Node> gatherAddedNodes(IUserModelElements userModelElements) {
		
		ArrayList<Node> addedNodes = new ArrayList<Node>();
		for (int i = 0; i < this.commands.size(); i++)
		{
			IWorkStepCommand command = this.commands.get(i);
			
			if (command.getKind() == WorkStepCommandKind.AddNode)
			{
				AddNodeWorkStepCommand addNodeCommand = ((AddNodeWorkStepCommand)command);
				
				String nodeName = addNodeCommand.getName() != null ? addNodeCommand.getName() : userModelElements.autoName();
				Node node = new Node(nodeName, addNodeCommand.isEmpty(), addNodeCommand.getLocation());
				
				addedNodes.add(node);
			}
		}
		return addedNodes;
	}
	
	private ArrayList<AddedEdgeContainer> gatherAddedEdges(IUserModelElements userModelElements, ArrayList<Node> addedNodes) {
		
		ArrayList<AddedEdgeContainer> addedEdges = new ArrayList<AddedEdgeContainer>();
		for (int i = 0; i < this.commands.size(); i++)
		{
			IWorkStepCommand command = this.commands.get(i);
			
			if (command.getKind() == WorkStepCommandKind.AddEdge)
			{
				AddEdgeWorkStepCommand addEdgeCommand = ((AddEdgeWorkStepCommand)command);
				
				String edgeName = addEdgeCommand.getName() != null ? addEdgeCommand.getName(): userModelElements.autoName();
				
				String[] edgeNodeNames = new String[] { addEdgeCommand.getNodeName1(), addEdgeCommand.getNodeName2() };
				AddedEdgeNodeContainer[] edgeNodes = Arrays.stream(edgeNodeNames)
						.map(t -> {
							
							//a node for added edge can be from the model or from the set of added nodes
							Optional<Node> node = Optional.ofNullable(userModelElements.getNode(t));
							if (node == null)
							{
								node = addedNodes.stream()
									.filter(n -> n.getName() == t)
									.findFirst();
								
								//TODO if node not found - error
								return new AddedEdgeNodeContainer(node.get(), false);
							} 
							else
							{
								return new AddedEdgeNodeContainer(node.get(), true);
							}
							
						}).toArray(AddedEdgeNodeContainer[]::new);
				
				Edge edge = new Edge(edgeName, edgeNodes[0].Node, edgeNodes[1].Node);
				AddedEdgeContainer addedEdgeContainer = new AddedEdgeContainer(edge, edgeNodes[0].isFromTheOldModel, edgeNodes[1].isFromTheOldModel, userModelElements);
				addedEdges.add(addedEdgeContainer);
			}
		}
		return addedEdges;
	}

	/**
	 * Find a node from which cycles search will start.
	 * The node must be of degree 3, so it is a meet point of added edge and 2 old edges.
	 */
	private SearchStart findSearchStart(ArrayList<AddedEdgeContainer> addedEdges, Arrow oldFaceOuterBoundaryPolygon) {
		
		SearchStart searchStart = new SearchStart();
		Arrow iArrow = oldFaceOuterBoundaryPolygon;
		do
		{
			for (int i = 0 ; i < addedEdges.size(); i++)
			{
				String iArrowOriginNodeName = iArrow.getOrigin().getNode().getName();
				
				AddedEdgeContainer addedEdge = addedEdges.get(i);
				
				if (addedEdge.isFromTheOldModel() && (
							addedEdge.getNode1().getName() == iArrowOriginNodeName ||
							addedEdge.getNode2().getName() == iArrowOriginNodeName))
				{
					
					searchStart.addedArrow = addedEdge.getArrow(0).getOrigin().getNode().getName() == iArrowOriginNodeName ?
							addedEdge.getArrow(0) : addedEdge.getArrow(0).getTwinArrow();
					searchStart.boundaryArrow = iArrow;
					searchStart.startNode = iArrow.getOrigin().getNode();
					
					return searchStart;
				}
			}
			
			iArrow = iArrow.getNext();
			
		} while (iArrow != oldFaceOuterBoundaryPolygon);
		
		return searchStart;
	}
	
	/**
	 * Find a cycle - a polygon. A search starts at startArrow and in every fork always turns to addedEdge.
	 */
	private ArrayList<Arrow> buildCycle(Arrow startArrow, boolean isIArrowAdded, Facet currentOldFacet, ArrayList<AddedEdgeContainer> addedEdges, int arrowsSetId) {
		
		Arrow iArrow = startArrow;
		ArrayList<Arrow> cycle = new ArrayList<Arrow>();
		do
		{
			cycle.add(iArrow);
			
			// we need arrowEndNode to find a next arrow of our cycle
			// the next arrow will has arrowEndNode as its origin
			Node iArrowEndNode = iArrow.getTwinArrow().getOrigin().getNode();
			
			// if iArrowEndNode is a fork-node, so it is also a part of one of added edges - select that edge for further search
			
			// find an added edge, that has iArrowEndNode in its boundary
			Optional<AddedEdgeContainer> nextAddedEdge = addedEdges.stream()
				.filter(ec -> ec.getNode1().getName() == iArrowEndNode.getName() || ec.getNode1().getName() == iArrowEndNode.getName())
				.findFirst();
			
			if (nextAddedEdge.isPresent())
			{
				// continue cycle with the arrow from the found added edge
				
				// travel between added edges

				// select right arrow of the found added edge
				Arrow nextArrow = nextAddedEdge.get().getArrow(arrowsSetId).getOrigin().getNode().getName() == iArrowEndNode.getName() ?
						nextAddedEdge.get().getArrow(arrowsSetId) : nextAddedEdge.get().getArrow(arrowsSetId).getTwinArrow();
				
				iArrow.setNext(nextArrow);
				isIArrowAdded = true;
			}
			else if (iArrow.getNext() == null)
			{
				// travel back to the old boundary (or continue to travel in old boundary)
				
				if (isIArrowAdded)
				{
					// find an edge which arrow belongs to the current old facet and out from the current arrowEndNode
					Arrow nextArrow = this.findAPolygonArrowThatStartsAtCertainVertex(currentOldFacet.getOuter(), iArrowEndNode.getVertex());
					if (nextArrow == null)
					{
						Arrow[] innerPolygons = currentOldFacet.getInner();
						for (int i = 0; i < innerPolygons.length; i++)
						{
							nextArrow = this.findAPolygonArrowThatStartsAtCertainVertex(innerPolygons[i], iArrowEndNode.getVertex());
							if (nextArrow != null)
							{
								break;
							}
						}
					}
					
					//TODO if nextArrow == null => error
					
					isIArrowAdded = false;
					
					iArrow.setNext(nextArrow);
				}
			}
			
			iArrow = iArrow.getNext();

		} while (iArrow != startArrow);
		
		return cycle;
	}
	
	private void createVerticesForAddedNodes(IUserModelElements userModelElements, ArrayList<Node> addedNodes)
	{
		for (int i = 0; i < addedNodes.size(); i++)
		{
			Node n = addedNodes.get(i);
			Vertex v = new Vertex("vx_" + userModelElements.autoName() + "_" + n.getName(), n);
			n.setVertex(v);
		}
	}

	private Arrow findAPolygonArrowThatStartsAtCertainVertex(Arrow polygonArrow, Vertex vertex) {
		
		Arrow iArrow = polygonArrow;
		do
		{
			if (iArrow.getOrigin().getName() == vertex.getName())
				return iArrow;
			
			iArrow = iArrow.getNext();
		} 
		while (iArrow != polygonArrow);
		
		return null;
	}
	
	/**
	 * This container is used for optimization purposes.
	 */
	class AddedEdgeContainer {
		
		private Arrow[] arrowSet;
			
		private IUserModelElements userModelElements;
		
		private Edge edge;
		
		public boolean isNode1FromTheOldModel;
		
		public boolean isNode2FromTheOldModel;
		
		public AddedEdgeContainer(Edge edge, boolean isNode1FromTheOldModel, boolean isNode2FromTheOldModel, IUserModelElements userModelElements) {

			this.edge = edge;
			this.isNode1FromTheOldModel = isNode1FromTheOldModel;
			this.isNode2FromTheOldModel = isNode2FromTheOldModel;
			this.userModelElements = userModelElements;
			
			this.arrowSet = new Arrow[2];
		}
		
		/**
		 * A sign, if any of its Nodes is from the old model (not added in the workStep).
		 */
		public boolean isFromTheOldModel()
		{
			return this.isNode1FromTheOldModel || this.isNode2FromTheOldModel;
		}
		
		public Arrow getArrow(int setId)
		{
			
			if (this.arrowSet[setId] == null)
			{
				Arrow arrow     = new Arrow("ar_" + this.userModelElements.autoName() + "_" + this.edge.getName(), this.edge, this.edge.getNode1().getVertex());
				Arrow arrowTwin = new Arrow("ar_" + this.userModelElements.autoName() + "_" + this.edge.getName(), this.edge, this.edge.getNode2().getVertex());
				
				arrow.setTwinArrow(arrowTwin);
				arrowTwin.setTwinArrow(arrow);
				
				this.arrowSet[setId] = arrow;
			}
			return this.arrowSet[setId];
			
		}
		
		public Node getNode1()
		{
			return this.edge.getNode1();
		}
		
		public Node getNode2()
		{
			return this.edge.getNode2();
		}
	
		public Edge bindWithArrows() {
			
			this.edge.setArrow(arrowSet[0]);
			return this.edge;
		}
	}
		
	/**
	 * This container is used for optimization purposes.
	 */
	class AddedEdgeNodeContainer {
		
		/**
		 * A sign, if this node exists in the old model.
		 */
		public boolean isFromTheOldModel;
		
		/**
		 * A Node.
		 */
		public Node Node;
		
		public AddedEdgeNodeContainer(Node node, boolean isFromTheOldModel) {
			
			this.Node = node;
			this.isFromTheOldModel = isFromTheOldModel;
		}
		
	}

	class SearchStart {
		
		public Arrow addedArrow;
		
		public Arrow boundaryArrow;
		
		public Node startNode;
		
		public boolean isEmpty()
		{
			return this.addedArrow == null;
		}
		
	}
}
