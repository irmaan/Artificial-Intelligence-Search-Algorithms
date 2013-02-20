import java.util.List;

/**
 * 
 */


public interface ISearch {
	List<Node> search();
	IPuzzleProblem getProblem();
	ISolution solve();
	String getSearchAlgorithm();
	boolean isMeta(IState state, IPuzzleProblem problem);
	public List<Node> pathToRoot(Node meta);
	public boolean permitVisitedNodes();
}
