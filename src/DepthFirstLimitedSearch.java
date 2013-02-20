import java.util.Collections;
import java.util.List;


public class DepthFirstLimitedSearch extends Search {

	private int limit;
	
	public DepthFirstLimitedSearch(IPuzzleProblem problem, ISolution solution, int limit){
		super(problem, "BPL", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
		
		this.limit = limit;
	}
	
	@Override
	public List<Node> search() {
		IPuzzleProblem problem = this.getProblem();
		return dfs_recursive(problem.getFirst(), problem, this.limit);
	}

	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}
	
	private List<Node> dfs_recursive(Node s, IPuzzleProblem problem, int limit){
		
		if(this.isMeta(s.getState(), problem)){
    		return pathToRoot(s);
    	}
		else if(limit == 0){
			return Collections.emptyList();
		}
		else{
			this.getSolution().addToExplored(1);
			s.printNode();
			
			for(Node w : problem.getDescendants(s)){
	        	this.getSolution().addToGenerated(1);
	        	
	        	List<Node> result = dfs_recursive(w, problem, limit - 1);
	        		
	        	if(result != null && !result.isEmpty()){
	        		return result;
	        	}
			}
		}
		return Collections.emptyList();
	}
}
