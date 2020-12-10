package ppspace.geometry.algorithms;

/**
 * An interface of geometric algorithms.
 * @author andrej.chanturidze
 *
 * @param <TResult> a type of the concrete algorithm result.
 */
public interface IAlgoritm<TResult> {

	/**
	 * Method to perform algorithm.
	 */
	public TResult calculate();
}
