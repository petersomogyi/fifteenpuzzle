package eu.eit.fifteenpuzzle.graph;

import java.util.List;

import eu.eit.fifteenpuzzle.trie.CycleFoundException;
import eu.eit.fifteenpuzzle.trie.Node;

public interface GraphConf {

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
	public boolean isUpAvailable();
	public boolean isRightAvailable();
	public boolean isDownAvailable();
	public boolean isLeftAvailable();
	
	// Modifies the current vertex by moving to the desired direction.
	public Graph moveUp(Node currentNode) throws IllegalStateException;
	public Graph moveRight(Node currentNode) throws IllegalStateException;
	public Graph moveDown(Node currentNode) throws IllegalStateException;
	public Graph moveLeft(Node currentNode) throws IllegalStateException;
	
	// Returns a list with the successor combinations.
	public List<Graph> getSuccessors();
	
	// Returns a lower bound on its distance to the final configuration.
	public int getDistance();

	// Returns the steps from the initial configuration to the current one.
	public int getSteps();
	
	// Returns the previous movement
	public char getPreviousMove();
}
