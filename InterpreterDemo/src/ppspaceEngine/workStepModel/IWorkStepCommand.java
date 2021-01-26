package ppspaceEngine.workStepModel;

/**
 * One of the commands of Work Step.
 * @author andrej.chanturidze
 *
 */
public interface IWorkStepCommand {
	
	/**
	 * A Kind of the command.
	 */
	public WorkStepCommandKind getKind();
	
	public enum WorkStepCommandKind {

		Undefined,
		
		AddNode,
		
		AddEdge,
		
		AddFace,

	}
}