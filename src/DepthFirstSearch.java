import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 
 */

/**
 * Depth-First search on a graph
 */

public class DepthFirstSearch extends Search {

	private HashSet<Node> visited;
	
	public DepthFirstSearch(IPuzzleProblem problem, ISolution solution) {
		super(problem, "BP", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
		visited = new HashSet<Node>();
	}
	
	@Override
	public List<Node> search() {
		IPuzzleProblem problem = this.getProblem();
		return dfs_iterative(problem.getFirst(), problem);
		
		//recursive
		/*
		Node meta = dfs_recursive(problem.getFirst(), problem);
		
		if(meta != null){
			System.out.println("Meta found!");
			return pathToRoot(meta);
		}
		else{
			return new LinkedList<IState>();
		}
		*/
	}

	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}
	
	public List<Node> dfs_iterative(Node s, IPuzzleProblem problem)
	{
		Stack<Node> stack = new Stack<Node>();
		stack.push(s);
		List<Node> path = new LinkedList<Node>();
		
		visited.add(s);
		
		while(!stack.isEmpty()){
			Node v = stack.pop();

			this.getSolution().addToExplored(1);
			
			if(this.isMeta(v.getState(), problem)){
				path = pathToRoot(v);
				return path;
			}
				
            for(Node w : problem.getDescendants(v)){
            	
            	w.printNode();
            	this.getSolution().addToGenerated(1);
            	
                if (permitVisitedNodes() || !visited.contains(w)) {
                    visited.add(w);
                    stack.push(w);
                }
            }
		}
		return path;
	}
	
	private Node dfs_recursive(Node s, IPuzzleProblem problem){
		
		visited.add(s);
		
		if(this.isMeta(s.getState(), problem)){
    		return s;
    	}
		
		this.getSolution().addToExplored(1);
		
		for(Node w : problem.getDescendants(s)){

        	w.printNode();
        	this.getSolution().addToGenerated(1);
        	
        	if (!visited.contains(w)) {
        		Node meta = dfs_recursive(w, problem);
        		if(meta != null) return meta;
            }
		}
		return null;
	}
}
