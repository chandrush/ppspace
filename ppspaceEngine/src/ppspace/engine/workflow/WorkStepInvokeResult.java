package ppspace.engine.workflow;

public class WorkStepInvokeResult {

	private boolean isSuccess;
	
	private String[] errorMessages;
	
	private Exception[] exceptions;

	private WorkStepInvokeResult(boolean isSuccess, String[] errorMessages, Exception[] exceptions) {
		
		this.isSuccess = isSuccess;
		this.errorMessages = errorMessages;
		this.exceptions = exceptions;
	}
	
	public static WorkStepInvokeResult Success() {
		
		return new WorkStepInvokeResult(true, new String[0], new Exception[0]);
	}
	
	public static WorkStepInvokeResult Failure(String message) {
		
		return new WorkStepInvokeResult(false, new String[] {message}, new Exception[0]);
	}
	
	public static WorkStepInvokeResult Failure(Exception exception) {
		
		return new WorkStepInvokeResult(false, new String[0], new Exception[] {exception});
	}
	
	public static WorkStepInvokeResult Failure(String[] messages) {
		
		return new WorkStepInvokeResult(false, messages, new Exception[0]);
	}
	
	public boolean isSuccess() {
		return this.isSuccess;
	}

	public String[] getErrorMessages() {
		return this.errorMessages;
	}
	
	public Exception[] getExceptions() {
		return this.exceptions;
	}
}
