package ppspace.engine.workflow.commands;

import ppspace.geometry.Vector3d;

public class AddNodeWorkStepCommand implements IWorkStepCommand {

	private String name;
	
	private boolean isEmpty;
	
	private Vector3d location;

	public AddNodeWorkStepCommand(String name, boolean isEmpty, Vector3d location) {
		
		this.name = name;
		this.isEmpty = isEmpty;
		this.location = location;
	}
	
	@Override
	public WorkStepCommandKind getKind() {
		
		return WorkStepCommandKind.AddNode;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public boolean isEmpty() {
		
		return this.isEmpty;
	}
	
	public Vector3d getLocation() {
		
		return this.location;
	}

}
