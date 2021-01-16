package ppspace.geometry;

import ppspace.geometry.precision.IPrecisionConfiguration;
import ppspace.geometry.precision.deltathresold.PrecisionConfiguration;

public class Vector3d  {

	private double x;
	
	private double y;
	
	private double z;
	
	public Vector3d(double x, double y, double z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		
		return this.x;
	}

	public double getY() {
		
		return this.y;
	}

	public double getZ() {
		
		return this.z;
	}

	public boolean equal(Vector3d other, IPrecisionConfiguration precisionConfiguration)
	{
		PrecisionConfiguration pc = (PrecisionConfiguration)precisionConfiguration;
		return pc.equal(this.x, other.getX()) && 
				pc.equal(this.y, other.getY()) && 
				pc.equal(this.z, other.getZ());
	}

	public final Vector3d crossProduct(Vector3d v) {
		
		return new Vector3d(this.y * v.getZ() - this.z * v.getY(),
        		-(this.x * v.getZ() - this.z * v.getX()),
        		this.x * v.getY() - this.y * v.getX());
	}
	
	public final double dotProduct(Vector3d v)
	{
		return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ();
	}
	
	public final Vector3d substract(Vector3d v)
	{
		return new Vector3d(this.x - v.getX(), this.y - v.getY(), this.z - v.getZ());
	}
	
	public final Vector3d add(Vector3d v)
	{
		return new Vector3d(this.x + v.getX(), this.y + v.getY(), this.z + v.getZ());
	}
	
	public final double euqlideanNorm()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public final Vector3d mult(double k)
	{
		return new Vector3d(this.x * k, this.y * k, this.z * k);
	}
	
	public String toString() {
		
		return String.format("%d, %d, %d", this.x, this.y, this.z);
	}
}
