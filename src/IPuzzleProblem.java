import java.util.List;

/**
 * 
 */


public interface IPuzzleProblem {

	public enum PuzzleType{
		ReguaPuzzle,
		TravessiaPonte
	}
	
	public Node getFirst();
	public boolean permitVisitedNodes();
	
	public PuzzleType getPuzzleType();
	public int N();
	public String toString();
	
	public List<Node> getDescendants(Node n);
	public boolean goalTest(IState state);
}