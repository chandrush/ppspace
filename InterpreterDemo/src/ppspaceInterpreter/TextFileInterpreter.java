package ppspaceInterpreter;

import ppspaceEngine.PartitionModel;
import ppspaceEngine.workStepModel.WorkStep;

/**
 * An Interpreter of the user input obtained from a text file.
 * @author andrej.chanturidze
 *
 */
public class TextFileInterpreter {

	private PartitionModel partitionModel;
	
	/**
	 * Constructor.
	 * @param partitionModel The ParitionModel to which, a user input will pass to. 
	 */
	public TextFileInterpreter(PartitionModel partitionModel) {
		
		this.partitionModel = partitionModel;
	}
	
	public void ProcessInput() {
		
		// ... text file parsing logic
		
		// parser meets a command "split"
		WorkStep workStep = this.partitionModel.split("oldElementName", "newElementName1", "newElementName2");
		
		// parser meets a command addNode
		workStep.addNode("nodeName", 1, 2, 3);
		
		// parser meets a command do
		workStep.invoke();
	}
}
