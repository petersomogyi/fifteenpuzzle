package eu.eit.fifteenpuzzle.idastar;

import eu.eit.fifteenpuzzle.SearchResult;
import eu.eit.fifteenpuzzle.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
IDA* algorithm pseudo code

node              current node
g                 the cost to reach current node
f                 estimated cost of the cheapest path (root..node..goal)
h(node)           estimated cost of the cheapest path (node..goal)
cost(node, succ)  path cost function
is_goal(node)     goal test
successors(node)  node expanding function

procedure ida_star(root, cost(), is_goal(), h())
  bound := h(root)
  loop
    t := search(root, 0, bound)
    if t = FOUND then return FOUND
    if t = infinite then return NOT_FOUND
    bound := t
  end loop
end procedure

function search(node, g, bound)
  f := g + h(node)
  if f > bound then return f
  if is_goal(node) then return FOUND
  min := infinite
  for succ in successors(node) do
    t := search(succ, g + cost(node, succ), bound)
    if t = FOUND then return FOUND
    if t < min then min := t
  end for
  return min
end function
*/

public final class Idastar implements IdastarConf {
	
	private long counter = 0;
	private Stack<Character> path;
	
	public SearchResult resolve(Graph start) throws NoSolutionException {
		
		if (!start.isSolvable()) {
			throw new NoSolutionException();
		}
		
		counter = 0;

		// Runtime calculation 
		long startTime = System.currentTimeMillis();
		
		Graph solution = null;
		int nextCost = start.getDistance();
		
		while (solution == null) {
			int currentCost = nextCost;
			solution = depthFirstSearch(start, currentCost);
			nextCost += 2;
		}
		
		//Calculate elapsed time
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		SearchResult result = new SearchResult(solution.getSteps(), getPath(), counter, totalTime);
		
		return result;
	}
	private Graph depthFirstSearch(Graph current, int currentCost) {
		if (current.isFinalConfiguration()) {
			path = new Stack<Character>();
			char move = current.getPreviousMove();
			if (move != '-') {
				path.push(move);
			}
			return current;
		}
		
		counter++;
		
		List<Graph> children = current.getSuccessors();
		
		for (Graph child : children) {
			int cost = child.getSteps() + child.getDistance();
			
			if (cost <= currentCost) {
				Graph result = depthFirstSearch(child, currentCost);
				if (result != null) {
					char move = current.getPreviousMove();
					if (move != '-') {
						path.push(move);
					}
					return result;
				}
			}
		}
		
		return null;
	}
	
	private char[] getPath() {
		ArrayList<Character> list = new ArrayList<Character>();
		while (!path.empty())
			list.add(path.pop());
		
		char[] ret = new char[list.size()];
		for (int i=0; i<list.size(); ++i)
				ret[i] = list.get(i);
		return ret;
	}
	
}
