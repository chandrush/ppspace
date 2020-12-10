package ppspace.engine.workflow;

import java.util.ArrayList;

public class Workflow {

	private ArrayList<IWorkStep> workSteps;
	
	public Workflow() {
		
		this.workSteps = new ArrayList<IWorkStep>();
	}
	
	public void AddStep(IWorkStep workStep) {
		
		this.workSteps.add(workStep);
	}

}
