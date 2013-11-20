package eu.eit.fifteenpuzzle.idastar;

public class NoSolutionException extends Exception {
	
	private static final long serialVersionUID = -3754685796566861612L;

	public NoSolutionException() {
		super("The provided configuration cannot be solved!");
	}
	
	public NoSolutionException(String message) {
		super(message);
	}

}
