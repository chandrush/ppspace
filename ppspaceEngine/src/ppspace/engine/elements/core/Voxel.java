package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Cell;

public class Voxel  {

	private Cell cell;
	
	private Facet outer;
	
	private Facet[] inner;
	
	public Voxel(Cell cell) {
		
		this.cell = cell;
	}
	
	public Cell getCell() {
		
		return this.cell;
	}

	public Facet getOuter() {

		return this.outer;
	}

	public void setOuter(Facet facet)
	{
		this.outer = facet;
	}

	public Facet[] getInner() {
		
		return this.inner;
	}

}
