package ppspace.engine;

/**
 * The result of the input operation.
 * 
 * @author andrej.chanturidze
 *
 */
public final class InputResult {

	public final ErrorObject error;

	public boolean isOk() {

		return this.error == null;
	}

	private InputResult(ErrorObject error) {

		this.error = error;
	}

	public static InputResult Success() {

		return new InputResult(null);
	}

	public static InputResult Error(ErrorObject error) {

		return new InputResult(error);
	}
}