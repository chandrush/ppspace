package ppspace.engine.elements.shadow;

import java.util.HashSet;
import java.util.Set;

import ppspace.engine.elements.user.Face;

public class SFace {

	public Face face; // persistent face in the user model
	public Set<String> edgeNameSet; // base names of the edges of the face

	public SFace(Face face) {
		this.face = face;
		this.edgeNameSet = new HashSet<String>();
	}

}
