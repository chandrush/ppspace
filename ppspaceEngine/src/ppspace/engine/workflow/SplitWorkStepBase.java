package ppspace.engine.workflow;

import java.util.ArrayList;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.workflow.commands.AddEdgeWorkStepCommand;
import ppspace.engine.workflow.commands.AddFaceWorkStepCommand;
import ppspace.engine.workflow.commands.AddNodeWorkStepCommand;
import ppspace.engine.workflow.commands.IWorkStepCommand;
import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.PrecisionConfiguration;

public abstract class SplitWorkStepBase implements ISplitWorkStep {

	public enum SplitRank
	{
		Edge,
		Face,
		Cell
	}

	protected ArrayList<IWorkStepCommand> commands;
	
	protected SplitWorkStepBase() {
		
		this.commands = new ArrayList<IWorkStepCommand>();
	}
	
	/**
	 * Returns rank of the object to be split.
	 */
	protected abstract SplitRank getRangk();
	
	@Override
	public boolean addNode(String name, double x1, double x2, double x3) {
		
		AddNodeWorkStepCommand cmd = new AddNodeWorkStepCommand(name, false, new Vector3d(x1, x2, x3));
		this.commands.add(cmd);
		return true;
	}
	
	@Override
	public boolean addNodeE(String name, double x1, double x2, double x3) {
		
		AddNodeWorkStepCommand cmd = new AddNodeWorkStepCommand(name, true, new Vector3d(x1, x2, x3));
		this.commands.add(cmd);
		return true;
	}
	
	@Override
	public boolean addEdge(String name, String node1, String node2) {
		
		if (this.getRangk().ordinal() < SplitRank.Face.ordinal())
			return false;
		
		AddEdgeWorkStepCommand cmd = new AddEdgeWorkStepCommand(name, node1,  node2);
		this.commands.add(cmd);
		return true;
	}
	
	@Override
	public boolean addFace(String name, String[] edgesNames) {
		
		if (this.getRangk().ordinal() < SplitRank.Cell.ordinal())
			return false;
		
		AddFaceWorkStepCommand cmd = new AddFaceWorkStepCommand(name, edgesNames);
		this.commands.add(cmd);
		return true;
	}
	
	@Override
	public abstract WorkStepInvokeResult invoke(IUserModelElements userModelElements, PrecisionConfiguration precisionConfiguration);

}
