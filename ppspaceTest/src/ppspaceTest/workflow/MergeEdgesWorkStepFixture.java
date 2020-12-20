package ppspaceTest.workflow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ppspace.engine.database.UserModelElements;
import ppspace.engine.workflow.IMergeWorkStep;
import ppspace.engine.workflow.ISplitWorkStep;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.engine.workflow.steps.InitializeWorkStep;
import ppspace.engine.workflow.steps.MergeEdgesWorkStep;
import ppspace.engine.workflow.steps.SplitEdgeWorkStep;
import ppspace.geometry.precision.PrecisionConfiguration;

class MergeEdgesWorkStepFixture {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testInvoke() {

		//arrange
		PrecisionConfiguration precisionConfiguration = new PrecisionConfiguration(5);
		UserModelElements userModelElements = new UserModelElements(precisionConfiguration, 0);
		(new InitializeWorkStep()).invoke(userModelElements, precisionConfiguration);
		
		ISplitWorkStep splitEdgeWorkStep = new SplitEdgeWorkStep("e1", "e1_a", "e1_b");
		splitEdgeWorkStep.addNode("e1SplitNode", 5, 0, 0);
		WorkStepInvokeResult splitResult = splitEdgeWorkStep.invoke(userModelElements, precisionConfiguration);
		
		if (!splitResult.isSuccess())
		{
			fail("Error at the arrangment phase.");
		}
		
		//act	
		IMergeWorkStep mergeEdgesWorkStep = new MergeEdgesWorkStep("e1_a", "e1_b", "e1");

		WorkStepInvokeResult mergeResult = mergeEdgesWorkStep.invoke(userModelElements, precisionConfiguration);
		
		//assert invoke result successful
		assertTrue(mergeResult.isSuccess());
		
		//assert
	}

}
