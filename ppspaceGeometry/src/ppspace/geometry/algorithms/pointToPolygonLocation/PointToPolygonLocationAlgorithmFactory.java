package ppspace.geometry.algorithms.pointToPolygonLocation;

import ppspace.geometry.Polygon3d;
import ppspace.geometry.Vector3d;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

public class PointToPolygonLocationAlgorithmFactory {

private PrecisionConfiguration precisionConfiguration;
	
	public PointToPolygonLocationAlgorithmFactory(PrecisionConfiguration precisionConfiguration) {
		
		this.precisionConfiguration = precisionConfiguration;
	}
	
	public PointToPolygonLocationAlgorithm create(Polygon3d polygon, Vector3d point) {
		
		return new PointToPolygonLocationAlgorithm(polygon, point, this.precisionConfiguration);
	}
}
