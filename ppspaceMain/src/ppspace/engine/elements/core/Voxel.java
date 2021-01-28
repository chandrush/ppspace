package ppspace.engine.elements.core;

import ppspace.engine.elements.user.Cell;

public class Voxel {

	private String name;

	private Cell cell;

	private Facet outer;

	private Facet[] inner;

	public Voxel(String name, Cell cell) {

		this.name = name;
		this.cell = cell;
	}

	public Cell getCell() {

		return this.cell;
	}

	public Facet getOuter() {

		return this.outer;
	}

	public void setOuter(Facet facet) {
		this.outer = facet;
	}

	public Facet[] getInner() {

		return this.inner;
	}

	public String getName() {

		return this.name;
	}

}
