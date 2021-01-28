package ppspace.engine.elements.shadow;

import java.util.LinkedList;
import java.util.Queue;

import ppspace.engine.elements.core.Vertex;
import ppspace.engine.elements.user.Edge;

public class SQueue {

	public Queue<Vertex> vertexQueue;
	public Queue<Edge> edgeQueue;

	public SQueue() {

		this.edgeQueue = new LinkedList<Edge>();
		this.vertexQueue = new LinkedList<Vertex>();

	}

}
