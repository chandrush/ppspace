package ppspace.engine.elements.shadow;

import ppspace.engine.elements.core.Arrow;
import ppspace.engine.elements.core.Vertex;

public class SFork {

	public SQueue queue;
	public Vertex vertex;
	public Arrow leavingArrow;

	public boolean isHead;

	public SFork(SQueue queue, Vertex vertex, Arrow leavingArrow, boolean isHead) {
		super();
		this.queue = queue;
		this.vertex = vertex;
		this.leavingArrow = leavingArrow;
		this.isHead = isHead;
	}

}