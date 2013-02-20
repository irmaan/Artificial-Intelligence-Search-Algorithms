
public class Node {
	private IState state;
	private Node parent;
	private int depth;
	private long timestamp;
	
	private final boolean VERBOSE = false;// true;
	
	public Node(Node parent, IState state, int depth){
		this.parent = parent;
		this.state = state;
		this.depth = depth;
		this.timestamp = System.nanoTime();
	}
	
	public IState getState(){
		return this.state;
	}
	
	public Node getParent(){
		return this.parent;
	}

	public int getDepth() {
		return depth;
	}
	
	public float getCostStep(){
		return this.state.getCostStep();
	}
	
	public float getCostTotal(){
		return this.state.getCostTotal();
	}
	
	public float getHeuristics(){
		return this.state.getHeuristics();
	}
	
	public long getTimeStamp(){
		return this.timestamp;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return this.state.hashCode();
	}
	
	public void printNode() {
		if(!VERBOSE) return;
		
		if(this.getState() != null){
			IState state = this.getState();
			
			//System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "state", "shift", "depth", "stepcost", "totalcost", "h");
			System.out.printf("%-20s%-5s%-5s%-5s%-5s%-5s\n", 
					state.getStateDefinition(), 
					((Action)state.getAction()).toString(),
					this.getDepth(), 
					state.getCostStep(), 
					state.getCostTotal(),
					state.getHeuristics());
			
			System.out.println(state.getStateDefinition());
		}
	}
}
