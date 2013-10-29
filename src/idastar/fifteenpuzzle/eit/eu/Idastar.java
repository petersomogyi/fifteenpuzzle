package idastar.fifteenpuzzle.eit.eu;

import java.util.List;
import java.util.Stack;

import graph.fifteenpuzzle.eit.eu.Graph;

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
	
	private static int counter = 0;
	private Stack<Character> path;
	
	//
	public void runIdaStar (Graph root) throws NoSolutionException{
		//if (root == null) System.err.println("NULL");
		//root.printFormatted();
		/*double bound = root.getSteps();
		while (true) {
			double t = search (root, 0, bound);
			if (t == 0) {//FOUND - distance can be positive or 0
				System.out.println("FOUND SOLUTION");
				System.out.println("Number of steps totally: "  + counter);
				return;
			} else if (t == Double.MAX_VALUE) {
				System.err.println("NO SOLUTION");
				throw new NoSolutionException();
			} else {
				bound = t;
			}
		}*/
	}
	
	/*//int might be enough
	public double search (Graph node, double g, double bound){
		counter++;
		double f = g + node.getSteps();
		System.out.println("Steps: " + node.getSteps());
		if (f > bound) return f;
		if (node.isFinalConfiguration()) {
			System.out.println("Steps: " + node.getSteps());
			node.printFormatted();
			return 0;
		}
		double min = Double.MAX_VALUE;
		for (Graph succ : node.getSuccessors()) {
			double t = search (succ, g + node.getDistance(), bound);
			if (t == 0) { //FOUND - distance can be positive or 0
				System.out.println("Steps: " + node.getSteps());
				node.printFormatted();
				return 0;
			}
			if (t < min) 
				min = t;
		}
		return min;
	}
	*/
	public int resolve(Graph start) {
		counter = 0;
		
		Graph solution = null;
		int nextCost = start.getDistance();
		
		while (solution == null) {
			int currentCost = nextCost;
			solution = depthFirstSearch(start, currentCost);
			nextCost += 2;
		}
		
		return solution.getSteps();
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
	
	public void getPath() {
		while (!path.empty())
			System.out.print(path.pop() + " ");
		
		System.out.println();
	}
	
	public void getCounter() {
		System.out.println(counter);
	}

}
