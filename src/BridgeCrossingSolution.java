import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class BridgeCrossingSolution implements ISolution {

	private int explored;
	private int generated;
	private String searchAlgorithm;
	private List<Node> path;
	
	public BridgeCrossingSolution(){
		this("");
	}
	
	public BridgeCrossingSolution(String searchAlgorithm){
		this.path = new LinkedList<Node>();
		this.searchAlgorithm = searchAlgorithm;
	}
	
	public BridgeCrossingSolution(BridgeCrossingSolution obj) {
		this.explored = obj.explored;
		this.generated = obj.generated;
		this.searchAlgorithm = obj.searchAlgorithm;
		this.path = obj.path;
	}

	@Override
	public void printSolution() {
		System.out.println(this.searchAlgorithm);
		System.out.println("caminho da solução:");
		this.printPath();
		System.out.printf("%-30s%-12s\n", "número de nós explorados:", this.explored);
		System.out.printf("%-30s%-12s\n", "número de nós gerados:", this.generated);
		System.out.printf("%-30s%-12s\n", "profundidade da meta:", this.getMetaDepth());
		System.out.printf("%-30s%.3f\n", "custo da solução:", this.getSolutionCost());
		System.out.printf("%-30s%.3f\n", "fator de ramificação médio:", this.getBranchingFactor());
	}

	private float getBranchingFactor() {
		int expl = this.explored != 0? this.explored : 1;
		return (float)this.generated / expl;
	}

	@Override
	public void printPath() {
		for(Node n : this.path){
			IState state = n.getState();
			Action action = (Action)state.getAction();
			Set<Integer> people = action.getPeople();
			System.out.println(people + " " + action.getDirection());
			//System.out.println(state.getStateDefinition() + " costStep:" + n.getCostStep() + " costTotal:" + n.getCostTotal() + " h:" + n.getHeuristics());
		}
	}

	@Override
	public void setPath(List<Node> nodes) {
		this.path = nodes;
	}
	
	@Override
	public void addToExplored(int addition){
		this.explored += addition;
	}
	
	@Override
	public void addToGenerated(int addition){
		this.generated += addition;
	}
	

	public int getMetaDepth(){
		if(this.solved()){
			Node meta = this.path.get(this.path.size() - 1);
			return meta.getDepth();
		}
		return Integer.MAX_VALUE;
	}
	
	public float getSolutionCost(){
		if(this.solved()){
			Node meta = this.path.get(this.path.size() - 1);
			return meta.getState().getCostTotal();
		}
		return Float.POSITIVE_INFINITY;
	}

	@Override
	public void setAlgorithmName(String searchAlgorithmName) {
		this.searchAlgorithm = searchAlgorithmName;
	}
	
	public String getAlgorithmName() {
		return this.searchAlgorithm;
	}

	@Override
	public boolean solved() {
		return this.path != null && this.path.size() > 0;
	}

	@Override
	public ISolution copy() {
		return new BridgeCrossingSolution(this);
	}

	@Override
	public void addSubSolution(ISolution subSolution) {
		BridgeCrossingSolution sub = (BridgeCrossingSolution)subSolution;
		this.explored += sub.explored;
		this.generated += sub.generated;
	}
}
