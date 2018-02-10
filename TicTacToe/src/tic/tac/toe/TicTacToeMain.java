package tic.tac.toe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TicTacToeMain extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Panel handlers for math
	public static final int PANELWIDTH  = 600;
	public static final int PANELHEIGHT = 600;
	public static final int GRIDWIDTH   = PANELWIDTH/3;
	public static final int GRIDHEIGHT  = PANELHEIGHT/3;

	// This is the game array
	int[][] game;

	// Control the current player
	int player;

	// Establish whether game is done or not.
	boolean gameWon;

	// X and Y images
	Image xImage;
	Image yImage;

	public TicTacToeMain() {
		setSize(PANELWIDTH, PANELHEIGHT);
		
		// Set up a new game and load it with blanks
		game = new int[3][3];
		for (int i = 0; i < game.length; i++)
			for (int j = 0; j < game[i].length; j++)
				game[i][j] = 0;

		gameWon = false;

		// Set the first player to 1
		player = 1;

		// Load the images
		xImage = Toolkit.getDefaultToolkit().createImage("res/x.png");
		yImage = Toolkit.getDefaultToolkit().createImage("res/o.png");

		// Prepares them so that they can be instantly painted
		prepareImage(xImage, this);
		prepareImage(yImage, null);

		// Set up the user's input
		addMouseListener(new MouseHandler(this));
	}
	
	public void reset() {
		gameWon = false;
		player = 1;
		for (int i = 0; i < game.length; i++)
			for (int j = 0; j < game[i].length; j++)
				game[i][j] = 0;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (xImage == null || yImage == null)
			System.exit(1);
		paintGrid(g);
		paintGame(g);
	}

	private void paintGrid(Graphics g) {
		// Horizontal
		g.drawLine(5, GRIDHEIGHT, PANELWIDTH-5, GRIDHEIGHT);
		g.drawLine(5, 2*GRIDHEIGHT, PANELWIDTH-5, 2*GRIDHEIGHT);
		
		// Vertical
		g.drawLine(GRIDWIDTH, 5, GRIDWIDTH, PANELHEIGHT-5);
		g.drawLine(2*GRIDWIDTH, 5, 2*GRIDWIDTH, PANELHEIGHT-5);
	}

	private void paintGame(Graphics g) {
		g.setColor(Color.lightGray);

		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j< game[i].length; j++) {
				
				if (game[i][j] == 1) {
					g.drawImage(xImage, i*GRIDWIDTH, j*GRIDHEIGHT, null);
				}
				else if (game[i][j] == 2) {
					g.drawImage(yImage, i*GRIDWIDTH, j*GRIDHEIGHT, null);
				}
				else
					g.fillRect(i*GRIDWIDTH+1, j*GRIDHEIGHT+1, GRIDWIDTH-1, GRIDHEIGHT-1);
			}
		}
	}

	class MouseHandler implements MouseListener {

		TicTacToeMain pane;

		public MouseHandler(TicTacToeMain panel) {
			pane = panel;
		}

		public void mouseClicked(MouseEvent e) {
			if (!gameWon) {

				int column = e.getX()/GRIDWIDTH;
				int row = e.getY()/GRIDHEIGHT;

				if (game[column][row] == 0) {
					game[column][row] = player;

					checkWin();

					// Switch up the players
					if (player == 1)
						player = 2;
					else if (player == 2)
						player = 1;
				}
				pane.repaint();
			
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		private void checkWin() {

			// Check horizontal
			for (int i = 0; i < game.length; i++) {
				boolean rowWin = true;
				for (int j = 0; j < game[i].length; j++) {
					if (game[j][i] != player)
						rowWin = false;
				}
				if (rowWin == true) {
					displayWin();
					return;
				}
			}

			// Check vertical
			for (int i = 0; i < game.length; i++) {
				boolean rowWin = true;
				for (int j = 0; j < game[i].length; j++) {
					if (game[i][j] != player)
						rowWin = false;
				}
				if (rowWin == true) {
					displayWin();
					return;
				}
			}

			// Check diagonal
			boolean topWin = true;
			boolean bottomWin = true;
			for (int i = 0; i < game.length; i++) {
				if (game[i][i] != player)
					topWin = false;
				if (game[(game.length-(i+1))][i] != player)
					bottomWin = false;
			}
			if (topWin == true || bottomWin == true) {
				displayWin();
				return;
			}

			// If the array is full and there is no winner, it's a cat's game
			boolean isFull = true;
			for (int i = 0; i < game.length; i++)
				for (int j = 0; j < game[i].length; j++)
					if (game[i][j] == 0)
						isFull = false;
			if (isFull) {
				pane.repaint();
				JOptionPane.showMessageDialog
					(pane, "Izjednaceno!",
					"Tie", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

		}

		private void displayWin() {
			gameWon = true;
			pane.repaint();
			JOptionPane.showMessageDialog
					(pane, "Igrac " + player + " je pobedio!",
					"Pobednik", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

	}
}
