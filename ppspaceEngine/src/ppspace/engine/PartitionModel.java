package ppspace.engine;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.database.UserModelElements;
import ppspace.engine.workflow.ISplitWorkStep;
import ppspace.engine.workflow.IWorkStep;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.engine.workflow.Workflow;
import ppspace.engine.workflow.steps.InitializeWorkStep;
import ppspace.engine.workflow.steps.SplitEdgeWorkStep;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

public class PartitionModel {

	private final IUserModelElements userModelElements;
	
	private Workflow workflow;
	
	private PrecisionConfiguration precisionConfiguration;
	
	private PartitionModel(IUserModelElements userModelElements, Workflow workflow, PrecisionConfiguration precisionConfiguration)
	{
		this.userModelElements = userModelElements;
		this.workflow = workflow;
		this.precisionConfiguration = precisionConfiguration;
	}
	
	/**
	 * Create new Partition Model.
	 */
	public static PartitionModel createNew() {
		
		// create precision configuration
		PrecisionConfiguration precisionConfiguration = new PrecisionConfiguration(5);
		
		// create user model elements collection
		UserModelElements userModelElements = new UserModelElements(precisionConfiguration, 0);
		
		// create workflow & workStep factory
		Workflow workflow = new Workflow();

		// create partition model
		PartitionModel partitionModel = new PartitionModel(userModelElements, workflow, precisionConfiguration);
		
		// create and perform initialization Work Step
		IWorkStep initializeWorkStep = new InitializeWorkStep();
		partitionModel.applyWorkStep(initializeWorkStep);
		
		return partitionModel;
	}
	
	/**
	 * Load Partition Model from file.
	 */
	public static PartitionModel loadFrom(String filePath) {
		
		//TODO Load a model from a file
		return null;
	}
	
	public ISplitWorkStep createSplitEdgeWorkStep(String oldEdge, String newEdge1, String newEdge2) {
		
		return new SplitEdgeWorkStep(oldEdge, newEdge1, newEdge2);
	}
	
	public WorkStepInvokeResult applyWorkStep(IWorkStep workStep) {
			
		// invoke the WorkStep
		WorkStepInvokeResult workStepInvokeResult = workStep.invoke(this.userModelElements, this.precisionConfiguration);
		
		// apply results in the case of successful WorkStep execution
		if (workStepInvokeResult.isSuccess())
		{
			// save the executed WorkStep in the Workflow history
			this.workflow.AddStep(workStep);
		}
		
		return workStepInvokeResult;
	}

}
