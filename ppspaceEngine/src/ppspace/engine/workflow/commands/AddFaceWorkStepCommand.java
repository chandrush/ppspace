package ppspace.engine.workflow.commands;

public class AddFaceWorkStepCommand implements IWorkStepCommand {

	private String name;
	
	private String[] edgesNames;
	
	public AddFaceWorkStepCommand(String name, String[] edgesNames) {

		this.name = name;
		this.edgesNames = edgesNames;
	}
	
	@Override
	public WorkStepCommandKind getKind() {
		
		return WorkStepCommandKind.AddFace;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String[] getEdgesNames()
	{
		return this.edgesNames;
	}

}
