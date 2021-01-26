package ppspaceEngine.workStepModel;

import ppspaceEngine.partitionModel.IPartitionModelWorkStep;
import ppspaceEngine.workStepModel.IWorkStepCommand.WorkStepCommandKind;

/**
 * A work step for splitting edge.
 * @author andrej.chanturidze
 *
 */
public class SplitEdgeWorkStep extends WorkStep {

	public SplitEdgeWorkStep(IPartitionModelWorkStep partitionModel) {
		super(partitionModel);

		
	}

	@Override
	public void invoke() {
		
		// do some checks
		if (this.commands.size() != 1)
			this.error(0, "error");
		
		IWorkStepCommand command = this.commands.get(0);
		
		if(command.getKind() != WorkStepCommandKind.AddNode)
			this.error(0, "error");
		
		AddNodeWorkStepCommand addNodeCommand = (AddNodeWorkStepCommand)command;
		
		// can create SNode and do all the rest stuff
		// ...
		
		// TODO Auto-generated method stub

	}

}
