package ppspace.engine.workflow.commands;


public class AddEdgeWorkStepCommand implements IWorkStepCommand {

	private String name;
	
	private String nodeName1;
	
	private String nodeName2;
	
	public AddEdgeWorkStepCommand(String name, String nodeName1, String nodeName2) {
		
		this.name = name;
		this.nodeName1 = nodeName1;
		this.nodeName2 = nodeName2;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getNodeName1() {
		
		return this.nodeName1;
	}

	public String getNodeName2() {
		
		return this.nodeName2;
	}

	@Override
	public WorkStepCommandKind getKind() {
		
		return WorkStepCommandKind.AddEdge;
	}
}
