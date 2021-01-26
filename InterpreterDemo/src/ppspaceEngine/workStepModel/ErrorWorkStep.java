package ppspaceEngine.workStepModel;

import ppspaceEngine.partitionModel.IPartitionModelWorkStep;

/**
 * A work step for splitting edge.
 * @author andrej.chanturidze
 */
public class ErrorWorkStep extends WorkStep {

	public ErrorWorkStep(IPartitionModelWorkStep partitionModel, int errorCode, String errorMessage) {
		super(partitionModel);

		this.error(errorCode, errorMessage);
	}

	@Override
	public void invoke() {
		
		return;		
	}

}
