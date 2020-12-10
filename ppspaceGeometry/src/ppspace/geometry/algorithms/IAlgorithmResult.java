package ppspace.geometry.algorithms;

/**
 * Base functionality of an geometric algorithm result.
 * @author andrej.chanturidze
 *
 */
public interface IAlgorithmResult {
	
	/**
	 * Tells if there were errors detected.
	 */
	boolean hasErrors();

	/**
	 * Errors messages.
	 */
	String[] errors();
}
