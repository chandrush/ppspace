package ppspaceTest.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ppspace.geometry.Vector3d;
import ppspace.geometry.algorithms.nodeToEdgeLocation.NodeToEdgeLocation;
import ppspace.geometry.algorithms.nodeToEdgeLocation.NodeToEdgeLocationAlgorithmFactory;
import ppspace.geometry.algorithms.nodeToEdgeLocation.NodeToEdgeLocationAlgorithmResult;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmFactory;
import ppspace.geometry.algorithms.vectorsort.VectorSortAlgorithmResult;
import ppspace.geometry.precision.PrecisionConfiguration;

class NodeToEdgeLocationAlgorithmFixture {

	@Test
	void testCalculate() {

		Vector3d nodeVector = null, edgeStart = null, edgeVector = null;
		PrecisionConfiguration precisionConfiguration = new PrecisionConfiguration(5);
		
		// ������ ������� ���������
		NodeToEdgeLocationAlgorithmFactory algorithmFactory = new NodeToEdgeLocationAlgorithmFactory(precisionConfiguration);
		
		// ������ �������� � ��������� ��������� ��������
		NodeToEdgeLocationAlgorithmResult result = algorithmFactory
				.create(nodeVector, edgeStart, edgeVector)
				.calculate();
		
		assertFalse(result.hasErrors());
		assertEquals(NodeToEdgeLocation.Line, result.getNodeToEdgeLocationCode());
	}

}
