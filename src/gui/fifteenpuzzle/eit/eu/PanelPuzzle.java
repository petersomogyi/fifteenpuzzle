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
	
	public PanelPuzzle() {
		tiles = new JLabel[16];
		for (int i = 0; i< 4; ++i) {
			for (int j = 0; j<4; ++j) {
				JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(WIDTH, HEIGHT));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				label.setBorder(new LineBorder(Color.BLACK));
				label.setVisible(false);
				this.add(label);
				tiles[i*4 + j] = label;
			}
		}
	}
	
	public void drawInitConfig(int[] conf) {
		for (int i=0; i<16; ++i){
			tiles[i].setText(String.valueOf(conf[i]));
			if (conf[i] == 0){
				empty = i;
				tiles[i].setVisible(false);
			} else {
				tiles[i].setVisible(true);
			}
		}
	}
	
	protected void moveLeft() {
		tiles[empty].setText(tiles[empty - 1].getText());
		tiles[empty].setVisible(true);
		tiles[empty - 1].setText("0");
		tiles[empty - 1].setVisible(false);
		empty = empty - 1;
		this.repaint();
	}
	protected void moveRight() {
		tiles[empty].setText(tiles[empty + 1].getText());
		tiles[empty].setVisible(true);
		tiles[empty + 1].setText("0");
		tiles[empty + 1].setVisible(false);
		empty = empty + 1;
		this.repaint();
	}
	protected void moveUp() {
		tiles[empty].setText(tiles[empty - 4].getText());
		tiles[empty].setVisible(true);
		tiles[empty - 4].setText("0");
		tiles[empty - 4].setVisible(false);
		empty = empty - 4;
		this.repaint();
	}
	protected void moveDown() {
		tiles[empty].setText(tiles[empty + 4].getText());
		tiles[empty].setVisible(true);
		tiles[empty + 4].setText("0");
		tiles[empty + 4].setVisible(false);
		empty = empty + 4;
		this.repaint();
	}
}
