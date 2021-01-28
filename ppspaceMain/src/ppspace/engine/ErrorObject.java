package ppspace.engine;

/**
 * An error object.
 * @author andrej.chanturidze
 *
 */
public class ErrorObject {

	public final int errorCode;
	
	public final String errorMessage;
	
	public ErrorObject(int errorCode, String errorMessage) {
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * The enumeration of the known error codes.
	 */
	public static class ErrorCode {
		
		public static int Undefined = 0;
	}
}
