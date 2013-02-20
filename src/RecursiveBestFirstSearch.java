import java.util.LinkedList;
import java.util.List;


/**
 * 
 */


public class RecursiveBestFirstSearch extends Search {

	private int maxRecursiveDepth;
	private float pathCost;
	
	public RecursiveBestFirstSearch(IPuzzleProblem problem, ISolution solution){
		super(problem, "RBFS", solution);
		solution.setAlgorithmName(this.getSearchAlgorithm());
	}
	
	@Override
	public List<Node> search() {
		clear();
		IPuzzleProblem p = this.getProblem();
		
		Node n = p.getFirst();
        SearchResult sr = rbfs(n, f(n), Float.POSITIVE_INFINITY, 0);
        
        if (sr.isGoalFound()) {
                Node s = sr.getGoalNode();
                return pathToRoot(s);
        }
        return new LinkedList<Node>();
	}
	
	@Override
	public boolean isMeta(IState state, IPuzzleProblem problem) {
		return problem.goalTest(state);
	}
	
	 private SearchResult rbfs(Node n, float node_f, float fLimit,
             int recursiveDepth) {

	     setMaxRecursiveDepth(recursiveDepth);
	     IPuzzleProblem p = this.getProblem();
	     
	    n.printNode();
     	this.getSolution().addToExplored(1);
	     
	     if (this.isMeta(n.getState(), this.getProblem())) {
	    	 return new SearchResult(fLimit, true, n);
	             //return new SearchResult(n, fLimit);
	     }
	     
	     List<Node> successors = p.getDescendants(n);
	     // if successors is empty then return failure, infinity
	     if (0 == successors.size()) {
	         return new SearchResult(Float.POSITIVE_INFINITY, false, null);    
	     }
	     
	     this.getSolution().addToGenerated(successors.size());
	     
	     float[] f = new float[successors.size()];
	     // for each s in successors do
	     // update f with value from previous search, if any
	     int size = successors.size();
	     for (int s = 0; s < size; s++) {
	             // s.f <- max(s.g + s.h, node.f)
	    	 	Node aux = successors.get(s);
	             f[s] = Math.max(f(aux), node_f);
	     }
	
	     // repeat
	     while (true) {
	             // best <- the lowest f-value node in successors
	             int bestIndex = getBestFValueIndex(f);
	             // if best.f > f_limit then return failure, best.f
	             if (f[bestIndex] > fLimit) {
	                     //return new SearchResult(null, f[bestIndex]);
	            	 return new SearchResult(f[bestIndex], false, null);
	             }
	             // if best.f > f_limit then return failure, best.f
	             int altIndex = getNextBestFValueIndex(f, bestIndex);
	             // result, best.f <- RBFS(problem, best, min(f_limit, alternative))
	             SearchResult sr = rbfs(successors.get(bestIndex), f[bestIndex],
	                             Math.min(fLimit, f[altIndex]), recursiveDepth + 1);
	             f[bestIndex] = sr.getF();
	             
	             // if result != failure then return result
	             if (sr.isGoalFound()) {
	                     return sr;
	             }
	     }
	}
	 
	 private float f(Node w) {
			return w != null? w.getCostTotal() + w.getHeuristics() : Float.POSITIVE_INFINITY;
		}

	// the lowest f-value node
     private int getBestFValueIndex(float[] f) {
             int lidx = 0;
             float lowestSoFar = Float.POSITIVE_INFINITY;

             for (int i = 0; i < f.length; i++) {
                     if (f[i] < lowestSoFar) {
                             lowestSoFar = f[i];
                             lidx = i;
                     }
             }

             return lidx;
     }

     // the second-lowest f-value
     private int getNextBestFValueIndex(float[] f, int bestIndex) {
             // Array may only contain 1 item (i.e. no alternative),
             // therefore default to bestIndex initially
             int lidx = bestIndex;
             float lowestSoFar = Float.POSITIVE_INFINITY;

             for (int i = 0; i < f.length; i++) {
                     if (i != bestIndex && f[i] < lowestSoFar) {
                             lowestSoFar = f[i];
                             lidx = i;
                     }
             }
             return lidx;
     }
     
     public void setMaxRecursiveDepth(int recursiveDepth) {
         int maxRdepth = this.maxRecursiveDepth;
         if (recursiveDepth > maxRdepth) {
        	 this.maxRecursiveDepth =  recursiveDepth;
         }
     }
     
     public void clear() {
    	 this.maxRecursiveDepth = 0;
    	 this.pathCost = 0;
 }
     
}
