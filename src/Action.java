import java.util.HashSet;
import java.util.Set;


public class Action {
	
	private final int shift;
	private Set<Integer> people;
	private String direction;
	
	public Action(){ 
		this(0, new HashSet<Integer>(), "");
	}
	
    public Action(int shift) { 
    	this(shift, new HashSet<Integer>(), ""); 
    }
    
    public Action(int shift, Set<Integer> people, String direction) { 
    	this.shift = shift;
    	this.people = people; 
    	this.direction = direction;
    }
    
    public int getShift() { return shift; }
    
    public Set<Integer> getPeople(){ return people; }
    
    public String getDirection(){ return direction; }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(this.direction + " ");
    	sb.append(this.shift + " ");
    	sb.append(this.people + " ");
    	
    	return sb.toString();
    }
}
