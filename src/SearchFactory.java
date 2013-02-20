
public final class SearchFactory {
	
	public static ISearch getSearch(String param, IPuzzleProblem problem, int limit) throws Exception{
		ISearch searchAlgorithm;
		ISolution solution;
		
		if(problem.getPuzzleType() == IPuzzleProblem.PuzzleType.ReguaPuzzle){
			solution = new ReguaPuzzleSolution();
		}
		else{
			solution = new BridgeCrossingSolution();
		}
		
		switch (param.toUpperCase()) {
		case "BL":
			searchAlgorithm = new BreadthFirstSearch(problem, solution);
			break;
		case "BP":
			searchAlgorithm = new DepthFirstSearch(problem, solution);
			break;
		case "BPL":
			searchAlgorithm = new DepthFirstLimitedSearch(problem, solution, limit);
			break;
		case "BPI":
			searchAlgorithm = new DepthFirstIterativeSearch(problem, solution);
			break;
		case "BCU":
			searchAlgorithm = new UniformCostSearch(problem, solution);
			break;
		case "A*":
			searchAlgorithm = new AStarSearch(problem, solution);
			break;
		case "IDA*":
			searchAlgorithm = new IDAStarSearch(problem, solution);
			break;
		case "RBFS":
			searchAlgorithm = new RecursiveBestFirstSearch(problem, solution);
			break;
		default:
			throw new Exception("Search algorithm not found: [" + param + "]");
		}
		return searchAlgorithm;
	}
}
