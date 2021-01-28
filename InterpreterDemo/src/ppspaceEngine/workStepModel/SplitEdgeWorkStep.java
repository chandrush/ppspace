package ppspaceEngine.workStepModel;

import ppspaceEngine.PartitionModel;
import ppspaceEngine.partitionModel.IPartitionModelWorkStep;
import ppspaceEngine.workStepModel.IWorkStepCommand.WorkStepCommandKind;

/**
 * A work step for splitting edge.
 * @author andrej.chanturidze
 *
 */
public class SplitEdgeWorkStep extends WorkStep {

	public SplitEdgeWorkStep(PartitionModel partitionModel) {
		super(partitionModel);

	}

	@Override
	public void invoke() {
		
		//method implementation plan:
		//validate input
		//perform topological tests
		//perform geometrical tests
		//change partition model
		
		// do some checks
		if (this.commands.size() != 1)
		{
			this.error(0, "error");
			return;
		}
		
		IWorkStepCommand command = this.commands.get(0);
		
		if(command.getKind() != WorkStepCommandKind.AddNode)
		{
			this.error(0, "error");
			return;
		}
		
		AddNodeWorkStepCommand addNodeCommand = (AddNodeWorkStepCommand)command;
		
		// can create SNode and do all the rest stuff
		// ...
		
		// TODO Auto-generated method stub
		
		//code: topological test
		
		//first topological test
		if (!this.partitionModel.hasANode(addNodeCommand.oldName))
		{
			this.error(0, "there is no old node in a model");
			return;
		}
		
		//if every thing is ok
		
		this.actualSplitEdgeLogic();
		
	}
	
	private void actualSplitEdgeLogic() {
		
		//TODO call Maximilian code
	}

}
