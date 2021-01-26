package ppspaceEngine.partitionModel;

/**
 * A part of the PartitionModel API intended for user in work steps.
 * @author andrej.chanturidze
 * 
 */
public interface IPartitionModelWorkStep {

	/**
	 * Returns an automatically generated unique name for model element.
	 * @return
	 */
	String getAutoName();
}
