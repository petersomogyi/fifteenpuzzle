package fifteenpuzzle.eit.eu;

import java.util.Arrays;

import graph.fifteenpuzzle.eit.eu.Vertex;

public class FifteenOptimalSolution {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		Vertex v = new Vertex(a);
		
		System.out.println(Arrays.toString(v.getAvailableEdges()));
		
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		
		System.out.println();
		
		v.printFormatted();
		
		v.moveUp();
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		
		System.out.println();
		
		v.printFormatted();
		

		v.moveLeft();
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		
		System.out.println();
		
		v.printFormatted();

		v.moveDown();
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		
		System.out.println();
		
		v.printFormatted();

		v.moveRight();
		System.out.println("Final:\t" + v.isFinalConfiguration());
		System.out.println("Up:\t" + v.isUpAvailable());
		System.out.println("Right:\t" + v.isRightAvailable());
		System.out.println("Down:\t" + v.isDownAvailable());
		System.out.println("Left\t" + v.isLeftAvailable());
		
		System.out.println();
		
		v.printFormatted();
	}

}
