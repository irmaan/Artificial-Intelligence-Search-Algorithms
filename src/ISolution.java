import java.util.List;

public interface ISolution {
	public void printSolution();
	public void printPath();
	public void setPath(List<Node> nodes);
	public void setAlgorithmName(String searchAlgorithmName);
	public boolean solved();
	public ISolution copy();
	public void addSubSolution(ISolution subSolution);
	public void addToExplored(int addition);
	public void addToGenerated(int addition);
}
