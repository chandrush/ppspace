package ppspace.geometry.algorithms.vectorsort;

import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.IPrecisionConfiguration;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

public class VectorSortAlgorithmFactory {

	private PrecisionConfiguration precisionConfiguration;
	
	public VectorSortAlgorithmFactory(IPrecisionConfiguration precisionConfiguration) {
		
		this.precisionConfiguration = (PrecisionConfiguration)precisionConfiguration;
	}
	
	public VectorSortAlgorithm create(Vector3d pivotArrow, Vector3d[] arrows, boolean validate) {
		
		return new VectorSortAlgorithm(pivotArrow, arrows, validate, this.precisionConfiguration);
	}

}
