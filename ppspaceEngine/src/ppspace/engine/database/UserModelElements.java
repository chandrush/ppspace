package ppspace.engine.database;

import java.util.Collection;

import ppspace.engine.elements.user.Cell;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;
import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.PrecisionConfiguration;

public class UserModelElements implements  IUserModelElements {

	/**
	 * Counter for unique naming.
	 */
	private int counter;

	private final PrecisionConfiguration precisionConfiguration;
	
	private final NodeSet nodes;
	
	private final INamedElementsCollection<Edge> edges;
	
	private final INamedElementsCollection<Face> faces;
	
	private final INamedElementsCollection<Cell> cells;
	

	/**
	 * Constructor for a child collection.
	 */
	public UserModelElements(PrecisionConfiguration precisionConfiguration, int autonameCounterInitialValue) {
		
		this.precisionConfiguration = precisionConfiguration;
		this.counter = autonameCounterInitialValue;
		
		// prepare elements collections
		this.nodes = new NodeSet(this.precisionConfiguration);
		this.edges = new NamedElementsCollection<Edge>();
		this.faces = new NamedElementsCollection<Face>();
		this.cells = new NamedElementsCollection<Cell>();
	}
	
	@Override
	public String autoName() {

		return "_" + this.counter++;
	}
	
	@Override
	public void addNode(Node node)
	{	
		this.nodes.add(node);
	}
	
	@Override
	public void addEdge(Edge edge) {

		this.edges.add(edge);
	}
	
	@Override
	public void addFace(Face face) {
		
		this.faces.add(face);
	}

	@Override
	public void addCell(Cell cell) {
		
		this.cells.add(cell);
	}
	
	@Override
	public Node getNode(String name) {
		
        return this.nodes.get(name);
	}
	
	@Override
	public Node getNode(Vector3d coordinates) {
		
		return this.nodes.get(coordinates);
	}

	@Override
	public Edge getEdge(String name) {
		
		return this.edges.get(name);
	}

	@Override
	public Face getFace(String name) {
		
		return this.faces.get(name);
	}

	@Override
	public Cell getCell(String name) {
		
		return this.cells.get(name);
	}
	
	@Override
	public void removeNode(String name)
	{
		this.nodes.remove(name);
	}

	@Override
	public void removeEdge(String name)
	{
		this.edges.remove(name);
	}
	
	@Override
	public void removeFace(String name)
	{
		this.faces.remove(name);;
	}
	
	@Override
	public void removeCell(String name)
	{
		this.cells.remove(name);
	}
	
	public Collection<Node> allNodes()
	{
		return this.nodes.all();
	}
	
	public Collection<Edge> allEdges()
	{
		return this.edges.all();
	}
	
	public Collection<Face> allFaces()
	{
		return this.faces.all();
	}
	
	public Collection<Cell> allCells()
	{
		return this.cells.all();
	}

}
