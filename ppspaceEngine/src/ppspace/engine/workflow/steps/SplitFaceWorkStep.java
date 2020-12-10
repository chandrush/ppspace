package ppspace.engine.workflow.steps;

import ppspace.engine.database.IUserModelElements;
import ppspace.engine.workflow.ISplitWorkStep;
import ppspace.engine.workflow.SplitWorkStepBase;
import ppspace.engine.workflow.SplitWorkStepBase.SplitRank;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.geometry.precision.PrecisionConfiguration;

/**
 * Split face work step splits a face. 
 * @author andrej.chanturidze
 *
 */
public class SplitFaceWorkStep extends SplitWorkStepBase implements ISplitWorkStep {

	private final String oldFaceName;
	
	private String newFaceName1;
	
	private String newFaceName2;

	public SplitFaceWorkStep(String oldFaceName, String newFaceName1, String newFaceName2) {
		
		this.oldFaceName = oldFaceName;
		this.newFaceName1 = newFaceName1;
		this.newFaceName2 = newFaceName2;
	}

	@Override
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, PrecisionConfiguration precisionConfiguration) {
		
		// *** VALIDATION *** START ***
		
		
		// *** VALIDATION *** END ***
		
		// *** SPLIT FACE LOGIC *** START ***
		
		
		// *** SPLIT FACE LOGIC *** END ***
		
		return null;
	}

	@Override
	protected SplitRank getRangk() {
		
		return SplitRank.Face;
	}


}
