import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class Search implements ISearch {

	private IPuzzleProblem problem;
	private String searchAlgorithmName;
	private ISolution solution;
	
	public Search(IPuzzleProblem problem, String searchAlgorithmName, ISolution solution){
		this.problem = problem;
		this.searchAlgorithmName = searchAlgorithmName != null? searchAlgorithmName : "";
		this.solution = solution;
	}

	@Override
	public IPuzzleProblem getProblem() {
		return this.problem;
	}

	@Override
	public String getSearchAlgorithm() {
		return this.searchAlgorithmName;
	}
	
	@Override
	public ISolution solve(){
		List<Node> path = this.search();
		this.solution.setPath(path);
		return this.solution;
	}
	
	public ISolution getSolution(){
		return this.solution;
	}
	

	@Override
	public abstract boolean isMeta(IState state, IPuzzleProblem problem);
	
	
	
	 /**
     * Returns the path from <tt>meta</tt> to <tt>initial state</tt>, or
     * <tt>null</tt> if no such path.
     * @param meta
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
	@Override
    public List<Node> pathToRoot(Node meta) {
        List<Node> path = new Stack<Node>();
        path.add(meta);
        Node x = meta.getParent();
        while(x != null){
        	path.add(x);
        	x = x.getParent();
        }
        //stack should iterate in reverse already...
        Collections.reverse(path);
        return path;
    }
	
	@Override
	public boolean permitVisitedNodes() {
		return this.problem.permitVisitedNodes();
	}
}
