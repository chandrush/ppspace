package ppspace.geometry.algorithms.vectorsort;

import ppspace.geometry.algorithms.AlgorithmResultBase;

public final class VectorSortAlgorithmResult extends AlgorithmResultBase {

	private int[] sortingIndexes;
	
	public VectorSortAlgorithmResult(int[] sortingIndexes) {

		this.sortingIndexes = sortingIndexes;
	}
	
	public VectorSortAlgorithmResult(String[] errors)
	{
		super(errors);
	}
	
	public int[] getResultSortingIndexes() {
		
		return this.sortingIndexes;
	}

}
