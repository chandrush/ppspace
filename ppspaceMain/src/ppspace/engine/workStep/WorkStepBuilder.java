package ppspace.engine.workStep;

import java.util.HashMap;
import java.util.Map;

import ppspace.engine.InputResult;

/**
 * A class to maintain state of a forming work step.
 * 
 * @author andrej.chanturidze.
 *
 */
class WorkStepBuilder {

	enum InputState {

		/**
		 * The Null is a state that is in the start and after reset is reached.
		 */
		Null, 
		SplitEdge, 
		SplitFace, 
		SplitCell, 
		Merge, 
		AddNode, 
		AddEdge, 
		AddFace, 
		Do,
		/**
		 * The Error is a state that is occured as a reaction to state machine or some other input error. 
		 */
		Error
	}

	private InputState inputState;

	private Map<int, InputState[]> stateMachine;

	/**
	 * Constructor.
	 */
	public WorkStepBuilder() {

		// configure the state machine
		this.stateMachine = new HashMap<InputState, InputState[]>();

		stateMachine.put(InputState.Null, 
				new InputState[] { InputState.SplitEdge, InputState.SplitEdge, InputState.SplitFace, InputState.Merge });

		stateMachine.put(InputState.SplitEdge, 
				new InputState[] { InputState.AddNode });

		stateMachine.put(InputState.SplitFace, 
				new InputState[] { InputState.AddNode, InputState.AddEdge });

		stateMachine.put(InputState.SplitCell,
				new InputState[] { InputState.AddNode, InputState.AddEdge, InputState.AddFace });

		stateMachine.put(InputState.Merge, 
				new InputState[] { InputState.Do });
		
		stateMachine.put(InputState.AddNode, 
				new InputState[] { InputState.Do, InputState.AddNode, InputState.AddEdge, InputState.AddFace});
		
		stateMachine.put(InputState.AddEdge, 
				new InputState[] { InputState.Do, InputState.AddEdge, InputState.AddFace});
		
		stateMachine.put(InputState.AddFace, 
				new InputState[] { InputState.Do, InputState.AddFace});
		
		stateMachine.put(InputState.Do, 
				new InputState[] { InputState.SplitEdge, InputState.SplitFace, InputState.SplitCell, InputState.Merge});
		
		//define an initial state
		this.inputState = InputState.Null;
	}

	private boolean ValidateInput(InputState inputState) {
		
		this.stateMachine.g
	}
	/**
	 * Input a "split" command.
	 * 
	 * @param oldElementName  A name of an element to be splitted.
	 * @param newElementName1 A name of an element to be created as the result of
	 *                        the split operation.
	 * @param newElementName2 A name of an element to be created as the result of
	 *                        the split operation.
	 * @return A status result of the input operation.
	 */
	public InputResult split(String oldElementName, String newElementName1, String newElementName2) {

		
		return InputResult.Success();
	}

	/**
	 * Input a "merge" command.
	 * 
	 * @param oldElementName1 A name of an old element to be merged.
	 * @param oldElementName2 A name of an old to be merged.
	 * @param newElementName  A name of an element to be created as the result of
	 *                        the merge operation.
	 * @return A status result of the input operation.
	 */
	InputResult merge(String oldElementName1, String oldElementName2, String newElementName);

	/**
	 * Adding a node for the work step from the user input.
	 * 
	 * @param nodeName A node name.
	 * @param x1       A node coordinate x1.
	 * @param x2       A node coordinate x2.
	 * @param x3       A node coordinate x3.
	 * @return A status result of the input operation.
	 */
	InputResult addNode(String nodeName, double x1, double x2, double x3);

	/**
	 * Adding a node for the work step from the user input.
	 * 
	 * @param nodeName A node name.
	 * @param x1       A direction coordinate x1.
	 * @param x2       A direction coordinate x2.
	 * @param x3       A direction coordinate x3.
	 * @return A status result of the input operation.
	 */
	InputResult addNodeE(String nodeName, double x1, double x2, double x3);

	/**
	 * Adding an edge for the work step from the user input.
	 * 
	 * @param edgeName An edge name.
	 * @param node1    One of the nodes of the edge.
	 * @param node2    Other node of the edge.
	 * @return A status result of the input operation.
	 */
	InputResult addEdge(String edgeName, String node1, String node2);

	/**
	 * Adding a face for the work step from the user input.
	 * 
	 * @param faceName A face name.
	 * @param edges    Collection of edges that form the face.
	 * @return A status result of the input operation.
	 */
	InputResult addFace(String faceName, String[] edges);

	/**
	 * An invocation of the work step is called as a reaction on "do" command.
	 * 
	 * @return A status result of the invoke operation.
	 */
	InputResult invoke();
}
