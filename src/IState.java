
public interface IState {
	public String toString();
	public float getCostStep();
	public float getCostTotal();
	public float getHeuristics();
	public Object getAction();
	public String getStateDefinition();
	public int hashCode();
}
