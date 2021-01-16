package ppspace.engine.workflow.steps;

import java.util.ArrayList;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.core.Facet;
import ppspace.engine.elements.core.Vertex;
import ppspace.engine.elements.core.Voxel;
import ppspace.engine.elements.user.Cell;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;
import ppspace.engine.workflow.IWorkStep;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.geometry.Vector3d;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmFactory;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmResult;
import ppspace.geometry.precision.IPrecisionConfiguration;

/**
 * Initialization WorkStep creates initial objects of a Partition Model.
 * @author andrej.chanturidze
 *
 */
public class InitializeWorkStep implements IWorkStep {

	/**
	 * Constructor.
	 */
	public InitializeWorkStep() {
		
	}

	@Override
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, IPrecisionConfiguration precisionConfiguration) {

		// *** create user model elements ***

		// create nodes
		Node n0  = new Node("n0", false, new Vector3d ( 0,  0,  0));
		Node ne1 = new Node("ne1", true, new Vector3d( 1,  0,  0));
		Node ne2 = new Node("ne2", true, new Vector3d( 0,  1,  0));
		Node ne3 = new Node("ne3", true, new Vector3d( 0,  0,  1));
		Node ne4 = new Node("ne4", true, new Vector3d(-1,  0,  0));
		Node ne5 = new Node("ne5", true, new Vector3d( 0, -1,  0));
		Node ne6 = new Node("ne6", true, new Vector3d( 0,  0, -1));

		// create edges
		Edge e1 = new Edge("e1", n0, ne1);
		Edge e2 = new Edge("e2", n0, ne2);
		Edge e3 = new Edge("e3", n0, ne3);
		Edge e4 = new Edge("e4", n0, ne4);
		Edge e5 = new Edge("e5", n0, ne5);
		Edge e6 = new Edge("e6", n0, ne6);
		
		Edge ee1  = new Edge("ee1",  ne1, ne2);
		Edge ee2  = new Edge("ee2",  ne2, ne4);
		Edge ee3  = new Edge("ee3",  ne4, ne5);
		Edge ee4  = new Edge("ee4",  ne5, ne1);
		Edge ee5  = new Edge("ee5",  ne1, ne3);
		Edge ee6  = new Edge("ee6",  ne3, ne4);
		Edge ee7  = new Edge("ee7",  ne4, ne6);
		Edge ee8  = new Edge("ee8",  ne6, ne1);
		Edge ee9  = new Edge("ee9",  ne2, ne3);
		Edge ee10 = new Edge("ee10", ne3, ne5);
		Edge ee11 = new Edge("ee11", ne5, ne6);
		Edge ee12 = new Edge("ee12", ne6, ne2);

		// create faces
		Face f1 =  new Face("f1",  new Edge[] {e1, e2, ee1  });
		Face f2 =  new Face("f2",  new Edge[] {e2, e4, ee2  });
		Face f3 =  new Face("f3",  new Edge[] {e4, e5, ee3  });
		Face f4 =  new Face("f4",  new Edge[] {e5, e1, ee4  });
		Face f5 =  new Face("f5",  new Edge[] {e1, e3, ee5  });
		Face f6 =  new Face("f6",  new Edge[] {e3, e4, ee6  });
		Face f7 =  new Face("f7",  new Edge[] {e4, e6, ee7  });
		Face f8 =  new Face("f8",  new Edge[] {e6, e1, ee8  });
		Face f9 =  new Face("f9",  new Edge[] {e2, e3, ee9  });
		Face f10 = new Face("f10", new Edge[] {e3, e5, ee10 });
		Face f11 = new Face("f11", new Edge[] {e5, e6, ee11 });
		Face f12 = new Face("f12", new Edge[] {e6, e2, ee12 });
		Face fe1 = new Face("fe1", new Edge[] {ee1, ee9, ee5});
		Face fe2 = new Face("fe2", new Edge[] {ee2, ee6, ee9});
		Face fe3 = new Face("fe3", new Edge[] {ee3, ee10, ee6});
		Face fe4 = new Face("fe4", new Edge[] {ee4, ee5, ee10});
		Face fe5 = new Face("fe5", new Edge[] {ee1, ee12, ee8});
		Face fe6 = new Face("fe6", new Edge[] {ee1, ee12, ee8});
		Face fe7 = new Face("fe7", new Edge[] {ee3, ee11, ee7});
		Face fe8 = new Face("fe8", new Edge[] {ee4, ee8, ee11});
		
		// create Cells
		Cell c1 = new Cell("c1", new Face[] {f5, f9, f1, fe1});
		Cell c2 = new Cell("c2", new Face[] {f9, f6, f2, fe2});
		Cell c3 = new Cell("c3", new Face[] {f6, f10, f3, fe3});
		Cell c4 = new Cell("c4", new Face[] {f10, f5, f4, fe4});
		Cell c5 = new Cell("c5", new Face[] {f8, f12, f1, fe5});
		Cell c6 = new Cell("c6", new Face[] {f12, f7, f2, fe6});
		Cell c7 = new Cell("c7", new Face[] {f7, f11, f3, fe7});
		Cell c8 = new Cell("c8", new Face[] {f11, f8, f4, fe8});

		// *** add created user model elements to the User elements collection ***
		
		Node[] nodes = new Node[] {n0, ne1, ne2, ne3, ne4, ne5, ne6};
		Edge[] edges = new Edge[] {e1, e2, e3, e4, e5, e6, ee1, ee2, ee3, ee4, ee5, ee6, ee7, ee8, ee9, ee10, ee11, ee12};
		Face[] faces = new Face[] {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, fe1, fe2, fe3, fe4, fe5, fe6, fe7, fe8};
		Cell[] cells = new Cell[] {c1, c2, c3, c4, c5, c6, c7, c8};
		
		for (int i = 0; i < nodes.length; i++)
			userModelElements.addNode(nodes[i]);

		for (int i = 0; i < edges.length; i++)
			userModelElements.addEdge(edges[i]);

		for (int i = 0; i < faces.length; i++)
			userModelElements.addFace(faces[i]);
		
		for (int i = 0; i < cells.length; i++)
			userModelElements.addCell(cells[i]);
		
		// *** create core model elements ***
		
		this.createVoxel("vx_c1", c1, precisionConfiguration);
		this.createVoxel("vx_c2", c2, precisionConfiguration);
		this.createVoxel("vx_c3", c3, precisionConfiguration);
		this.createVoxel("vx_c4", c4, precisionConfiguration);
		this.createVoxel("vx_c5", c5, precisionConfiguration);
		this.createVoxel("vx_c6", c6, precisionConfiguration);
		this.createVoxel("vx_c7", c7, precisionConfiguration);
		this.createVoxel("vx_c8", c8, precisionConfiguration);
		
		//returning result
		return WorkStepInvokeResult.Success();
	}
	
	private void createVoxel(String voxelName, Cell cell, IPrecisionConfiguration precisionConfiguration) {
		
		Voxel vx = new Voxel(cell);
		cell.setVoxel(vx);
		
		// create the facets for the faces
		Face[] cellFaces = cell.getFaces();
		Facet[] facets = new Facet[cellFaces.length];
		for (int i = 0; i < cellFaces.length; i++)
		{ 
			facets[i] = this.createFaceFacets(cellFaces[i], precisionConfiguration);
			
			//set the reference to a voxel
			facets[i].setVoxel(vx);
			facets[i].getTwinFacet().setVoxel(vx);
		}
		
		//setup a reference to the outer facet
		vx.setOuter(facets[0]);
	}
	
	private Facet createFaceFacets(Face face, IPrecisionConfiguration precisionConfiguration) {
		
		//if the face already has a facet assigned - that means that the work was done earlier
		if (face.getFacet() != null)
		{
			return face.getFacet();
		}
		
		//create arrows for the edges of the given face
		Edge[] faceEdges = face.getEdges();
		Arrow[] arrows = new Arrow[faceEdges.length];
		for (int i = 0; i < faceEdges.length; i++)
		{
			arrows[i] = this.createEdgeArrows(faceEdges[i]);
		}
		
		//arrange arrows in polygons (handle the case with chaotic set of edges)
		//for this workstep we know that there is only one bidirectional polygon
		this.setupPolygonFromArrows(arrows[0], arrows, precisionConfiguration);
		this.setupPolygonFromArrows(arrows[0].getTwinArrow(), arrows, precisionConfiguration);
		
		//calculate normal vectors for twin facets (we know, that for initial step two consecutive edges don't lay on the same line)
		Vector3d p1NormalVector = this.normalVectorFunc(arrows[0]);
		Vector3d p2NormalVector = this.normalVectorFunc(arrows[0].getTwinArrow());
		
		//create facets
		Facet facet1 = new Facet(face, p1NormalVector);
		Facet facet2 = new Facet(face, p2NormalVector);
			
		//setup twin facet
		facet1.setTwinFacet(facet2);
		facet2.setTwinFacet(facet1);
		
		//setup a reference to a facet for arrows
		Arrow a = arrows[0];
		do 
		{
			a.setFacet(facet1);
			a = a.getNext();
		} while (a != arrows[0]);
		a = arrows[0].getTwinArrow();
		do
		{
			a.setFacet(facet2);
			a = a.getNext();
		} while (a != arrows[0].getTwinArrow());
		
		
		//setup references to outer polygons
		facet1.setOuterPolygonArrow(arrows[0]);
		facet2.setOuterPolygonArrow(arrows[0].getTwinArrow());
		
		//for the face setup reference to the facet
		face.setFacet(facet1);
		
		//a place to calculate/update dihedral cycle references
		
		for (int i = 0; i < faceEdges.length; i++)
		{
			Edge edge = faceEdges[i];
			
			//existed twin arrows for the edge with dihedral cycle setuped
			Arrow edgeArrow = edge.getArrow();
			Arrow edgeArrowTwin = edgeArrow.getTwinArrow();
			
			//a new twin arrows, created for this facet, that has to be inserted into dihedral cycle
			//(earlier in this method for faceEdges[i] arrows[i] was created)
			Arrow newArrow = arrows[i];
			Arrow newArrowTwin = arrows[i].getTwinArrow();
			
			//arrange arrows in pairs by their orientation
			if (edgeArrow.getOrigin() != newArrow.getOrigin())
			{
				Arrow temp = newArrow;
				newArrow = newArrowTwin;
				newArrowTwin = temp;
			}
			
			//if not first arrow in dihedral cycle
			if (edgeArrow != newArrow)
			{
				updateDihedralReferences(edgeArrow, newArrow, precisionConfiguration);
				updateDihedralReferences(edgeArrowTwin, newArrowTwin, precisionConfiguration);
			}
		}
		
		return facet1;
	}
	
	private Arrow createEdgeArrows(Edge edge) {
		
		//ensure edge nodes have corresponding vertexes in the coreModel
		Node node1 = edge.getNode1();
		Node node2 = edge.getNode2();
		
		if (node1.getVertex() == null)
		{
			node1.setVertex(new Vertex(node1));
		}
		
		if (node2.getVertex() == null)
		{
			node2.setVertex(new Vertex(node2));
		}
		
		//create arrows
		Arrow a1 = new Arrow(edge, edge.getNode1().getVertex());
		Arrow a2 = new Arrow(edge, edge.getNode2().getVertex());
		
		//setup twin status
		a1.setTwinArrow(a2);
		a2.setTwinArrow(a1);
		
		//setup reference from the edge to the arrows pair
		if (edge.getArrow() == null)
			edge.setArrow(a1);
			
		return a1;
	}
	
	private void setupPolygonFromArrows(Arrow p, Arrow[] arrows, IPrecisionConfiguration precisionConfiguration) {
		
		//the end of the current arrow should be the same as the origin of the next
		
		while (p.getNext() == null)
		{
			Vector3d pEndPoint = ArrowUtils.getEndPointVector(p);
			
			//iterate over arrows, find a next, then continue
			for (int i = 0; i < arrows.length; i++)
			{
				//tested arrow must not belong to the same edge (twin arrow will satisfy the condition, but will not be a next arrow in the polygon)
				if (!p.getEdge().equals(arrows[i].getEdge()))
				{
					if (ArrowUtils.getStartPointVector(arrows[i]).equal(pEndPoint, precisionConfiguration))
					{
						p.setNext(arrows[i]);
						p = arrows[i];
						break;
					}
					else if (ArrowUtils.getEndPointVector(arrows[i]).equal(pEndPoint, precisionConfiguration))
					{
						p.setNext(arrows[i].getTwinArrow());
						p = arrows[i].getTwinArrow();
						break;
					}
				}
			}
		}
	}
	
	private Vector3d normalVectorFunc(Arrow p) {
		
		Vector3d v1 = ArrowUtils.getStartPointVector(p);
		Vector3d v2 = ArrowUtils.getStartPointVector(p.getNext());
		Vector3d v3 = ArrowUtils.getStartPointVector(p.getNext().getNext());
		
		Vector3d v1v2 = v2
				.substract(v1);
		Vector3d v1v3 = v3
				.substract(v1);
		
		Vector3d normalVector = v1v2.crossProduct(v1v3);
		return normalVector;
	};
	
	private void updateDihedralReferences(Arrow arrow, Arrow newArrow, IPrecisionConfiguration precisionConfiguration) {
		
		//create an array of arrows of dihedral cycle and new arrow
		ArrayList<Arrow> dihedralCycleArrows = new ArrayList<Arrow>();
		Arrow iteratorArrow = arrow;
		do 
		{
			dihedralCycleArrows.add(iteratorArrow);
			iteratorArrow = iteratorArrow.getRot();
		} while (iteratorArrow != null && iteratorArrow != arrow);
		dihedralCycleArrows.add(newArrow);
		
		//extract vectors for the sorting algorithm
		Vector3d[] dihedralCycleArrowsVectors = dihedralCycleArrows.stream()
    			.map(a -> {
    				
    				Vector3d v1 = ArrowUtils.getStartPointVector(a);
    				Vector3d v2 = ArrowUtils.getEndPointVector(a);
    				return v2.substract(v1);
    			})
    			.toArray(Vector3d[]::new);
		
		VectorSortAlgorithmFactory vectorSortAlgorithmFactory = new VectorSortAlgorithmFactory(precisionConfiguration);
		VectorSortAlgorithmResult result = vectorSortAlgorithmFactory
				.create(dihedralCycleArrows.get(0).getFacet().getNormal(), dihedralCycleArrowsVectors, false)
				.calculate();
		
		if (result.hasErrors())
		{
			//it will never happen
		}
		
		int[] sortedIndexes = result.getResultSortingIndexes();
		for (int j = 0; j < dihedralCycleArrows.size() - 1; j++)
		{
			dihedralCycleArrows.get(sortedIndexes[j]).setRot(dihedralCycleArrows.get(sortedIndexes[j + 1]));
		}
		dihedralCycleArrows.get(dihedralCycleArrows.size() - 1).setRot(dihedralCycleArrows.get(0));
	}

	final static class ArrowUtils {

			public static Vector3d getEndPointVector(Arrow a) {
				
				return a.getTwinArrow().getOrigin().getNode().getVector();
			}
			
			public static Vector3d getStartPointVector(Arrow a) {
				
				return a.getOrigin().getNode().getVector();
			}
	}
}
