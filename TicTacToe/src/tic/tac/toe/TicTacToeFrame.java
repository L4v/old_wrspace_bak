package tic.tac.toe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TicTacToeMain ticTacToe;

	public TicTacToeFrame() {

		// Set up the simple GUI elements
		super("Tic Tac Toe");
		setMinimumSize(new Dimension(610,670));
		setSize(600,670);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set up the Tic Tac Toe panel
		ticTacToe = new TicTacToeMain();
		add(ticTacToe, BorderLayout.CENTER);

		// Set up the bottom button
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.darkGray);
		JButton resetButton = new JButton("Reset");
		bottom.add(resetButton);
		add(bottom, BorderLayout.SOUTH);

		// Make the button reset the panel
		resetButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ticTacToe.reset();
				ticTacToe.requestFocus();
			}

		});

		ticTacToe.requestFocus();
		
		// Finally, make the frame visible!
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TicTacToeFrame();
	}

}
