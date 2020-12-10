package ppspace.engine.database;

import java.util.ArrayList;
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
	
	/**
	 * Reference to the parent collection.
	 */
	private final IUserModelElements parentCollection;
	
	private final PrecisionConfiguration precisionConfiguration;
	
	private final NodeSet nodes;
	
	private final INamedElementsCollection<Edge> edges;
	
	private final INamedElementsCollection<Face> faces;
	
	private final INamedElementsCollection<Cell> cells;
	
	private final ArrayList<String> removedNodes;
	
	private final ArrayList<String> removedEdges;
	
	private final ArrayList<String> removedFaces;
	
	private final ArrayList<String> removedCells;
	
	/**
	 * Constructor for a child collection.
	 */
	public UserModelElements(IUserModelElements parentCollection, PrecisionConfiguration precisionConfiguration, int autonameCounterInitialValue) {
		
		this.parentCollection = parentCollection;
		this.precisionConfiguration = precisionConfiguration;
		this.counter = autonameCounterInitialValue;
		
		// prepare elements collections
		this.nodes = new NodeSet(this.precisionConfiguration);
		this.edges = new NamedElementsCollection<Edge>();
		this.faces = new NamedElementsCollection<Face>();
		this.cells = new NamedElementsCollection<Cell>();
		
		// prepare removed elements collections
		this.removedNodes = new ArrayList<String>();
		this.removedEdges = new ArrayList<String>();
		this.removedFaces = new ArrayList<String>();
		this.removedCells = new ArrayList<String>();
	}
	
	/**
	 * Constructor for the parent collection.
	 */
	public UserModelElements(PrecisionConfiguration precisionConfiguration) {
		
		this(null, precisionConfiguration, 0);
	}
	
	@Override
	public String autoName() {

		if (this.parentCollection != null)
			return this.parentCollection.autoName();
		
		return "_" + this.counter++;
	}
	
	@Override
	public IUserModelElements createChildCollection() {
		
		return new UserModelElements(this, this.precisionConfiguration, 0);
	}

	@Override
	public IUserModelElements getParentCollection() {

		return (IUserModelElements) this.parentCollection;
	}
	
	@Override
	public void merge() {
		
		if (this.parentCollection == null)
			return;
		
		for (Node a : this.nodes.all()) {
			
			this.parentCollection.addNode(a);
		}
		
		for (Edge a : this.edges.all()) {
			
			this.parentCollection.addEdge(a);
		}
		
		for (Face a : this.faces.all()) {
			
			this.parentCollection.addFace(a);
		}
		
		for (Cell a : this.cells.all()) {
			
			this.parentCollection.addCell(a);
		}
		
		for (String nodeName : this.removedNodes)
			this.parentCollection.removeNode(nodeName);
		
		for (String edgeName : this.removedEdges)
			this.parentCollection.removeEdge(edgeName);
		
		for (String faceName : this.removedFaces)
			this.parentCollection.removeFace(faceName);
		
		for (String cellName : this.removedCells)
			this.parentCollection.removeCell(cellName);
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
		
        return this.getElement(name, this.nodes, this.removedNodes, () -> this.getParentCollection().getNode(name));
	}
	
	@Override
	public Node getNode(Vector3d coordinates) {
		
		IUserModelElements parentCollection = this.getParentCollection();
		
		Node foundNode = null;
		
		if (parentCollection != null)
		{
			foundNode = parentCollection.getNode(coordinates);
		}
		
		if (foundNode == null)
		{
			foundNode = this.nodes.get(coordinates);
		}
		
		if (foundNode != null && this.removedNodes.contains(foundNode.getName()))
		{
			foundNode = null;
		}
		
		return foundNode;
	}

	@Override
	public Edge getEdge(String name) {
		
		return this.getElement(name, this.edges, this.removedEdges, () -> this.getParentCollection().getEdge(name));
	}

	@Override
	public Face getFace(String name) {
		
		return this.getElement(name, this.faces, this.removedFaces, () -> this.getParentCollection().getFace(name));
	}

	@Override
	public Cell getCell(String name) {
		
		return this.getElement(name, this.cells, this.removedCells, () -> this.getParentCollection().getCell(name));
	}
	
	@Override
	public void removeNode(String name)
	{
		if (this.getParentCollection() != null)
		{
			this.parentCollection.removeNode(name);
		}
		else
		{
			this.removedNodes.add(name);
		}
	}

	@Override
	public void removeEdge(String name)
	{
		if (this.getParentCollection() != null)
		{
			this.parentCollection.removeEdge(name);
		}
		else
		{
			this.removedEdges.add(name);
		}
	}
	
	@Override
	public void removeFace(String name)
	{
		if (this.getParentCollection() != null)
		{
			this.parentCollection.removeFace(name);
		}
		else
		{
			this.removedFaces.add(name);
		}
	}
	
	@Override
	public void removeCell(String name)
	{
		if (this.getParentCollection() != null)
		{
			this.parentCollection.removeCell(name);
		}
		else
		{
			this.removedCells.add(name);
		}
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

	private <T extends INamedElement> T getElement(String name, 
			INamedElementsCollection<T> collection,
			ArrayList<String> removedElements,
			java.util.function.Supplier<T> parentCollectionGet) {
		
		T foundElement = null;
		
		// if we are in the parent collection – no additional logic is needed
		if (this.parentCollection != null)
		{
			foundElement = parentCollectionGet.get();
		}
		
		if (foundElement == null)
		{
			foundElement = collection.get(name);
		}

		if (foundElement != null && removedElements.contains(name))
		{
			foundElement = null;
		}
	
		return foundElement;
	}

}
