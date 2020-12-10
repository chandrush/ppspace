package ppspace.geometry.precision;

public class PrecisionConfiguration {

	private final int accuracy;
	
	private final double epsilon;
	
	private final long iEpsilon;
	
	public PrecisionConfiguration(int accuracy) {

		this.accuracy = accuracy;
		this.iEpsilon = (long)Math.pow(10, accuracy);
		this.epsilon = 1.0 / this.iEpsilon;
	}
	
	public final int accuracy()
	{
		return this.accuracy;
	}
	
	public final long iEpsilon()
	{
		return this.iEpsilon;
	}
	
	public final double epsilon() {
		
		return this.epsilon;
	}

	public int compare(double value1, double value2) {
		
		if (Math.abs(value1 - value2) < this.epsilon)
			return 0;
		
		if (value1 > value2)
			return 1;
		else
			return -1;
	}

	public boolean equal(double value1, double value2) {
		
		return Math.abs(value1 - value2) < this.epsilon;
	}

	public double round(double value) {
		
		return (double)Math.round(value * 100000d) / 100000d;
	}

}
