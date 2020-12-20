package ppspace.geometry.algorithms.pointToPolygonLocation;

import ppspace.geometry.algorithms.AlgorithmResultBase;

public class PointToPolygonLocationAlgorithmResult extends AlgorithmResultBase {

	public PointToPolygonLocationKind getLocationCode()
	{
		return PointToPolygonLocationKind.NotSet;
	}
	
	public enum PointToPolygonLocationKind
	{
		NotSet,
		Interior,
		Exterior
	}
	
}
