package ppspace.geometry.algorithms;

/**
 * Base implementation of an Algorithm result.
 * @author andrej.chanturidze
 *
 */
public abstract class AlgorithmResultBase implements IAlgorithmResult {

	protected final String[] errors;
	
	protected AlgorithmResultBase()
	{
		this.errors = null;
	}
	
	protected AlgorithmResultBase(String[] errors)
	{
		this.errors = errors;
	}
	
	@Override
	public boolean hasErrors() {
		
		return this.errors != null && this.errors.length > 0;
	}

	@Override
	public String[] errors() {

		return this.errors;
	}

}
