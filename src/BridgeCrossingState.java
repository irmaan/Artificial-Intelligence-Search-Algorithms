import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BridgeCrossingState implements IState {

	private Object action;
	private float costStep; //step cost until node
	private float costTotal; //cost until node
	private float heuristics;
	
	private Set<Integer> peopleAtWest;
	private Set<Integer> peopleAtEast;
	
	public BridgeCrossingState(Object action, Set<Integer> peopleAtWest, Set<Integer> peopleAtEast){
		this(action, peopleAtWest, peopleAtEast, 0, 0, 0);
	}
	
	public BridgeCrossingState(Object action, 
			Set<Integer> peopleAtWest, 
			Set<Integer> peopleAtEast, 
			float costStep, 
			float costTotal) {
		this(action, peopleAtWest, peopleAtEast, costStep, costTotal, 0);
	}
	
	public BridgeCrossingState(Object action, 
			Set<Integer> peopleAtWest, 
			Set<Integer> peopleAtEast, 
			float costStep, 
			float costTotal, 
			float heuristics) {
		
		this.action = action;
		this.costStep = costStep;
		this.costTotal = costTotal;
		this.heuristics = heuristics;
		
		this.peopleAtWest = peopleAtWest != null? peopleAtWest : new HashSet<Integer>();
		this.peopleAtEast = peopleAtEast != null? peopleAtEast : new HashSet<Integer>();
	}
	
	private String buildStateDefinition() {
		StringBuilder sb = new StringBuilder();
		List<Integer> west = new ArrayList<Integer>(this.peopleAtWest),
				east = new ArrayList<Integer>(this.peopleAtEast);
		
		Collections.sort(west);
		Collections.sort(east);
		
		sb.append("W {");
		for (Integer el : west) {
			sb.append(el);
			sb.append(" ");
		}
		sb.append("} ");
		
		sb.append("E {");
		for (Integer el : east) {
			sb.append(el);
			sb.append(" ");
		}
		sb.append("}");
		
		return sb.toString();
	}

	@Override
	public float getCostStep() {
		return this.costStep;
	}

	@Override
	public float getCostTotal() {
		return this.costTotal;
	}
	
	@Override
	public Object getAction() {
		return this.action;
	}

	@Override
	public String getStateDefinition() {
		return this.buildStateDefinition();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return 31 * this.peopleAtEast.hashCode() + this.peopleAtWest.hashCode(); 
		//return this.stateDefinition.hashCode();
	}

	@Override
	public float getHeuristics() {
		return this.heuristics;
	}
	
	public Set<Integer> getPeopleAtWest(){
		return this.peopleAtWest;
	}
	
	public Set<Integer> getPeopleAtEast(){
		return this.peopleAtEast;
	}
	
	public String getActionDirection(){
		if(this.action != null){
			return ((Action)this.action).getDirection();
		}
		return "";
	}
}
