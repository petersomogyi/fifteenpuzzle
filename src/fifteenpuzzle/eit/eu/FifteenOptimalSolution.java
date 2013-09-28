package fifteenpuzzle.eit.eu;

import java.util.Arrays;

import graph.fifteenpuzzle.eit.eu.Graph;

public class FifteenOptimalSolution {

	public static void main(String[] args) {
		int[] a = {15,1,2,3,4,5,6,7,8,9,10,11,0,13,14,12};
		Graph v = new Graph(a);
		
		movementTest01(v);
		
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

}
