package ppspace.geometry.algorithms.nodeToEdgeLocation;

import ppspace.geometry.precision.PrecisionConfiguration;

public class NodeToEdgeLocationAlgorithmFactory {

	private PrecisionConfiguration precisionConfiguration;
	
	public NodeToEdgeLocationAlgorithmFactory(PrecisionConfiguration precisionConfiguration) {
		
		this.precisionConfiguration = precisionConfiguration;
	}
	
	public NodeToEdgeLocationAlgorithm create() {
		
		return new NodeToEdgeLocationAlgorithm(this.precisionConfiguration);
	}

}
