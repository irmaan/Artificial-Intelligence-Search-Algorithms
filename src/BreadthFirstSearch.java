import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 
 */

public class BreadthFirstSearch extends Search {

	public BreadthFirstSearch(IPuzzleProblem problem, ISolution solution){
		super(problem, "BL", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
	}
	
	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}

	@Override
	public List<Node> search() {
		IPuzzleProblem problem = this.getProblem();
		Node s = problem.getFirst();
		List<Node> path = new Stack<Node>();
		
    	if(this.isMeta(s.getState(), problem)){
    		path = pathToRoot(s);
    		return path;
    	}
    	
        Queue<Node> q = new LinkedList<Node>();
        HashMap<Node, Node> visited = new HashMap<Node, Node>();
        
        visited.put(s, s);
        q.add(s);
        
        while (!q.isEmpty()) {
            Node v = q.poll();
            
            v.printNode();
            this.getSolution().addToExplored(1);
            
            for(Node w : problem.getDescendants(v)){
            	this.getSolution().addToGenerated(1);
                if (permitVisitedNodes() || !visited.containsKey(w)) {
                    visited.put(w, w);
                    q.add(w);
                }
                if(this.isMeta(w.getState(), problem)){
                	path = pathToRoot(w);
                	return path;
                }
            }
        }
        return path;
    }
}
