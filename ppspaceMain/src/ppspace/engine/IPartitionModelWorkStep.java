package ppspace.engine;

/**
 * A part of the PartitionModel API intended for user in work steps.
 * 
 * @author andrej.chanturidze
 * 
 */
public interface IPartitionModelWorkStep {

	/**
	 * An automated numerical name is given according to the rank of the object and
	 * the order it is introduced to the partitioning
	 * 
	 * @param rank of the created object
	 * @return base name of the new object
	 */
	String getAutoName(int rank);
}
