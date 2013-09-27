package graph.fifteenpuzzle.eit.eu;

import java.util.Arrays;

public final class Vertex implements VertexInterface {

	// Represents the puzzle in a linear array with 16 elements.
	private final int[] tiles;

	// Constant for the final configuration.
	private final int[] FINAL_CONFIGURATION = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15 };

	// Constructor from int array.
	// TODO Discuss exception
	public Vertex(final int[] configuration) throws IllegalArgumentException {
		// Validate the configuration
		if (!isValidconfiguration(configuration))
			throw new IllegalArgumentException();

		// Copy the elements of the array to the tiles variable
		this.tiles = Arrays.copyOf(configuration, configuration.length);
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
		// TODO Throw exception.
		return i;
	}

	// Returns the row of the tile
	private int getRow(int tileNumber) {
		int i = 0;
		while (i < 16 && this.tiles[i] != tileNumber) {
			++i;
		}

		// Error
		// if (i >= 16)
		// throw new Exception();

		return i / 4;
	}

	private int getRow() {
		return getRow(0);
	}

	// Returns the column of the tile
	private int getColumn(int tileNumber) {
		int i = 0;
		while (i < 16 && this.tiles[i] != tileNumber) {
			++i;
		}

		// Error
		// if (i >= 16)
		// throw new Exception();

		return i % 4;
	}

	private int getColumn() {
		return getColumn(0);
	}

	// Return a boolean array which represents the available moves.
	// [Up, Right, Down, Left] --> e.g. [true, true, false, true]
	@Override
	public boolean[] getAvailableEdges() {
		boolean[] ret = new boolean[4];
		ret[0] = isUpAvailable();
		ret[1] = isRightAvailable();
		ret[2] = isDownAvailable();
		ret[3] = isLeftAvailable();
		return ret;
	}

	@Override
	public boolean isUpAvailable() {
		return getRow() > 0;
	}

	@Override
	public boolean isRightAvailable() {
		return getColumn() < 3;
	}

	@Override
	public boolean isDownAvailable() {
		return getRow() < 3;
	}

	@Override
	public boolean isLeftAvailable() {
		return getColumn() > 0;
	}

	@Override
	public Vertex moveUp() {
		if (isUpAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);
			
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 4];
			new_tiles[emptyIndex - 4] = 0;
			
			return new Vertex(new_tiles);
		} else {
			// TODO Throw exception?
			return null;
		}
	}

	@Override
	public Vertex moveRight() {
		if (isUpAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);
			
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 1];
			new_tiles[emptyIndex + 1] = 0;
			
			return new Vertex(new_tiles);
		} else {
			// TODO Throw exception?
			return null;
		}
	}

	@Override
	public Vertex moveDown() {
		if (isUpAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);
			
			new_tiles[emptyIndex] = new_tiles[emptyIndex + 4];
			new_tiles[emptyIndex + 4] = 0;
			
			return new Vertex(new_tiles);
		} else {
			// TODO Throw exception?
			return null;
		}
	}

	@Override
	public Vertex moveLeft() {
		if (isLeftAvailable()) {
			int emptyIndex = getEmptyIndex();
			int[] new_tiles = Arrays.copyOf(this.tiles, this.tiles.length);
			
			new_tiles[emptyIndex] = new_tiles[emptyIndex - 1];
			new_tiles[emptyIndex - 1] = 0;
			
			return new Vertex(new_tiles);
		} else {
			// TODO Throw exception?
			return null;
		}
	}

	@Override
	public Vertex move(char direction) {
		if (direction == 'U') {
			return moveUp();
		} else if (direction == 'R') {
			return moveRight();
		} else if (direction == 'D') {
			return moveDown();
		} else if (direction == 'L') {
			return moveLeft();
		} else {
			return null;
		}
	}

	// Calculates the minimum distance of the puzzle
	// using Manhattan Distance heuristic
	// Link: http://heuristicswiki.wikispaces.com/Manhattan+Distance
	@Override
	public double getDistance() {
		double dist = 0;
		
		for (int i=0; i < 16; ++i) {
			if (this.tiles[i] != 0) {
				dist += Math.abs((this.tiles[i] % 4) - (i % 4));
				dist += Math.abs((this.tiles[i] / 4) - (i / 4));
			}
		}
		
		return dist;
	}

}
