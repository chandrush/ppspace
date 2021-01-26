package ppspaceEngine.partitionModel;

import ppspaceEngine.workStepModel.WorkStep;

/**
 * A part of the PartitionModel API intended for data input.
 * @author andrej.chanturidze
 *
 */
public interface IPartitionModelInput {

	/**
	 * Input a "split" command.
	 * @param oldElementName	A name of an element to be splitted.
	 * @param newElementName1	A name of an element to be created as the result of the split operation.
	 * @param newElementName2	A name of an element to be created as the result of the split operation.
	 * @return A WorkStep object to take further input.
	 */
	WorkStep split(String oldElementName, String newElementName1, String newElementName2);
	
	/**
	 * Input a "merge" command.
	 * @param oldElementName1	A name of an old element to be merged.
	 * @param oldElementName2	A name of an old to be merged.
	 * @param newElementName	A name of an element to be created as the result of the merge operation.
	 * @return A WorkStep object to take further input.
	 */
	WorkStep merge(String oldElementName1, String oldElementName2, String newElementName);
}
