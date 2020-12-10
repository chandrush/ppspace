package ppspace.engine.workflow;

/**
 * A Split Work step.
 * @author andrej.chanturidze
 *
 */
public interface ISplitWorkStep extends IWorkStep {

	public boolean addNode(String name, double x1, double x2, double x3);
	
	public boolean addNodeE(String name, double x1, double x2, double x3);
	
	public boolean addEdge(String name, String node1, String node2);
	
	public boolean addFace(String name, String[] edgesNames);
}
