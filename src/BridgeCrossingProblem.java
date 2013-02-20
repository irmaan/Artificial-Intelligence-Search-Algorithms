import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BridgeCrossingProblem implements IPuzzleProblem {

	private int N;
	private int maxMinutes;
	private Node first;
	private String heuristics;
	
	private final String WEST_TO_EAST = ">>";
	private final String EAST_TO_WEST = "<<";
	
	public BridgeCrossingProblem(List<String> problemDefinition, String heuristics) throws Exception{
		if(heuristics.equals("h1") || heuristics.equals("h2") || heuristics.equals("h3")){
			this.heuristics = heuristics;
		}
		else{
			System.err.println("heurística deve ser h1, h2 ou h3! Usando h1.");
			this.heuristics = "h1";
		}
		
		init(problemDefinition);
	}
	
	private void init(List<String> problemDefinition) throws Exception {
		//validation
		if(problemDefinition == null || problemDefinition.size() == 0){
			throw new Exception("problem definition is invalid!");
		}
		
		Pattern intPattern = Pattern.compile("\\d+");
		Matcher matcher = intPattern.matcher(problemDefinition.get(0));
		
		if(matcher.find()){
			String sN = matcher.group();
			this.N = Integer.parseInt(sN);
		}
		if(matcher.find()){
			String sMaxMinutes = matcher.group();
			this.maxMinutes = Integer.parseInt(sMaxMinutes);
		}
		if(this.N <= 0 || this.maxMinutes <= 0){
			throw new Exception("N or max_minutes is invalid!");
		}
		
		HashSet<Integer> peopleAtWest = new HashSet<Integer>();
		for(int pos = 2; pos < this.N + 2 && pos < problemDefinition.size(); pos++){
			String person = problemDefinition.get(pos);
			Integer p = Integer.parseInt(person);
			if(!peopleAtWest.add(p)){
				System.out.println("people times should be unique!");
			}
		}
		
		//end validation
		
		IState initial = new BridgeCrossingState(new Action(), peopleAtWest, new HashSet<Integer>()); 
		this.first = makeChild(null, new Action(), initial);
	}

	@Override
	public Node getFirst() {
		return this.first;
	}

	@Override
	public PuzzleType getPuzzleType() {
		return PuzzleType.TravessiaPonte;
	}

	@Override
	public int N() {
		return this.N;
	}

	private Node makeChild(Node parent, Object legalAction, IState state) {
		Action action = (Action)legalAction;
		BridgeCrossingState actedState = act(action, (BridgeCrossingState) state);
		
		float childStepCost = action.getPeople().size() > 0? Collections.max(action.getPeople()) : 0;
		float parentCostTotal = state.getCostTotal();
		float totalCost = parentCostTotal + childStepCost;
		float heuristics = getHeuristics((BridgeCrossingState)state);
		int depth = parent != null? parent.getDepth() + 1 : 1;
		
		IState newState = new BridgeCrossingState(action, 
				actedState.getPeopleAtWest(),
				actedState.getPeopleAtEast(),
				childStepCost, 
				totalCost, 
				heuristics);
		
		return new Node(parent, newState, depth);
	}
	
	private BridgeCrossingState act(Action legalAction, BridgeCrossingState parentState) {
		Set<Integer> peopleAtWest = new HashSet<Integer>(parentState.getPeopleAtWest());
		Set<Integer> peopleAtEast = new HashSet<Integer>(parentState.getPeopleAtEast());
		
		Set<Integer> movingPeople = legalAction.getPeople();
		
		if(legalAction.getDirection().equals(WEST_TO_EAST)){
			peopleAtWest.removeAll(movingPeople);
			peopleAtEast.addAll(movingPeople);
		}
		else if(legalAction.getDirection().equals(EAST_TO_WEST)){
			peopleAtWest.addAll(movingPeople);
			peopleAtEast.removeAll(movingPeople);
		}
		return new BridgeCrossingState(legalAction, peopleAtWest, peopleAtEast);
	}

	@Override
	public boolean goalTest(IState state) {
		if(state == null) return false;
		BridgeCrossingState bcs = (BridgeCrossingState) state; 
		return bcs != null &&
				bcs.getPeopleAtEast().size() == this.N && 
				bcs.getPeopleAtWest().size() == 0 ;
				//&& bcs.getCostTotal() <= this.maxMinutes;
	}

	public float getHeuristics(BridgeCrossingState state){
		if(this.heuristics.equals("h1")){
			return getHeuristics_1(state);
		}
		else if(this.heuristics.equals("h2")){
			return getHeuristics_2(state);
		}
		else{
			return getHeuristics_3(state);
		}
	}
	
	/*
	 * Count people still on the west
	 */
	public float getHeuristics_1(BridgeCrossingState state) {
		return state.getPeopleAtWest().size();
	}

	/*
	 * Sum the slowest of every pair still at west
	 * When the set of people is odd, add the slowest too.
	 */
	public float getHeuristics_2(BridgeCrossingState state) {
		float cost = 0;
		Set<Integer> peopleOnWest = state.getPeopleAtWest();
		if(peopleOnWest != null){
			List<Integer> listOfPeople = new LinkedList<Integer>(peopleOnWest);
			Collections.sort(listOfPeople);
			
			//sum the slowest of every pair
			int size = listOfPeople.size();
			for(int i = 1; i < size; i++){
				if(i % 2 != 0) cost += listOfPeople.get(i);
			}
			
			if(size % 2 != 0) {
				cost += listOfPeople.get(size - 1);
			}
		}
		
		return cost;
	}
	
	/*
	 * assuming no one needs to go back with the flashlight 
	 */
	public float getHeuristics_3(BridgeCrossingState state) {
		return Utils.sum(state.getPeopleAtWest()) / 2; //TODO optimistic
	}

	@Override
	public List<Node> getDescendants(Node parent) {
		List<Node> descendants = new ArrayList<Node>();
		BridgeCrossingState parentState = (BridgeCrossingState)parent.getState();
		String direction = getDirection(parentState);
		Set<Set<Integer>> setsOfPeople = getSetsOfPeople(direction, parentState);
		
		for(Set<Integer> people : setsOfPeople){
			Action legalAction = new Action(0, people, direction);
			Node child = this.makeChild(parent, legalAction, parentState);
			descendants.add(child);
		}
		return descendants;
	}

	//invert direction of children
	private String getDirection(BridgeCrossingState parentState) {
		if(parentState.getActionDirection().equals(WEST_TO_EAST)){
			return EAST_TO_WEST;
		}
		return WEST_TO_EAST;
	}

	//gets k-sized sets of people depending on direction
	//k=2 when going east, k=1 when returning to the west 
	private Set<Set<Integer>> getSetsOfPeople(String direction, BridgeCrossingState parentState) {
		List<Integer> listOfPeople;
		int k;
		
		if(direction.equals(WEST_TO_EAST)){ 
			listOfPeople = new LinkedList<Integer>(parentState.getPeopleAtWest());
			k = 2;
		}
		else{
			listOfPeople = new LinkedList<Integer>(parentState.getPeopleAtEast());
			k = 1;
		}
		
		List<Set<Integer>> result = Utils.getSubsets(listOfPeople, k);

		return new HashSet<Set<Integer>>(result);
	}

	@Override
	public boolean permitVisitedNodes() {
		return true;
	}

}
