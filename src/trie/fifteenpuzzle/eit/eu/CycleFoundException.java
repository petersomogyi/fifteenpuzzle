package trie.fifteenpuzzle.eit.eu;

public class CycleFoundException extends Exception {
	
	private char step;
	
	public CycleFoundException(char step) {
		this.step = step;
	}
	
	public char getStep() {
		return step;
	}
}
