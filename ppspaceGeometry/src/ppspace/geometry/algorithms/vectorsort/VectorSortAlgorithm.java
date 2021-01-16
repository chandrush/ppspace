package ppspace.geometry.algorithms.vectorsort;

import java.util.Arrays;
import java.util.Comparator;

import ppspace.geometry.Vector3d;
import ppspace.geometry.algorithms.AlgorithmBase;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

public class VectorSortAlgorithm extends AlgorithmBase<VectorSortAlgorithmResult> {

	private final Vector3d pivotArrow;
	
	private final Vector3d[] arrows;
	
	private final boolean validate;
	
	private final int[] resultSorting;
	
	public VectorSortAlgorithm(Vector3d pivotArrow, Vector3d[] arrows, boolean validate, PrecisionConfiguration precisionConfiguration) {

		super(precisionConfiguration);
		
		this.pivotArrow = pivotArrow;
		this.arrows = arrows;
		this.validate = validate;
		
		this.resultSorting = new int[this.arrows.length];
	}

	@Override
	protected void calculateInternal() {
		
		if (this.validate)
		{
			//TODO check arrows to be in one plane
		}
		
		final double[] angles = new double[this.arrows.length];
		final Integer[] indices = new Integer[this.arrows.length];
		for (int i = 0; i < this.arrows.length; i++)
		{
			angles[i] = this.calculateAngle( this.arrows[0], this.arrows[i]);
			indices[i] = i;
		}
		
		final PrecisionConfiguration pc = this.precisionConfiguration;
		Arrays.sort(indices, new Comparator<Integer>() {
		    @Override public int compare(Integer o1, Integer o2) {
		        return pc.compare(angles[o1], angles[o2]);
		    }
		});

		for (int i = 0; i < indices.length; i++)
			this.resultSorting[i] = indices[i];
	}
	
	@Override
	protected VectorSortAlgorithmResult constructResult() {
		
		return new VectorSortAlgorithmResult(this.resultSorting);
	}

	@Override
	protected VectorSortAlgorithmResult constructErrorsResult() {
		
		return new VectorSortAlgorithmResult(this.errors());
	}
	
	private final double calculateAngle(Vector3d a, Vector3d b) {
		
		double dotProduct = a.dotProduct(b);
		Vector3d crossProduct = a.crossProduct(b);
		
		double arg = dotProduct / (a.euqlideanNorm() * b.euqlideanNorm());
		arg = this.precisionConfiguration.round(arg);
		
		double rad = Math.acos(arg);
		
		double cpS = Math.signum(Math.round(this.pivotArrow.dotProduct(crossProduct) * this.precisionConfiguration.accuracy()));
	    
		if (cpS >= 0)
	        return rad;
	    else
	        return 2 * Math.PI - rad;	
	}

}
