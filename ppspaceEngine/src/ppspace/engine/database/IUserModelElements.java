package ppspace.engine.database;

import ppspace.engine.elements.user.Cell;
import ppspace.engine.elements.user.Edge;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;
import ppspace.geometry.Vector3d;

/**
 * Collection of elements of a User Model.
 * @author andrej.chanturidze
 *
 */
public interface IUserModelElements {
	
	String autoName();
	
	/**
	 * Returns a node by its name.
	 */
	public Node getNode(String name);
	
	/**
	 * Returns a node by its coordinates.
	 */
	public Node getNode(Vector3d coordinates);
	
	/**
	 * Returns an edge by its name.
	 */
	public Edge getEdge(String name);
	
	/**
	 * Returns a Face by its name.
	 */
	public Face getFace(String name);
	
	/**
	 * Returns a Cell by its name.
	 */
	public Cell getCell(String name);
	
	/**
	 * Add a Node to collection of nodes.
	 */
	public void addNode(Node node);
	
	/**
	 * Add an Edge to collection of edges.
	 */
	public void addEdge(Edge edge);
	
	/**
	 * Add a Face to collection of faces.
	 */
	public void addFace(Face face);
	
	/**
	 * Add a Cell to collection of cells.
	 */
	public void addCell(Cell cell);
	
	/**
	 * Remove a node from collection by name.
	 */
	public void removeNode(String name);

	/**
	 * Remove an edge from collection by name.
	 */
	public void removeEdge(String name);
	
	/**
	 * Remove a face from collection by name.
	 */
	public void removeFace(String name);
	
	/**
	 * Remove a cell from collection by name.
	 */
	public void removeCell(String name);

}
