package gui.fifteenpuzzle.eit.eu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelPuzzle extends JPanel {
	private JLabel[] tiles;
	private int empty;
	
	protected final int WIDTH = 50;
	protected final int HEIGHT = 50;
	
	public void drawInitConfig(int[] conf) {
		tiles = new JLabel[16];

		for (int i = 0; i< 4; ++i) {
			for (int j = 0; j<4; ++j) {
				JLabel label = new JLabel(String.valueOf(conf[i*4 + j]));
				label.setPreferredSize(new Dimension(WIDTH, HEIGHT));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				label.setVisible(true);
				if (conf[i*4 + j] == 0) {
					empty = i*4 + j;
					label.setVisible(false);
				} else {
					label.setOpaque(true);
					label.setBackground(Color.LIGHT_GRAY);
					label.setBorder(new LineBorder(Color.BLACK));
				}
				this.add(label);
				tiles[i*4 + j] = label;
			}
		}
	}
	
	protected void moveLeft() {
		tiles[empty].setText(tiles[empty - 1].getText());
		tiles[empty].setVisible(true);
		tiles[empty - 1].setText("0");
		tiles[empty - 1].setVisible(false);
		empty = empty - 1;
	}
	protected void moveRight() {
		tiles[empty].setText(tiles[empty + 1].getText());
		tiles[empty].setVisible(true);
		tiles[empty + 1].setText("0");
		tiles[empty + 1].setVisible(false);
		empty = empty + 1;
	}
	protected void moveUp() {
		tiles[empty].setText(tiles[empty - 4].getText());
		tiles[empty].setVisible(true);
		tiles[empty - 4].setText("0");
		tiles[empty - 4].setVisible(false);
		empty = empty - 4;
	}
	protected void moveDown() {
		tiles[empty].setText(tiles[empty + 4].getText());
		tiles[empty].setVisible(true);
		tiles[empty + 4].setText("0");
		tiles[empty + 4].setVisible(false);
		empty = empty + 4;
	}
}
