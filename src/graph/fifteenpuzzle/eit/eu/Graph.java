package graph.fifteenpuzzle.eit.eu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trie.fifteenpuzzle.eit.eu.CycleFoundException;
import trie.fifteenpuzzle.eit.eu.Trie;

public final class Graph implements GraphConf {

	// Represents the puzzle in a linear array with 16 elements.
	private final int[] tiles;
	// Number of steps from the root
	private final int steps;
	// Direction of the previous move to check the
	private final char previousMove;

	private final Trie trie;
	private final Trie.TrieNode trieCurrent;

	// Index of the empty tile in the array
	private final int emptyIndex;

	// Constant for the final configuration.
	private final int[] FINAL_CONFIGURATION = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15 };

	// Constructor from int array.
	public Graph(final int[] configuration, final Trie root,
			final Trie.TrieNode current) throws IllegalArgumentException {
		// Validate the configuration
		if (!isValidconfiguration(configuration))
			throw new IllegalArgumentException();

		// Copy the elements of the array to the tiles variable
		this.tiles = Arrays.copyOf(configuration, configuration.length);
		this.steps = 0;
		// Store the empty index
		this.emptyIndex = getEmptyIndex();
		// There were no move from the root element
		this.previousMove = '-';
		this.trie = root;
		this.trieCurrent = current;
	}

	private Graph(final int[] configuration, final int steps,
			final char previousMove, final Trie root,
			final Trie.TrieNode current) {
		// Copy the elements of the array to the tiles variable
		this.tiles = Arrays.copyOf(configuration, configuration.length);

		this.steps = steps + 1;

		this.emptyIndex = getEmptyIndex();

		this.previousMove = previousMove;
		this.trie = root;
		this.trieCurrent = current;
	}

	// Validates a configuration if it matches the requirements.
	// 16 integer elements (from 0 to 16) representing the different tiles.
	// All tiles are in the list exactly one time.
	private boolean isValidconfiguration(final int[] configuration) {
		// Check for null parameter.
		if (null == configuration)
			return false;

		// Check if exactly 16 tiles are passed.
		if (configuration.length != 16)
			return false;

		// Check if all tiles are in the array (0-15).
		for (int i = 0; i < 16; ++i) {
			int j = 0;
			while (j < 16 && configuration[j] != i) {
				++j;
			}
			if (j >= 16)
				return false;
		}

		// Validation finished successfully.
		return true;
	}

	// 15 puzzle solvability
	// Background:
	// http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
	// Note: The final solution is different in most of the examples
	// (the empty tile needs to be in the final place)
	// The solvability is modified based on this difference.
	//
	// Sum the inversion number of the tiles
	// Because the grid's width is even, we have to check the empty tile's
	// position.
	// If it is in the first or third row, then the inversion number has to be
	// even,
	// otherwise it needs to be odd!
	public boolean isSolvable() {
		int inversion = 0;

		for (int i = 0; i < 16; ++i) {
			for (int j = i + 1; j < 16; ++j) {
				if (tiles[i] > tiles[j] && tiles[i] != 0 && tiles[j] != 0)
					// Inversion (do not count the empty tile!)
					inversion++;
			}
		}

		if ((emptyIndex / 4) % 2 == 0) {
			// First or third row -> inversion even
			return inversion % 2 == 0;
		} else {
			// Second or fourth row -> inversion odd
			return inversion % 2 == 1;
		}
	}

	// Returns true if the current configuration is the final one.
	@Override
	public boolean isFinalConfiguration() {
		return Arrays.equals(this.tiles, this.FINAL_CONFIGURATION);
	}

	// Print the vertex to the standard output as a string line.
	@Override
	public void print() {
		for (int e : this.tiles) {
			System.out.print(e + " ");
		}
		System.out.println();
	}

	// Print the vertex to the standard output formatted.
	@Override
	public void printFormatted() {
		for (int i = 0; i < 16; ++i) {
			int e = this.tiles[i];

			if (e < 10)
				// Adds leading space in front of the number
				System.out.print(" " + e + " ");
			else
				System.out.print(e + " ");
			if (i % 4 == 3)
				System.out.println();
		}

	}

	// Returns the empty tile's index
	private int getEmptyIndex() {
		int i = 0;
		while (i < 16) {
			if (this.tiles[i] == 0)
				return i;
			++i;
		}

		return i;
	}

	// Returns the row number of the empty tile
	private int getRow() {
		return this.emptyIndex / 4;
	}

	// Returns the column number of the empty tile
	private int getColumn() {
		return this.emptyIndex % 4;
	}

	// Return a boolean array which represents the available moves.
	// [Up, Right, Down, Left]
	// Example: [true, true, false, true]
	@Override
	public boolean[] getAvailableEdges() {
		boolean[] ret = new boolean[4];
		ret[0] = isUpAvailable();
		ret[1] = isRightAvailable();
		ret[2] = isDownAvailable();
		ret[3] = isLeftAvailable();
		return ret;
	}

	// Returns true if the up movement is enabled
	@Override
	public boolean isUpAvailable() {
		return getRow() > 0;
	}

	// Returns true if the right movement is enabled
	@Override
	public boolean isRightAvailable() {
		return getColumn() < 3;
	}

	// Returns true if the down movement is enabled
	@Override
	public boolean isDownAvailable() {
		return getRow() < 3;
	}

	// Returns true if the left movement is enabled
	@Override
	public boolean isLeftAvailable() {
		return getColumn() > 0;
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving up
	@Override
	public Graph moveUp(Trie.TrieNode currentNode) throws IllegalStateException {
		if (isUpAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 4];
			new_tiles[emptyIndex - 4] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'U', trie, currentNode);
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving right
	@Override
	public Graph moveRight(Trie.TrieNode currentNode)
			throws IllegalStateException {
		if (isRightAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 1];
			new_tiles[emptyIndex + 1] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'R', trie, currentNode);
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving down
	@Override
	public Graph moveDown(Trie.TrieNode currentNode)
			throws IllegalStateException {
		if (isDownAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 4];
			new_tiles[emptyIndex + 4] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'D', trie, currentNode);
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving left
	@Override
	public Graph moveLeft(Trie.TrieNode currentNode)
			throws IllegalStateException {
		if (isLeftAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 1];
			new_tiles[emptyIndex - 1] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'L', trie, currentNode);
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a list with the successor combinations
	public List<Graph> getSuccessors() {
		List<Graph> successors = new ArrayList<Graph>();

		// u,r,d,l are the corresponding next node in the trie,
		// unless we found a cycle, then we get an exception
		
		Trie.TrieNode u;
		Trie.TrieNode r;
		Trie.TrieNode d;
		Trie.TrieNode l;
		// Add the successor nodes to the list.
		try {
			u = trie.nextNode(trieCurrent, 'u');
			if (previousMove != 'D' && isUpAvailable()) {
				Graph up = moveUp(u);
				successors.add(up);
			}
		} catch (CycleFoundException e) {
			//cycle detected: left
		}
		try {
			r = trie.nextNode(trieCurrent, 'r');
			if (previousMove != 'L' && isRightAvailable()) {
				Graph right = moveRight(r);
				successors.add(right);
			}
		} catch (CycleFoundException e) {
			//cycle detected: right
		}
		try {
			d = trie.nextNode(trieCurrent, 'd');
			if (previousMove != 'U' && isDownAvailable()) {
				Graph down = moveDown(d);
				successors.add(down);
			}
		} catch (CycleFoundException e) {
			//cycle detected: down
		}
		try {
			l = trie.nextNode(trieCurrent, 'l');
			if (previousMove != 'R' && isLeftAvailable()) {
				Graph left = moveLeft(l);
				successors.add(left);
			}
		} catch (CycleFoundException e) {
			//cycle detected: left
		}

		// Return with the list of successors
		return successors;
	}

	// Calculates the minimum distance of the puzzle
	// using the Manhattan-Distance heuristic and linear conflicts
	// Link: http://heuristicswiki.wikispaces.com/Manhattan+Distance
	@Override
	public int getDistance() {
		// return manhattanDistance();
		return manhattanDistance() + linearConflicts();
	}

	// Returns the Manhattan-distance
	private int manhattanDistance() {
		int dist = 0;

		// Sum the individual distances from the correct position
		for (int i = 0; i < 16; ++i) {
			if (this.tiles[i] != 0) {
				dist += Math.abs((this.tiles[i] % 4) - (i % 4));
				dist += Math.abs((this.tiles[i] / 4) - (i / 4));
			}
		}

		return dist;
	}

	// Returns the distance caused by the linear conflicts
	// Link: http://heuristicswiki.wikispaces.com/Linear+Conflict
	public int linearConflicts() {
		int dist = 0;

		for (int row = 0; row < 4; ++row) {
			int max = -1;

			for (int col = 0; col < 4; ++col) {
				int val = tiles[row * 4 + col];
				if (val != 0 && val / 4 == row) {
					if (val > max) {
						max = val;
					} else {
						dist += 2;
					}
				}
			}
		}

		for (int col = 0; col < 4; ++col) {
			int max = -1;

			for (int row = 0; row < 4; ++row) {
				int val = tiles[row * 4 + col];
				if (val != 0 && val % 4 == col) {
					if (val > max) {
						max = val;
					} else {
						dist += 2;
					}
				}
			}
		}

		return dist;
	}

	@Override
	public int getSteps() {
		return this.steps;
	}

	public char getPreviousMove() {
		return previousMove;
	}

}
