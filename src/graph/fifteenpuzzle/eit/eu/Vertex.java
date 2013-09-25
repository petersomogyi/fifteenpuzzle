package graph.fifteenpuzzle.eit.eu;

import java.util.Arrays;

public class Vertex implements VertexInterface {

	// Represents the puzzle in a linear array with 16 elements.
	private int[] tiles;

	// Constant for the final configuration.
	private final int[] FINAL_CONFIGURATION = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

	// Constructor from int array.
	// TODO Discuss exception
	public Vertex(final int[] configuration) throws IllegalArgumentException {
		// Validate the configuration
		if (!isValidconfiguration(configuration))
			throw new IllegalArgumentException();
		
		// Copy the elements of the array to the tiles variable
		this.tiles = new int[16];
		System.arraycopy(configuration, 0, this.tiles, 0, configuration.length);
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

	@Override
	public boolean[] getAvailableEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUpAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRightAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDownAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeftAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(char direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
