package fifteenpuzzle.eit.eu;

import graph.fifteenpuzzle.eit.eu.Graph;
import gui.fifteenpuzzle.eit.eu.MainFrame;
import idastar.fifteenpuzzle.eit.eu.Idastar;
import idastar.fifteenpuzzle.eit.eu.NoSolutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import trie.fifteenpuzzle.eit.eu.Trie;

public class Controller {

	Graph graph;
	Idastar solver;
	MainFrame gui;

	public Controller() {
		gui = new MainFrame(this);
	}

	public void openConfigurationFile(File file) throws FileNotFoundException,
			IllegalArgumentException {
		// TODO: reimplement
		Scanner scanner;
		int[] a = new int[16];
		// can throw FileNotfoundException
		scanner = new Scanner(file);

		int i = 0;
		while (scanner.hasNextInt()) {
			a[i++] = scanner.nextInt();
		}
		scanner.close();

		// Create root graph
		graph = new Graph(a);
	}
	
	public void solveConfiguration() throws NoSolutionException {
		solver = new Idastar();
		SearchResult result = solver.resolve(graph);
		solutionFound(result);
	}
	
	private void solutionFound(SearchResult result) {
		gui.displayResult(result);
	}

}
