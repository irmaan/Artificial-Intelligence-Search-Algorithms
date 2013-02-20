import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */


public class ReguaPuzzleProblem implements IPuzzleProblem {
	
	private String validStateRegex;
	private static char EMPTY_SPACE = '-';
	private int N;
	private Node first;
	private String heuristics;
	
	public ReguaPuzzleProblem(List<String> problemDefinition, String heuristics) throws Exception{
		if(heuristics.equals("h1") || heuristics.equals("h2")){
			this.heuristics = heuristics;
		}
		else{
			System.err.println("heurística deve ser h1 ou h2! Usando h1.");
			this.heuristics = "h1";
		}
		
		init(problemDefinition);
	}
	
	private void init(List<String> problemDefinition) throws Exception{
		
		//validation
		if(problemDefinition == null || problemDefinition.size() != 2){
			throw new Exception("problem definition is invalid!");
		}
		else if(problemDefinition.get(1).isEmpty()){
	    	throw new Exception("initial state is invalid!");
	    }
		
		String nSize = problemDefinition.get(0);
		int n = Integer.parseInt(nSize); 
		
		String initialState = problemDefinition.get(1);
		
		if(initialState.length() != n * 2 + 1){
			throw new Exception("initial state length is invalid!");
		}
		else if(initialState.indexOf(EMPTY_SPACE) == -1){
			throw new Exception("initial state should contain one empty space character: '-'");
		}
		//end validation
		
		this.N = n;
		this.validStateRegex = "^B{#}A{#}$".replaceAll("#", Integer.toString(N)); 
		IState initial = new ReguaPuzzleState(new Action(), initialState);
		this.first = makeChild(null, new Action(), initial);
	}

	@Override
	public Node getFirst() {
		return this.first;
	}

	/* (non-Javadoc)
	 * @see IPuzzleMaker#getPuzzleType()
	 */
	@Override
	public PuzzleType getPuzzleType() {
		return PuzzleType.ReguaPuzzle;
	}

	/* (non-Javadoc)
	 * @see IPuzzleMaker#N()
	 */
	@Override
	public int N() {
		return this.N;
	}

	private List<Object> getLegalActions(IState state) {
		List<Object> actionList = new ArrayList<Object>();
		String stateDefinition = state.getStateDefinition();
		
		int length = stateDefinition.length();
		int emptyPosition = getEmptyPosition(stateDefinition);
		
		//left shift
		for(int pos = emptyPosition - 1, i = 0; pos >= 0 && i < this.N; i++, pos--){
			Action leftAction = new Action(pos - emptyPosition);
			actionList.add(leftAction);
		}
		
		//right shift
		for(int pos = emptyPosition + 1, i = 0; pos < length && i < this.N; i++, pos++){
			Action rightAction = new Action(pos - emptyPosition);
			actionList.add(rightAction);
		}
		
		return actionList;
	}

	@Override
	public boolean goalTest(IState state) {
		return state.getStateDefinition().replace("-", "").matches(this.validStateRegex);
	}

	private Node makeChild(Node parent, Object legalAction, IState state) {
		Action action = (Action)legalAction;
		String newStateDefinition = state.getStateDefinition();
		newStateDefinition = act(action, newStateDefinition);
		
		float childStepCost = Math.abs(action.getShift());
		float parentCostTotal = state.getCostTotal();
		float totalCost = parentCostTotal + childStepCost;
		float heuristics = getHeuristics(newStateDefinition); //TODO somente para A-star e IDA-star
		int depth = parent != null? parent.getDepth() + 1 : 1;
		
		IState newState = new ReguaPuzzleState(legalAction, 
				newStateDefinition, 
				childStepCost, 
				totalCost, 
				heuristics);
		
		return new Node(parent, newState, depth);
	}
	
	private int getEmptyPosition(String stateDefinition){
		return stateDefinition.indexOf(EMPTY_SPACE);
	}
	
	private String act(Action legalAction, String stateDefinition) {
		String newStateDefinition = "";
		int emptyPosition = getEmptyPosition(stateDefinition);
		
		int from = emptyPosition, 
			to = emptyPosition + legalAction.getShift(); //calculate shift
		
		newStateDefinition = Utils.moveChar(from, to, stateDefinition);
		
		return newStateDefinition;
	}	
	
	
	public float getHeuristics(String stateDefinition){
		if(this.heuristics.equals("h1")){
			return getHeuristics_1(stateDefinition);
		}
		else{
			return getHeuristics_2(stateDefinition);
		}
	}
	
	/*
	 * Returns the number of wrong positioned elements.
	 */
	public float getHeuristics_1(String stateDefinition){
		stateDefinition = stateDefinition.replace("-", ""); //ignore white
		
		int wrongPositionedCount = 0;
		for(int i = 0; i < this.N; i++){
			if(stateDefinition.charAt(i) == 'A') wrongPositionedCount++;
		}
		wrongPositionedCount = 2 * wrongPositionedCount;
		return wrongPositionedCount;
	}
	
	/*
	 * Sum the distance to the other side of each wrong positioned element.
	 */
	public float getHeuristics_2(String stateDefinition){
		stateDefinition = stateDefinition.replace("-", ""); //ignore white
		
		int wrongPositionedDistanceSum = 0;
		int middle = this.N;
		for(int i = 0; i < stateDefinition.length(); i++){
			
			if(i < middle && stateDefinition.charAt(i) == 'A'){
				wrongPositionedDistanceSum += Math.abs(i - 1 - middle);
			}
			else if(i >= middle && stateDefinition.charAt(i) == 'B'){
				wrongPositionedDistanceSum += Math.abs(i - middle);
			}
		}
		
		System.out.println(stateDefinition + " sum (h2): " + wrongPositionedDistanceSum);
		
		return (float)wrongPositionedDistanceSum / 2;
	}

	@Override
	public List<Node> getDescendants(Node n) {
		List<Node> descendants = new LinkedList<Node>();
		IState state = n.getState();
		List<Object> legalActions = this.getLegalActions(state);
		
		for(Object legalAction: legalActions){
        	Node sucessor = this.makeChild(n, legalAction, state);
        	descendants.add(sucessor);
		}
		return descendants;
	}

	@Override
	public boolean permitVisitedNodes() {
		return false;
	}
}
