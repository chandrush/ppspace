package ppspace.geometry.algorithms.nodeToEdgeLocation;

import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.PrecisionConfiguration;

public class NodeToEdgeLocationAlgorithmFactory {

	private PrecisionConfiguration precisionConfiguration;
	
	public NodeToEdgeLocationAlgorithmFactory(PrecisionConfiguration precisionConfiguration) {
		
		this.precisionConfiguration = precisionConfiguration;
	}
	
	public NodeToEdgeLocationAlgorithm create(Vector3d node, Vector3d edgeStart, Vector3d edgeVector) {
		
		return new NodeToEdgeLocationAlgorithm(this.precisionConfiguration);
	}

}
