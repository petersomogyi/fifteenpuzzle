package idastar.fifteenpuzzle.eit.eu;

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
	
	//
	public void runIdaStar (Graph root) throws NoSolutionException{
		//if (root == null) System.err.println("NULL");
		//root.printFormatted();
		double bound = root.getSteps();
		while (true) {
			double t = search (root, 0, bound);
			if (t == 0) {//FOUND - distance can be positive or 0
				System.out.println("FOUND SOLUTION");
				return;
			} else if (t == Double.MAX_VALUE) {
				System.err.println("NO SOLUTION");
				throw new NoSolutionException();
			} else {
				bound = t;
			}
		}
	}
	
	//int might be enough
	public double search (Graph node, double g, double bound){
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
			//TODO:getDistance(node) implementation
			if (succ == null) System.err.println("NULL");
			//System.out.println("Steps: " + node.getSteps());
			//node.printFormatted();
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

}