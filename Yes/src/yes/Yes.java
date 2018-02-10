package yes;

import java.awt.*;
import javax.swing.*;


public class Yes extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = 600;
	public JFrame frame;
	
	public Yes(){
		JFrame frame = new JFrame();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.pack();
	}
	
	public static void main(String[] args){
		
	
	}
}
