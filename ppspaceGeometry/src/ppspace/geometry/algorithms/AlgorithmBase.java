package ppspace.geometry.algorithms;

import java.util.ArrayList;

import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

/**
 * A base class of geometric algorithms.
 * @author andrej.chanturidze
 *
 * @param <TResult> a type of the concrete algorithm result.
 */
public abstract class AlgorithmBase<TResult> implements IAlgoritm<TResult> {

	protected final PrecisionConfiguration precisionConfiguration;
	
	private final ArrayList<String> errors;
	
	protected AlgorithmBase(PrecisionConfiguration precisionConfiguration)
	{
		if (precisionConfiguration == null)
			throw new IllegalArgumentException("PrecisionConfiguration is not defined.");
		
		this.precisionConfiguration = precisionConfiguration;
		
		this.errors = new ArrayList<String>();
	}
	
	protected final boolean hasErrors()
	{
		return !this.errors.isEmpty();
	}
	
	protected final String[] errors()
	{
		return (String[]) this.errors.toArray();
	}
	
	public final TResult calculate()
	{
		try
		{
			this.calculateInternal();
			
			if (!this.hasErrors())
			{
				return this.constructResult();
			} 
			else
			{
				return this.constructErrorsResult();
			}
		} 
		catch (Exception exception)
		{
			this.errors.add(exception.getMessage());
			return this.constructErrorsResult();
		}
	}
	
	protected abstract void calculateInternal();

	protected abstract TResult constructResult();
	
	protected abstract TResult constructErrorsResult();
	
	protected void addErrors(String[] errors)
	{
		for (int i = 0; i < errors.length; i++)
		{
			this.errors.add(errors[i]);
		}
	}
	
	protected void addError(String error)
	{
		this.errors.add(error);
	}
}
