package idastar.fifteenpuzzle.eit.eu;

import graph.fifteenpuzzle.eit.eu.Graph;

public interface IdastarConf {
	
	//
	public void runIdaStar (Graph root) throws NoSolutionException;
	
	//int might be enough
	//public double search (Graph node, double g, double bound);

}
