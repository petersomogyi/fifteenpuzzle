package eu.eit.fifteenpuzzle.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelPuzzle extends JPanel {

	private static final long serialVersionUID = -1841313609569728670L;
	private JLabel[] tiles;
	private int empty;

	protected final int WIDTH = 50;
	protected final int HEIGHT = 50;
	protected final int MARGIN = 5;
	protected final int DELAY = 10;

	public PanelPuzzle() {
		super();
		setLayout(null);

		setPreferredSize(new Dimension(250, 250));
		setMaximumSize(new Dimension(250, 250));
		setMinimumSize(new Dimension(250, 250));

		tiles = new JLabel[16];
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				tiles[i * 4 + j] = new JLabel();
				tiles[i * 4 + j].setSize(new Dimension(WIDTH, HEIGHT));
				tiles[i * 4 + j].setHorizontalAlignment(JLabel.CENTER);
				tiles[i * 4 + j].setVerticalAlignment(JLabel.CENTER);
				tiles[i * 4 + j].setOpaque(true);
				tiles[i * 4 + j].setBackground(Color.LIGHT_GRAY);
				tiles[i * 4 + j].setBorder(new LineBorder(Color.BLACK));
				tiles[i * 4 + j].setVisible(false);
				tiles[i * 4 + j].setLocation(j * (WIDTH + MARGIN) + 20, i
						* (HEIGHT + MARGIN) + 10);
				this.add(tiles[i * 4 + j]);
			}
		}
	}

	public void drawInitConfig(int[] conf) {
		for (int i = 0; i < 16; ++i) {
			tiles[i].setText(String.valueOf(conf[i]));
			if (conf[i] == 0) {
				empty = i;
				tiles[i].setVisible(false);
			} else {
				tiles[i].setVisible(true);
			}
		}
		repaint();
	}

	protected void moveLeft() {
		int idx = empty - 1;

		tiles[empty].setLocation(tiles[idx].getLocation());
		animatedMove(tiles[idx], 'L');

		JLabel tmp = tiles[empty];
		tiles[empty] = tiles[idx];
		tiles[idx] = tmp;
		empty = idx;
	}

	protected void moveRight() {
		int idx = empty + 1;

		tiles[empty].setLocation(tiles[idx].getLocation());
		animatedMove(tiles[idx], 'R');

		JLabel tmp = tiles[empty];
		tiles[empty] = tiles[idx];
		tiles[idx] = tmp;
		empty = idx;
	}

	protected void moveUp() {
		int idx = empty - 4;

		tiles[empty].setLocation(tiles[idx].getLocation());
		animatedMove(tiles[idx], 'U');

		JLabel tmp = tiles[empty];
		tiles[empty] = tiles[idx];
		tiles[idx] = tmp;
		empty = idx;
	}

	protected void moveDown() {
		int idx = empty + 4;

		tiles[empty].setLocation(tiles[idx].getLocation());
		animatedMove(tiles[idx], 'D');

		JLabel tmp = tiles[empty];
		tiles[empty] = tiles[idx];
		tiles[idx] = tmp;
		empty = idx;
	}

	protected void animatedMove(JLabel lbl, char dir) {
		int dx = 0, dy = 0;
		switch (dir) {
		case 'L':
			dx = 1;
			dy = 0;
			break;
		case 'R':
			dx = -1;
			dy = 0;
			break;
		case 'U':
			dx = 0;
			dy = 1;
			break;
		case 'D':
			dx = 0;
			dy = -1;
			break;
		}

		int startX = (int) lbl.getLocation().getX();
		int startY = (int) lbl.getLocation().getY();
		int x = 0;
		int y = 0;
		while (Math.abs(x) < (WIDTH + MARGIN)
				&& Math.abs(y) < (HEIGHT + MARGIN)) {
			x += dx;
			y += dy;
			lbl.setLocation(startX + x, startY + y);
			repaint();
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
