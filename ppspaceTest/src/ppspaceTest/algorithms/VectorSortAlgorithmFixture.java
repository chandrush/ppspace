package ppspaceTest.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ppspace.geometry.Vector3d;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmFactory;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmResult;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;


class VectorSortAlgorithmFixture {

	private static PrecisionConfiguration precisionConfiguration;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		precisionConfiguration = new PrecisionConfiguration(5);
	}

	@SuppressWarnings("unused")
	@Test
	void testCalculate() {
		
		// --- arrange
		
		//define a plane orientation
		SimpleMatrix pivotVector1 = new SimpleMatrix(new double[][]{{0}, {0}, {1}});
		SimpleMatrix pivotVector2 = new SimpleMatrix(new double[][]{{0}, {0}, {-1}});
						
		//generate vectors
		int k = 6;
		double angleStep = Math.PI / k;
		double radius = 100;
		SimpleMatrix[] vectors = new SimpleMatrix[2 * k];
		for (int i = 0; i < 2 * k; i++)
		{
			double a = i * angleStep;
			double x1 = Math.cos(a) * radius;
			double x2 = Math.sin(a) * radius;
			vectors[i] = new SimpleMatrix(new double[][]{{x1}, {x2}, {0}});
		}
		
		// create transformation matrix
		// apply 3d transformation
		if (true)
		{
			double xa = 0.7;
			double ya = -0.2;
			double za = 0.3;
			SimpleMatrix dmx = new SimpleMatrix(new double[][]{
					{1, 0, 0},
					{0, Math.cos(xa), Math.sin(xa)},
					{0, -Math.sin(xa), Math.cos(xa)}
				});
			SimpleMatrix dmy = new SimpleMatrix(new double[][]{
					{Math.cos(ya), 0, -Math.sin(ya)},
					{0, 1, 0},
					{Math.sin(ya), 0, Math.cos(ya)}
				});
				SimpleMatrix dmz = new SimpleMatrix(new double[][]{
					{Math.cos(za), Math.sin(za), 0},
					{-Math.sin(za), Math.cos(za), 0},
					{0, 0, 1}
				});
			SimpleMatrix t = dmx.mult(dmy).mult(dmz);
			
			
			for (int i = 0; i < vectors.length; i++)
			{
				vectors[i] = t.mult(vectors[i]);
			}
			
			pivotVector1 = t.mult(pivotVector1);
			pivotVector2 = t.mult(pivotVector2);
		}

		//transform ejml-vectors to ppspace-vectors
		Vector3d[] ppspaceVectors = new Vector3d[vectors.length];
		for (int i = 0; i < ppspaceVectors.length; i++)
		{
			ppspaceVectors[i] = new Vector3d(vectors[i].get(0), vectors[i].get(1), vectors[i].get(2));
		}
		
		Vector3d ppspacePivotVector1 = new Vector3d(pivotVector1.get(0), pivotVector1.get(1), pivotVector1.get(2));
		Vector3d ppspacePivotVector2 = new Vector3d(pivotVector2.get(0), pivotVector2.get(1), pivotVector2.get(2));
		
		//generate random order for vectors sorting
		Integer[] range = IntStream
				.rangeClosed(0, ppspaceVectors.length - 1)
				.boxed()
				.toArray(Integer[]::new);
			    
		Arrays.sort(range, new Comparator<Integer>() {
			@Override public int compare(Integer o1, Integer o2) {
		        return Double.compare(Math.random(), Math.random());
			}
		});
		//Integer[] range = new Integer[] {5, 3, 9, 0, 7, 4, 11, 1, 6, 2, 10, 8};
		
		// sort vectors according to random order
		Vector3d[] ppspaceVectorsReodered = new Vector3d[ppspaceVectors.length];
		for (int i = 0; i < ppspaceVectors.length; i++)
		{
			ppspaceVectorsReodered[i] = ppspaceVectors[range[i]];
		}
		
		// --- act
		VectorSortAlgorithmFactory vectorSortFactory = new VectorSortAlgorithmFactory(precisionConfiguration);
		VectorSortAlgorithmResult result1 = vectorSortFactory
				.create(ppspacePivotVector1, ppspaceVectorsReodered, false)
				.calculate();
		
		// --- assert
		assertFalse(result1.hasErrors());
		
		int[] sortIndexes = result1.getResultSortingIndexes();
		for (int i = 0; i < sortIndexes.length; i++)
		{
			int test = (range[sortIndexes[i]] + sortIndexes.length - range[sortIndexes[0]]) % sortIndexes.length;
			if (test != i)
				fail("Wrong sort order.");
		}
		
		// --- act
		VectorSortAlgorithmResult result2 = vectorSortFactory
				.create(ppspacePivotVector2, ppspaceVectorsReodered, false)
				.calculate();
		
		// --- assert
		assertFalse(result2.hasErrors());
		
		int[] sortIndexes2 = result2.getResultSortingIndexes();
		for (int i = sortIndexes2.length - 1; i >= 0; i--)
		{
			int test = (range[sortIndexes2[i]] + sortIndexes2.length - range[sortIndexes2[sortIndexes2.length - 1]]) % sortIndexes2.length;
			if (test != (sortIndexes2.length - i - 1)  )
				fail("Wrong sort order.");
		}
	}

}
