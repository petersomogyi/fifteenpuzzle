package fifteenpuzzle.eit.eu;

import graph.fifteenpuzzle.eit.eu.Graph;
import gui.fifteenpuzzle.eit.eu.MainFrame;
import idastar.fifteenpuzzle.eit.eu.Idastar;
import idastar.fifteenpuzzle.eit.eu.NoSolutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import trie.fifteenpuzzle.eit.eu.Trie;

public class Controller {

	Graph graph;
	Idastar solver;
	MainFrame gui;
	private SearchResult result;

	public Controller() {
		gui = new MainFrame(this);
	}

	public int[] openConfigurationFile(File file) throws FileNotFoundException,
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
		
		Trie t = new Trie();
        String[] words = {"lurdlurdlurd", "urdlurdlurdl", "rdlurdlurdlu", "dlurdlurdlur", 
						  "ruldruldruld", "uldruldruldr", "ldruldruldru", "druldruldrul",
						  "dllurrdllurrdllurr","llurrdllurrdllurrd","lurrdllurrdllurrdl","urrdllurrdllurrdll","rrdllurrdllurrdllu","rdllurrdllurrdllur",
						  "drrulldrrulldrrull","rrulldrrulldrrulld","rulldrrulldrrulldr","ulldrrulldrrulldrr","lldrrulldrrulldrru","ldrrulldrrulldrrul",
						  "ddruulddruulddruul","druulddruulddruuld","ruulddruulddruuldd","uulddruulddruulddr","ulddruulddruulddru","lddruulddruulddruu",
						  "ddluurddluurddluur","dluurddluurddluurd","luurddluurddluurdd","uurddluurddluurddl","urddluurddluurddlu","rddluurddluurddluu",
						  "ddlluurrddlluurrddlluurr","dlluurrddlluurrddlluurrd","lluurrddlluurrddlluurrdd","luurrddlluurrddlluurrddl","uurrddlluurrddlluurrddll","urrddlluurrddlluurrddllu","rrddlluurrddlluurrddlluu","rddlluurrddlluurrddlluur",
						  "ddrruullddrruullddrruull","drruullddrruullddrruulld","rruullddrruullddrruulldd","ruullddrruullddrruullddr","uullddrruullddrruullddrr","ullddrruullddrruullddrru","llddrruullddrruullddrruu","lddrruullddrruullddrruul"};
        //Arrays.sort(words);
        for (i = 0; i < words.length; i++)
            t.insertWord(words[i]);
		// Create root graph
		graph = new Graph(a,t,t.root);

		return a;
	}

	public void solveConfiguration() throws NoSolutionException {
		solver = new Idastar();
		SearchResult result = solver.resolve(graph);
		solutionFound(result);
	}

	private void solutionFound(SearchResult result) {
		gui.displayResult(result);
	}

	public void playSolution() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (char c : result.getPath()) {
					gui.step(c);
				}
				playFinished();
			}
		}).start();
	}
	
	public void playFinished() {
		gui.playFinished();
	}

	public void setResult(SearchResult result) {
		this.result = result;
	}
}
