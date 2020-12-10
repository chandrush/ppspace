package ppspaceTest.workflow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ppspace.engine.database.UserModelElements;
import ppspace.engine.elements.user.Face;
import ppspace.engine.elements.user.Node;
import ppspace.engine.workflow.IWorkStep;
import ppspace.engine.workflow.WorkStepInvokeResult;
import ppspace.engine.workflow.steps.InitializeWorkStep;
import ppspace.geometry.precision.PrecisionConfiguration;

class InitializeWorkStepFixture {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	void testInvoke() {
		
		//arrange

		PrecisionConfiguration precisionConfiguration = new PrecisionConfiguration(5);
		UserModelElements userModelElements = new UserModelElements(precisionConfiguration);
				
		// invoke the WorkStep
		IWorkStep initializeWorkStep = new InitializeWorkStep();

		//act
		WorkStepInvokeResult workStepInvokeResult = initializeWorkStep.invoke(userModelElements, precisionConfiguration);
		
		//assert invoke result is success
		assertTrue(workStepInvokeResult.isSuccess());
		
		//assert elements quantities
		Collection<Node> nodesCollection = userModelElements.allNodes();
		assertEquals(7, nodesCollection.size());
		
		Collection<Face> facesCollection = userModelElements.allFaces();
		assertEquals(20, facesCollection.size());
			
			
//		try
//		{
//			PrintWriter writer = new PrintWriter("e:\\temp\\a.txt", "UTF-8");
//			for (IArrow a : arrowsCollection.all())
//			{
//				writer.println(a.getEdge().getName());
//			}writer.flush();
//		}
//		catch (Exception exception)
//		{
//			fail(exception);
//		}
//		
//		INamedElementsCollection<IArrow> z = (INamedElementsCollection<IArrow>)coreModelElements.all(CoreModelElements.ElementKind.Arrow);
//		
//		int size = z.all().size();
//		assertEquals(100, size);
		
	}
}