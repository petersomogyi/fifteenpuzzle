package graph.fifteenpuzzle.eit.eu;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Graph implements GraphConf {

	// Represents the puzzle in a linear array with 16 elements.
	private final int[] tiles;
	// Number of steps from the root
	private final int steps;
	// Direction of the previous move to check the
	private final char previousMove;
	// Index of the empty tile in the array
	private final int emptyIndex;

	// Constant for the final configuration.
	private final int[] FINAL_CONFIGURATION = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15 };

	// Constructor from int array.
	public Graph(final int[] configuration) throws IllegalArgumentException {
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
	}

	private Graph(final int[] configuration, final int steps,
			final char previousMove) {
		// Copy the elements of the array to the tiles variable
		this.tiles = Arrays.copyOf(configuration, configuration.length);

		this.steps = steps + 1;

		this.emptyIndex = getEmptyIndex();

		this.previousMove = previousMove;
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

	// Returns the row of the tile
	private int getRow(int tileNumber) {
		int i = 0;
		while (i < 16 && this.tiles[i] != tileNumber) {
			++i;
		}

		return i / 4;
	}

	// Returns the row number of the empty tile
	private int getRow() {
		return this.emptyIndex / 4;
	}

	// Returns the column of the tile
	private int getColumn(int tileNumber) {
		int i = 0;
		while (i < 16 && this.tiles[i] != tileNumber) {
			++i;
		}

		return i % 4;
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
	public Graph moveUp() throws IllegalStateException {
		if (isUpAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 4];
			new_tiles[emptyIndex - 4] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'U');
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving right
	@Override
	public Graph moveRight() throws IllegalStateException {
		if (isRightAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 1];
			new_tiles[emptyIndex + 1] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'R');
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving down
	@Override
	public Graph moveDown() throws IllegalStateException {
		if (isDownAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 4];
			new_tiles[emptyIndex + 4] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'D');
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a new Vertex object which can be reached
	// from the current state by moving left
	@Override
	public Graph moveLeft() throws IllegalStateException {
		if (isLeftAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);

			// Swap the 2 values
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 1];
			new_tiles[emptyIndex - 1] = 0;

			// Create the new Vertex object
			return new Graph(new_tiles, this.getSteps(), 'L');
		} else {
			throw new IllegalStateException(
					"Operation cannot be executed in this state.");
		}
	}

	// Returns a list with the successor combinations
	public List<Graph> getSuccessors() {
		List<Graph> successors = new ArrayList<Graph>();

		// Add the successor nodes to the list.
		if (previousMove != 'D' && isUpAvailable()) {
			Graph up = moveUp();
			successors.add(up);
		}
		if (previousMove != 'L' && isRightAvailable()) {
			Graph right = moveRight();
			successors.add(right);
		}
		if (previousMove != 'U' && isDownAvailable()) {
			Graph down = moveDown();
			successors.add(down);
		}
		if (previousMove != 'R' && isLeftAvailable()) {
			Graph left = moveLeft();
			successors.add(left);
		}

		// Return with the list of successors
		return successors;
	}

	// Calculates the minimum distance of the puzzle
	// using the Manhattan-Distance heuristic and linear conflicts
	// Link: http://heuristicswiki.wikispaces.com/Manhattan+Distance
	@Override
	public int getDistance() {
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

	private boolean wrongOrder(int a, int b) {
		return (((a < b) && tiles[a] > tiles[b])
				|| ((a > b) && tiles[a] < tiles[b]));
	}

	@Override
	public int getSteps() {
		return this.steps;
	}

}
