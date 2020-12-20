package ppspace.engine.elements.user;

import ppspace.engine.database.INamedElement;
import ppspace.engine.elements.core.Voxel;

public class Cell implements INamedElement  {

	private String name;
	
	private Face[] faces;
	
	private Voxel voxel;
	
	public Cell(String name, Face[] faces, Voxel voxel) {
		
		this.name = name;
		this.faces = faces;
		this.voxel = voxel;
	}
	
	public Cell(String name, Face[] faces) {
		
		this(name, faces, null);
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

	public Face[] getFaces() {

		return this.faces;
	}

}
