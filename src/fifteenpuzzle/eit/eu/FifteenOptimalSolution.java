package fifteenpuzzle.eit.eu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import trie.fifteenpuzzle.eit.eu.Trie;
import idastar.fifteenpuzzle.eit.eu.Idastar;
import idastar.fifteenpuzzle.eit.eu.NoSolutionException;
import graph.fifteenpuzzle.eit.eu.Graph;

public class FifteenOptimalSolution {

	
	public static void main(String[] args) {
		//int[] a = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int[] a = {1,2,3,7,4,5,0,6,8,9,10,11,12,13,14,15};
		//int[] a = {10, 6, 4, 12, 1, 14, 3, 7, 5, 15, 11, 13, 8, 0, 2, 9};
		
		//reading the filename from the standard io
		String fileName = getFileName();
		//getting the initial state from the file
		try {
			a = getInitConfig(fileName);
		} catch (FileNotFoundException e1) {
			System.err.println("The file does not exist!");
			return;
		}
		
		
		Trie t = new Trie();
        String[] words = {"lurdlurdlurd", "urdlurdlurdl", "rdlurdlurdlu", "dlurdlurdlur", 
						  "ruldruldruld", "uldruldruldr", "ldruldruldru", "druldruldrul",
						  "dllurrdllurrdllurr","llurrdllurrdllurrd","lurrdllurrdllurrdl","urrdllurrdllurrdll","rrdllurrdllurrdllu","rdllurrdllurrdllur",
						  "drrulldrrulldrrull","rrulldrrulldrrulld","rulldrrulldrrulldr","ulldrrulldrrulldrr","lldrrulldrrulldrru","ldrrulldrrulldrrul",
						  "ddruulddruulddruul","druulddruulddruuld","ruulddruulddruuldd","uulddruulddruulddr","ulddruulddruulddru","lddruulddruulddruu",
						  "ddluurddluurddluur","dluurddluurddluurd","luurddluurddluurdd","uurddluurddluurddl","urddluurddluurddlu","rddluurddluurddluu",
						  "ddlluurrddlluurrddlluurr","dlluurrddlluurrddlluurrd","lluurrddlluurrddlluurrdd","luurrddlluurrddlluurrddl","uurrddlluurrddlluurrddll","urrddlluurrddlluurrddllu","rrddlluurrddlluurrddlluu","rddlluurrddlluurrddlluur",
						  "ddrruullddrruullddrruull","drruullddrruullddrruulld","rruullddrruullddrruulldd","ruullddrruullddrruullddr","uullddrruullddrruullddrr","ullddrruullddrruullddrru","llddrruullddrruullddrruu","lddrruullddrruullddrruul"};
        for (int i = 0; i < words.length; i++)
            t.insertWord(words[i]);
        
		Graph v = new Graph(a,t,t.root);
		
	
		Idastar solver = new Idastar();
	
		System.out.println("Start searching...");
		// Timer
		long startTime = System.currentTimeMillis();
		
		System.out.println("Steps: " + solver.resolve(v));
		
		//Calculate elapsed time
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		System.out.print("Solution: ");
		solver.getPath();
		System.out.print("Number of checked nodes: ");
		solver.getCounter();
		
		System.out.println("Running time: " + totalTime + " ms");
		
		//Check the solution
		//TODO: throw errors
		System.out.println("Optimal step number: " + getOptimalStepNum(fileName));
	}
	
	//read filename from the standard io
	//after listing the content of test folder
	public static String getFileName(){
		File f = new File("Tests");
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		for (String n : names) {
			if (n.matches("^(easy|korf).[0-9]{3}$"))
				System.out.println(n);
		}
		System.out.println("Choose an input file:");
		
		Scanner input = new Scanner(System.in);
	    String fileName = input.nextLine();
		input.close();
		return fileName;
	}
	
	//TODO: add more exceptions
	//check that there are really 15 numbers in the file
	public static int[] getInitConfig(String fileName) throws FileNotFoundException {
		Scanner scanner;
		int[] a = new int[16];
		try {
			//can throw FileNotfoundException
			scanner = new Scanner(new File("Tests/"+fileName));
			
			int i = 0;
			while(scanner.hasNextInt()){
			   a[i++] = scanner.nextInt();
			}
			scanner.close();
			return a;
		} catch (FileNotFoundException e1) {
			throw e1;
		}
	}
	
	//read file.ref
	//TODO also check the "ok" part
	//check the exceptions
	public static int getOptimalStepNum(String fileName){
		Scanner scanner;
		int num = -1;
		String isSolution= "";
		try {
			//can throw FileNotfoundException
			scanner = new Scanner(new File("Tests/"+fileName + ".ref"));
			
			while(!scanner.hasNextInt()){
				isSolution = isSolution + scanner.next();
	        }
			if (scanner.hasNextInt()) {
			   num = scanner.nextInt();
			}
			scanner.close();
			return num;
		} catch (FileNotFoundException e1) {
			//if the .ref pair of a correct test file does not exist
			e1.printStackTrace();
		}
		return num;
	}
}
