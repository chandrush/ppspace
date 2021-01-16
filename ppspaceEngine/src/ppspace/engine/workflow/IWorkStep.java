package ppspace.engine.workflow;

import ppspace.engine.database.IUserModelElements;
import ppspace.geometry.precision.IPrecisionConfiguration;

/**
 * A base for Work step.
 * @author andrej.chanturidze
 *
 */
public interface IWorkStep {

	/**
	 * An invocation logic of the WorkStep.
	 */
	public WorkStepInvokeResult invoke(IUserModelElements userModelElements, IPrecisionConfiguration precisionConfiguration);
}
