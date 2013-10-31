package gui.fifteenpuzzle.eit.eu;

import idastar.fifteenpuzzle.eit.eu.NoSolutionException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

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

import fifteenpuzzle.eit.eu.Controller;
import fifteenpuzzle.eit.eu.SearchResult;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -7277311210957252035L;

	// Controller
	private final Controller controller;

	// GUI components
	private JPanel pnlMain, pnlPuzzle, pnlControl, pnlButtonHolder;
	private JButton btnSolve, btnAnimate, btnOpen;
	private JLabel lblStepNo, lblRuntime, lblProgress, lblSteps;
	private JTextPane txpSteps;
	//private JLabel lblPuzzle; // TODO: change to the implemented version
	private PanelPuzzle puzzle; // TODO: change to the implemented version

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
		lblSteps = new JLabel("Steps");
		txpSteps = new JTextPane();

		File f = new File(".");
		fileChooser = new JFileChooser(f.getAbsolutePath());

		//lblPuzzle = new JLabel("Puzzle position");
		puzzle = new PanelPuzzle();
		puzzle.setLayout(new GridLayout(4,4,5,5));
		lblProgress = new JLabel(new ImageIcon("loading.gif"));

		pnlControl.setPreferredSize(new Dimension(245, 600));
		pnlControl.setMaximumSize(new Dimension(245, 600));
		pnlControl.setMinimumSize(new Dimension(245, 600));

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

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnOpen) {
					int returnVal = fileChooser.showOpenDialog(MainFrame.this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();

						try {
							int[] a;
							a = controller.openConfigurationFile(file);
							puzzle.drawInitConfig(a);
							pack();

							lblStepNo.setText("Optimal steps: -");
							lblRuntime.setText("Running time: -");
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
						} finally {
							btnAnimate.setEnabled(false);
						}
					} else {
						// Open command cancelled by user.
					}
				}
			}
		});

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

		btnAnimate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Animation/steps
				controller.playSolution();
				pack();
			}
		});

		setLayout(new BorderLayout());

		// Set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fifteen Puzzle Solver");
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(getRootPane());

		//pnlPuzzle.add(lblPuzzle);
		pnlPuzzle.add(puzzle);

		pnlButtonHolder.add(btnOpen);
		pnlButtonHolder.add(btnSolve);
		pnlButtonHolder.add(btnAnimate);

		pnlControl.setLayout(null);

		pnlControl.add(pnlButtonHolder);
		pnlControl.add(lblRuntime);
		pnlControl.add(lblStepNo);
		pnlControl.add(lblSteps);
		pnlControl.add(txpSteps);
		pnlControl.add(lblProgress);

		pnlButtonHolder.setLocation(5, 25);
		lblRuntime.setLocation(10, 70);
		lblStepNo.setLocation(10, 90);
		lblSteps.setLocation(10, 110);
		txpSteps.setLocation(10, 140);
		lblProgress.setLocation(58, 300);

		pnlButtonHolder.setSize(new Dimension(235, 50));
		lblRuntime.setSize(new Dimension(235, 32));
		lblStepNo.setSize(new Dimension(235, 32));
		lblSteps.setSize(new Dimension(235, 32));
		txpSteps.setSize(new Dimension(225, 86));
		lblProgress.setSize(new Dimension(128, 128));

		lblProgress.setVisible(false);

		pnlButtonHolder.setVisible(true);

		pnlMain.add(pnlPuzzle);
		pnlMain.add(pnlControl, BorderLayout.EAST);

		add(pnlMain);

		setVisible(true);
	}

	public void displayResult(SearchResult result) {
		String path = "";
		controller.setResult(result);
		for (char c : result.getPath())
			path += c + " ";
		lblRuntime.setText("Running time: " + result.getRunningTime() + "ms");
		lblStepNo.setText("Optimal steps: " + result.getSteps());
		txpSteps.setText(path);

		btnOpen.setEnabled(true);
		btnSolve.setEnabled(true);
		btnAnimate.setEnabled(true);
		lblProgress.setVisible(false);
	}
	
	public void step(char c) {
		switch(c) {
		case 'L': puzzle.moveLeft();break;
		case 'R': puzzle.moveRight();break;
		case 'U': puzzle.moveUp();break;
		case 'D': puzzle.moveDown();break;
		}
	}
	
	

}
