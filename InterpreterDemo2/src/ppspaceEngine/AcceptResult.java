package ppspaceEngine;

/**
 * Describes result of passing command from Interpreter to the PartitionModel.
 * @author andrej.chanturidze
 *
 */
public class AcceptResult {

	/**
	 * Detected error.
	 */
	protected ErrorObject error;
	
	private AcceptResult(int errorCode, String errorMessage) {

		this.error = new ErrorObject(errorCode, errorMessage);
	}
	
	private AcceptResult()
	{
		
	}
	
	public static AcceptResult Error(int errorCode, String errorMessage)
	{
		return new AcceptResult(errorCode, errorMessage);
	}
	
	public static AcceptResult Success()
	{
		return new AcceptResult();
	}
	
	/**
	 * Returns detected error.
	 */
	public ErrorObject getError() {
		
		return this.error;
	}
	
	/**
	 * To check if the result was successive or was created with errors.
	 * @return
	 */
	public boolean hasError() {
		
		return this.error != null;
	}
}
