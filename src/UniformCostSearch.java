import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */


public class UniformCostSearch extends Search {

	private Frontier frontier;
	private HashSet<Node> visited;
	
	public UniformCostSearch(IPuzzleProblem problem, ISolution solution) {
		super(problem, "BCU", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
		
		visited = new HashSet<Node>();
		frontier = new Frontier(createPathCostComparator());
	}
	
	@Override
	public List<Node> search() {
		IPuzzleProblem problem = this.getProblem();
		Node root = problem.getFirst();
		frontier.add(root, root.getCostTotal());
		
		while(!frontier.isEmpty()){
			
			Node v = frontier.poll();
			
			v.printNode();
			this.getSolution().addToExplored(1);
			
			IState state = v.getState();
			if(this.isMeta(state, problem)){
				return pathToRoot(v);
			}
			visited.add(v);
			
			for(Node w : problem.getDescendants(v)){
				this.getSolution().addToGenerated(1);
				
                if (permitVisitedNodes() || !visited.contains(w)) {
                	if(!frontier.contains(w)){
                		frontier.add(w, w.getCostTotal());
                	}
                	else if(frontier.getValue(w) > w.getCostTotal()){
                		frontier.replace(w, w.getCostTotal());
                	}
                }
            }
		}
		return new LinkedList<>();
	}
	
	private static Comparator<Node> createPathCostComparator() {
		return new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				float thisWeight = node1.getCostTotal();  
			    float thatWeight = node2.getCostTotal();  
			    if(Math.abs(thisWeight - thatWeight) > 0.001){   
			        return Double.compare(thisWeight, thatWeight);
			    }  
			    else{  
			        return Long.compare(node1.getTimeStamp(), node2.getTimeStamp());  
			    }  
			}
		};
	}

	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}

}
