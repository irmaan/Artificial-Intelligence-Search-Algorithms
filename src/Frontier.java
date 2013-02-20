import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

 
public class Frontier {
	
	private PriorityQueue<Node> frontierPQ;
	private HashMap<Node, Float> frontierValuesMap;
	
	public Frontier(Comparator<Node> comparator){
		frontierPQ = new PriorityQueue<Node>(5, comparator); 
		frontierValuesMap = new HashMap<Node, Float>();
	}
	
	public void add(Node n, float value){
		this.frontierPQ.add(n);
		this.frontierValuesMap.put(n, value);
	}
	
	public boolean contains(Node n){
		return this.frontierValuesMap.containsKey(n);
	}
	
	public Node peek(){
		return this.frontierPQ.peek();
	}
	
	public Node poll(){
		Node n = this.frontierPQ.poll();
		this.frontierValuesMap.remove(n);
		return n;
	}
	
	public float getValue(Node n){
		return this.frontierValuesMap.get(n);
	}
	
	public void replace(Node n, float value){
		this.frontierValuesMap.put(n, value);
	}
	
	public boolean isEmpty(){
		return this.frontierPQ.isEmpty();
	}
	
}
