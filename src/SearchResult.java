
public class SearchResult{
		private float f;
		private boolean isGoalFound;
		private Node goalNode;
		
		public SearchResult(float f, boolean isGoalFound, Node goalNode){
			this.f = f;
			this.isGoalFound = isGoalFound;
			this.goalNode = goalNode;
		}
		
		public SearchResult(float f){
			this(f, false, null);
		}
		
		public float getF(){
			return this.f;
		}
		
		public boolean isGoalFound(){
			return this.isGoalFound;
		}
		
		public Node getGoalNode(){
			return this.goalNode;
		}
	}
