package fifteenpuzzle.eit.eu;

import idastar.fifteenpuzzle.eit.eu.Idastar;
import idastar.fifteenpuzzle.eit.eu.NoSolutionException;
import graph.fifteenpuzzle.eit.eu.Graph;

public class FifteenOptimalSolution {

	public static void main(String[] args) {
		//int[] a = {1,2,3,7,4,5,0,6,8,9,10,11,12,13,14,15};
		//int[] a = {};
		int[] a = {10, 6, 4, 12, 1, 14, 3, 7, 5, 15, 11, 13, 8, 0, 2, 9};
		Graph v = new Graph(a);
		
		//testList(v);
		Idastar solver = new Idastar();
		try {
			solver.runIdaStar(v);
		} catch (NoSolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void movementTest01(Graph v) {
		System.out.println("*** Movement Test 01 ***");
		/*
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		*/
		System.out.println();
		
		v.printFormatted();
		
		v.moveUp().printFormatted();
		
		System.out.println();
		System.out.println("*** Movement Test 01 end ***");
		System.out.println();
	}
	
	public static void testList(Graph v) {
		v.printFormatted();
		System.out.println("****************");
		for (Graph g : v.getSuccessors()) {
			g.printFormatted();
			System.out.println();
		}
	}

}
