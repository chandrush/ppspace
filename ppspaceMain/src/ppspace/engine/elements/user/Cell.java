package ppspace.engine.elements.user;

import java.util.HashSet;
import java.util.Set;

import ppspace.engine.elements.core.Voxel;

public class Cell {

	private String name;

	private Set<Face> faces;

	private Voxel voxel;

	public Cell(String name) {

		this.name = name;
		this.faces = new HashSet<Face>();
	}

	public String getName() {

		return this.name;
	}

	public Voxel getVoxel() {

		return this.voxel;
	}

	public void setVoxel(Voxel voxel) {

		if (this.voxel != null)
			throw new IllegalStateException("The Cell already has a Voxel setuped.");

		this.voxel = voxel;
	}

	public Set<Face> getFaces() {

		return this.faces;
	}

}
