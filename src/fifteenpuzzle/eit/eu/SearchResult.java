package fifteenpuzzle.eit.eu;

// This class is responsible to store the results from the IDA* search.
// Stores
// - the optimal step number
// - an optimal path from the starting configuration
// - the number of visited nodes
// - the searching time
public class SearchResult {

	private int steps;
	private char[] path;
	private int visitedNodes;
	private long runningTime;

	public SearchResult(int steps, char[] path, int visitedNodes,
			long runningTime) {
		this.steps = steps;
		this.path = path;
		this.visitedNodes = visitedNodes;
		this.runningTime = runningTime;
	}

	public int getSteps() {
		return steps;
	}

	public char[] getPath() {
		return path;
	}

	public int getVisitedNodes() {
		return visitedNodes;
	}

	public long getRunningTime() {
		return runningTime;
	}
}
