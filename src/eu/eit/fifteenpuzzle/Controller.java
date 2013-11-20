package eu.eit.fifteenpuzzle;

import eu.eit.fifteenpuzzle.graph.Graph;
import eu.eit.fifteenpuzzle.gui.MainFrame;
import eu.eit.fifteenpuzzle.idastar.Idastar;
import eu.eit.fifteenpuzzle.idastar.NoSolutionException;
import eu.eit.fifteenpuzzle.trie.CycleFoundException;
import eu.eit.fifteenpuzzle.trie.Trie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// This class controls the GUI and the search engine and provides the required methods for communication.
public class Controller {

	Graph graph;
	Idastar solver;
	MainFrame gui;
	private SearchResult result;

	public Controller() {
		// Creates the GUI
		gui = new MainFrame(this);
	}

	// Reads the start configuration from the file into an array and creates the
	// graph. Initializes the Trie for the search. Throws exception in case the
	// file does not meet the requirements.
	public int[] openConfigurationFile(File file)
			throws IllegalArgumentException, IOException {

		int[] config = null;

		// Read the configuration from the file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line = null;

			if ((line = br.readLine()) != null) {
				String noInStringArr[] = line.split(" ");
				config = new int[noInStringArr.length];
				for (int i = 0; i < config.length; ++i) {
					config[i] = Integer.parseInt(noInStringArr[i]);
				}
			}

		} catch (IOException e) {
			throw e;
		}

		// Configure the trie
		Trie t = new Trie();
		String[] words = { "lurdlurdlurd", "urdlurdlurdl", "rdlurdlurdlu",
				"dlurdlurdlur", "ruldruldruld", "uldruldruldr", "ldruldruldru",
				"druldruldrul", "dllurrdllurrdllurr", "llurrdllurrdllurrd",
				"lurrdllurrdllurrdl", "urrdllurrdllurrdll",
				"rrdllurrdllurrdllu", "rdllurrdllurrdllur",
				"drrulldrrulldrrull", "rrulldrrulldrrulld",
				"rulldrrulldrrulldr", "ulldrrulldrrulldrr",
				"lldrrulldrrulldrru", "ldrrulldrrulldrrul",
				"ddruulddruulddruul", "druulddruulddruuld",
				"ruulddruulddruuldd", "uulddruulddruulddr",
				"ulddruulddruulddru", "lddruulddruulddruu",
				"ddluurddluurddluur", "dluurddluurddluurd",
				"luurddluurddluurdd", "uurddluurddluurddl",
				"urddluurddluurddlu", "rddluurddluurddluu",
				"ddlluurrddlluurrddlluurr", "dlluurrddlluurrddlluurrd",
				"lluurrddlluurrddlluurrdd", "luurrddlluurrddlluurrddl",
				"uurrddlluurrddlluurrddll", "urrddlluurrddlluurrddllu",
				"rrddlluurrddlluurrddlluu", "rddlluurrddlluurrddlluur",
				"ddrruullddrruullddrruull", "drruullddrruullddrruulld",
				"rruullddrruullddrruulldd", "ruullddrruullddrruullddr",
				"uullddrruullddrruullddrr", "ullddrruullddrruullddrru",
				"llddrruullddrruullddrruu", "lddrruullddrruullddrruul" };

		for (int i = 0; i < words.length; i++)
			t.add(words[i]);
		t.setLinks(t.getRootNode());

		// Create root graph
		graph = new Graph(config, t, t.getRootNode());

		// Returns with the initial configuration to display it
		return config;
	}

	// Solves the configuration stored in the graph member.
	// Notifies the GUI when the solution was found.
	public void solveConfiguration() throws NoSolutionException {
		solver = new Idastar();
		SearchResult result = solver.resolve(graph);
		solutionFound(result);
	}

	// Notifies the GUI about the solution and passes the result to it.
	private void solutionFound(SearchResult result) {
		gui.displayResult(result);
	}

	// Plays the solution on the GUI by moving the tiles.
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

	// Notifies the GUI is the animation has finished.
	public void playFinished() {
		gui.playFinished();
	}

	// Stores the result in a member variable.
	public void setResult(SearchResult result) {
		this.result = result;
	}
}
