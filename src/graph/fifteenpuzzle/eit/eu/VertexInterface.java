package graph.fifteenpuzzle.eit.eu;

public interface VertexInterface {

	// Returns true if the current vertex is the final configuration
	// Represented as {0, 1, 2, ... 14, 15}
	public boolean isFinalConfiguration();
	
	// Print the vertex to the standard output as a string line.
	public void print();		
	
	// Print the vertex to the standard output formatted.
	public void printFormatted();
	
	// Returns an array of booleans with the available edges (movements)
	// {U, R, D, L}
	public boolean[] getAvailableEdges();	
	
	// Returns true if the edge (movement) is available
	// Direction means that we move the empty tile (0) to that direction.
	// TODO Create exception if move is not available
	public boolean isUpAvailable();
	public boolean isRightAvailable();
	public boolean isDownAvailable();
	public boolean isLeftAvailable();
	
	// Modifies the current vertex by moving to the desired direction
	public void moveUp();
	public void moveRight();
	public void moveDown();
	public void moveLeft();
	
	// Modifies the current vertex by moving to the direction passed by the parameter
	public void move(char direction);

	// Returns a lower bound on its distance to the final configuration using
	// the default heuristic
	// TODO Int might be enough instead of double. Has to match the heuristics!
	public double getDistance();
	
	// Returns a lower bound on its distance to the final configuration
	// using the heuristic passed by the parameter
	// TODO Discuss the possible heuristics
	// public double getDistance(String heuristic); // Not required yet!
}