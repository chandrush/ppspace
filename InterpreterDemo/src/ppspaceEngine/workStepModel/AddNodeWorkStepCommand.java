package ppspaceEngine.workStepModel;

/**
 * A program representation of an AddNode command.
 * @author andrej.chanturidze
 *
 */
public class AddNodeWorkStepCommand implements IWorkStepCommand {

	public AddNodeWorkStepCommand(String name, boolean isEmpty, double x1, double x2, double x3) {
		
	}
	
	@Override
	public WorkStepCommandKind getKind() {
		
		return WorkStepCommandKind.AddNode;
	}
	
}
