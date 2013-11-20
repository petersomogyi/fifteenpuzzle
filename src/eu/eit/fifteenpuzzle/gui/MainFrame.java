package eu.eit.fifteenpuzzle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import eu.eit.fifteenpuzzle.Controller;
import eu.eit.fifteenpuzzle.SearchResult;
import eu.eit.fifteenpuzzle.idastar.NoSolutionException;

// This class is responsible for the GUI of the application.
// Provides possibility to open configuration stored in files, solve it,
// display the search results and play an animated path from the starting
// configuration to the final one.
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -7277311210957252035L;

	// Controller
	private final Controller controller;

	// GUI components
	private JPanel pnlMain, pnlPuzzle, pnlControl, pnlButtonHolder;
	private JButton btnSolve, btnAnimate, btnOpen;
	private JLabel lblStepNo, lblRuntime, lblProgress, lblSteps, lblNodes;
	private JTextPane txpSteps;
	private PanelPuzzle puzzle;

	// Filechooser
	private final JFileChooser fileChooser;

	public MainFrame(final Controller controller) {
		this.controller = controller;

		pnlMain = new JPanel(new BorderLayout());
		pnlPuzzle = new JPanel();
		pnlControl = new JPanel();
		pnlButtonHolder = new JPanel(new FlowLayout());
		btnSolve = new JButton("Solve");
		btnAnimate = new JButton("Play");
		btnOpen = new JButton("Open");
		lblStepNo = new JLabel("Optimal steps: -");
		lblRuntime = new JLabel("Running time: -");
		lblNodes = new JLabel("Configurations: -");
		lblSteps = new JLabel("Steps");
		txpSteps = new JTextPane();

		// Get current directory
		File f = new File(".");
		fileChooser = new JFileChooser(f.getAbsolutePath());

		puzzle = new PanelPuzzle();
		lblProgress = new JLabel(new ImageIcon("loading.gif"));

		pnlControl.setPreferredSize(new Dimension(245, 300));
		pnlControl.setMaximumSize(new Dimension(245, 300));
		pnlControl.setMinimumSize(new Dimension(245, 300));

		// Borders
		pnlPuzzle.setBorder(BorderFactory.createTitledBorder("Puzzle"));
		pnlControl.setBorder(BorderFactory.createTitledBorder("Control"));
		txpSteps.setBorder(new LineBorder(Color.LIGHT_GRAY));

		txpSteps.setEditable(false);

		btnOpen.setEnabled(true);
		btnSolve.setEnabled(false);
		btnAnimate.setEnabled(false);

		btnOpen.setPreferredSize(new Dimension(72, 25));
		btnSolve.setPreferredSize(new Dimension(72, 25));
		btnAnimate.setPreferredSize(new Dimension(72, 25));

		// Handle Click event for Open button
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnOpen) {
					int returnVal = fileChooser.showOpenDialog(MainFrame.this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();

						try {
							int[] config;
							config = controller.openConfigurationFile(file);
							puzzle.drawInitConfig(config);

							lblStepNo.setText("Optimal steps: -");
							lblRuntime.setText("Running time: -");
							lblNodes.setText("Configurations: -");
							txpSteps.setText("");
							btnSolve.setEnabled(true);
						} catch (FileNotFoundException e1) {
							btnSolve.setEnabled(false);
							JOptionPane
									.showMessageDialog(null,
											"The file was not found!",
											"File not found",
											JOptionPane.ERROR_MESSAGE);
						} catch (IllegalArgumentException e1) {
							btnSolve.setEnabled(false);
							JOptionPane.showMessageDialog(null,
									"The provided configuration is not valid!",
									"Not valid configuration",
									JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							btnSolve.setEnabled(false);
							JOptionPane.showMessageDialog(null,
									"The provided configuration is not valid!",
									"Not valid configuration",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} finally {
							btnAnimate.setEnabled(false);
						}
					} else {
						// Open command cancelled by user.
					}
				}
			}
		});

		// Handle Click event for Solve button
		btnSolve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnSolve) {
					btnOpen.setEnabled(false);
					btnSolve.setEnabled(false);
					btnAnimate.setEnabled(false);
					lblProgress.setVisible(true);
					new Thread(new Runnable() {
						public void run() {
							try {
								controller.solveConfiguration();
							} catch (NoSolutionException e) {
								btnOpen.setEnabled(true);
								btnSolve.setEnabled(true);
								btnAnimate.setEnabled(false);
								lblProgress.setVisible(false);

								txpSteps.setText("The current configuration cannot be solved!");
								JOptionPane
										.showMessageDialog(
												null,
												"The current configuration cannot be solved!",
												"Unsolvable configuration",
												JOptionPane.ERROR_MESSAGE);
							}
						}
					}).start();

				}

			}
		});

		// Handle Click event for Animate button
		btnAnimate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnOpen.setEnabled(false);
				btnSolve.setEnabled(false);
				btnAnimate.setEnabled(false);
				lblProgress.setVisible(true);
				controller.playSolution();
			}
		});

		setLayout(new BorderLayout());

		// Set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fifteen Puzzle Solver");
		setSize(new Dimension(530, 350));
		setResizable(false);
		setLocationRelativeTo(getRootPane());

		// Add elements to the window
		pnlPuzzle.add(puzzle);

		pnlButtonHolder.add(btnOpen);
		pnlButtonHolder.add(btnSolve);
		pnlButtonHolder.add(btnAnimate);

		pnlControl.setLayout(null);

		pnlControl.add(pnlButtonHolder);
		pnlControl.add(lblRuntime);
		pnlControl.add(lblStepNo);
		pnlControl.add(lblNodes);
		pnlControl.add(lblSteps);
		pnlControl.add(txpSteps);
		pnlControl.add(lblProgress);

		pnlButtonHolder.setLocation(5, 25);
		lblRuntime.setLocation(10, 60);
		lblStepNo.setLocation(10, 80);
		lblNodes.setLocation(10, 100);
		lblSteps.setLocation(10, 120);
		txpSteps.setLocation(10, 150);
		lblProgress.setLocation(85, 250);

		pnlButtonHolder.setSize(new Dimension(235, 40));
		lblRuntime.setSize(new Dimension(235, 22));
		lblStepNo.setSize(new Dimension(235, 22));
		lblSteps.setSize(new Dimension(235, 22));
		lblNodes.setSize(new Dimension(235, 22));
		txpSteps.setSize(new Dimension(225, 86));
		lblProgress.setSize(new Dimension(64, 64));

		lblProgress.setVisible(false);

		pnlButtonHolder.setVisible(true);

		pnlMain.add(pnlPuzzle);
		pnlMain.add(pnlControl, BorderLayout.EAST);

		add(pnlMain);

		setVisible(true);
	}

	// Displays the result of the search
	public void displayResult(SearchResult result) {
		String path = "";
		controller.setResult(result);
		for (char c : result.getPath())
			path += c + " ";

		// Format time
		long millis = result.getRunningTime();
		String time = String.format(
				"%02dm %02ds %03dms",
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)), // The change is in this line
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)), millis % 1000);
		lblRuntime.setText("Running time: " + time);
		lblStepNo.setText("Optimal steps: " + result.getSteps());
		lblNodes.setText("Configurations: "
				+ NumberFormat.getNumberInstance().format(
						result.getVisitedNodes()));
		txpSteps.setText(path);

		btnOpen.setEnabled(true);
		btnSolve.setEnabled(true);
		btnAnimate.setEnabled(true);
		lblProgress.setVisible(false);
	}

	public void step(char c) {
		switch (c) {
		case 'L':
			puzzle.moveLeft();
			break;
		case 'R':
			puzzle.moveRight();
			break;
		case 'U':
			puzzle.moveUp();
			break;
		case 'D':
			puzzle.moveDown();
			break;
		}
	}

	public void playFinished() {
		lblProgress.setVisible(false);
		btnAnimate.setEnabled(false);
		btnSolve.setEnabled(false);
		btnOpen.setEnabled(true);
	}

}
