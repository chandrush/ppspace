package ppspace.engine.elements.core;

import java.util.LinkedList;
import java.util.List;

import ppspace.engine.elements.user.Face;

public class Facet {

	private String name;
	private Face face;

	public Facet twin;
	public Arrow outer;
	public List<Arrow> inner;
	public Voxel voxel;

	public Facet(String name, Face face, Arrow outer) {

		this.outer = outer;
		this.inner = new LinkedList<Arrow>();

		this.name = name;
		this.face = face;
	}

	public String getName() {

		return this.name;
	}

	public Face getFace() {

		return this.face;
	}

}