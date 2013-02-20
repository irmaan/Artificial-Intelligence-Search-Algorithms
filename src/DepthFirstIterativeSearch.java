import java.util.LinkedList;
import java.util.List;

/**
 * 
 */


public class DepthFirstIterativeSearch extends Search {

	public DepthFirstIterativeSearch(IPuzzleProblem problem, ISolution solution){
		super(problem, "BPI", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
	}
	
	@Override
	public List<Node> search() {
		IPuzzleProblem problem = this.getProblem();
		int limit = 1, maximum = 100;
		List<Node> path = new LinkedList<Node>();
		
		ISearch depthFirstLimitedSearch;
		ISolution subSolution = null;
		while(limit < maximum && (path == null || path.isEmpty())){
			subSolution = (ISolution) this.getSolution().copy();
			depthFirstLimitedSearch = new DepthFirstLimitedSearch(problem, subSolution, limit++);
			path = depthFirstLimitedSearch.search();
			
			//this.getSolution().addSubSolution(subSolution);
		}
		
		if(subSolution != null){
			this.getSolution().addSubSolution(subSolution);
		}
		
		return path;
	}

	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}

}
