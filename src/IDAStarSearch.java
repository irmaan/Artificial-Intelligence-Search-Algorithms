import java.util.LinkedList;
import java.util.List;

/**
 * 
 */


public class IDAStarSearch extends Search {

	public IDAStarSearch(IPuzzleProblem problem, ISolution solution) {
		super(problem, "IDA*", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
	}
	
	@Override
	public List<Node> search() {
		
		IPuzzleProblem problem = this.getProblem();
		Node root = problem.getFirst();
		float bound = root.getHeuristics();
		
		while(true){
			SearchResult result = idaStar(root, 0, bound);
			if(result.isGoalFound()){
				return pathToRoot(result.getGoalNode());
			}
			if(result.getF() == Float.POSITIVE_INFINITY){
				return new LinkedList<Node>(); //not found
			}
			bound = result.getF();
		}
	}
	
	private SearchResult idaStar(Node n, float g, float bound){
		IPuzzleProblem problem = this.getProblem();
		
		this.getSolution().addToExplored(1);
		n.printNode();
		
		float f = g + n.getHeuristics();
		if(f > bound) return new SearchResult(f);
		
		if(this.isMeta(n.getState(), problem)){
			return new SearchResult(0, true, n);
		}
		
		float min = Float.POSITIVE_INFINITY;
		for(Node sucessor : problem.getDescendants(n)){
        	
			this.getSolution().addToGenerated(1);
        	SearchResult result = idaStar(sucessor, g + sucessor.getCostStep(), bound);
        	if(result.isGoalFound()) return result;
        	if(result.getF() < min) min = result.getF();
		}
		return new SearchResult(min);
	}

	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}
}
