package ppspaceEngine.workStepModel;

import java.util.ArrayList;
import java.util.Map;

import ppspaceEngine.elements.user.Node;
import ppspaceEngine.partitionModel.ErrorObject;
import ppspaceEngine.partitionModel.IPartitionModelWorkStep;

/**
 * A work step.
 * @author andrej.chanturidze.
 *
 */
public abstract class WorkStep {

	/**
	 * A reference to the partition model.
	 */
	protected IPartitionModelWorkStep partitionModel;
	
	/**
	 * Local to WorkStep link between the base names and the Java references of the nodes. 
	 */
	protected Map<String, Node> nodeMap;
	
	/**
	 * Collection of errors.
	 */
	protected ArrayList<ErrorObject> errors;
	
	/**
	 * Commands of work step.
	 */
	protected ArrayList<IWorkStepCommand> commands;
	
	/**
	 * Constructor.
	 * @param partitionModel A partition model to work on.
	 */
	protected WorkStep(IPartitionModelWorkStep partitionModel) {
		
		this.partitionModel = partitionModel;
		
		this.errors = new ArrayList<ErrorObject>();
		this.commands = new ArrayList<IWorkStepCommand>();
	}
	
	/**
	 * Adding a node for the work step from the user input.
	 * @param nodeName A node name.
	 * @param x1 A node coordinate x1.
	 * @param x2 A node coordinate x2.
	 * @param x3 A node coordinate x3.
	 */
	public void addNode(String nodeName, double x1, double x2, double x3) {
		
		this.commands.add(new AddNodeWorkStepCommand(nodeName, false, x1, x2, x3));
	}
	
	/**
	 * Adding an empty node for the work step from the user input.
	 */
	public void addNodeE(String nodeName, double x1, double x2, double x3) {
		
		this.commands.add(new AddNodeWorkStepCommand(nodeName, true, x1, x2, x3));
	}
	
	/**
	 * Adding an edge for the work step from the user input.
	 */
	public void addEdge(String edgeName, String node1, String node2) {
		
		//stub
	}
	
	/**
	 * Adding a face for the work step from the user input.
	 */
	public void addFace(String faceName, String[] edges) {
		
		//stub
	}
	
	/**
	 * Invoke a logic of the work step on the partition model.
	 */
	public abstract void invoke();
	
	/**
	 * Add a detected error to the collection of errors.
	 * @param errorCode An error code.
	 * @param errorMessage An error message.
	 */
	protected void error(int errorCode, String errorMessage) {
		
		this.errors.add(new ErrorObject(errorCode, errorMessage));
	}
	
	/**
	 * Check, if the work step has errors detected.
	 * @return True if the work step has errors detected.
	 */
	public boolean hasErrors() {
		
		return this.errors.size() > 0;
	}
}
