package main;

import java.awt.*;
import java.applet.*;

import javax.swing.JFrame;


public class Component extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int pixelSize = 3;
	public static Dimension size = new Dimension(640, 480);
	public static Dimension pixel = new Dimension((size.width / pixelSize), (size.height / pixelSize));
	private Image screen;
	public static boolean isRunning = false;
	public static boolean started = false;
	private static JFrame frame;
	public static Listening listening;
	public static String title = "RPG";
	public static int dir = 0;
	
	public static Tile tile;
	public static Level level;
	public static TheCharacter character;
	
	public static int moveFromBorder = 0;
	public static double sX = moveFromBorder, sY = moveFromBorder;

	public Component() {
		setPreferredSize(size);
		addMouseListener(new Listening());
		addMouseWheelListener(new Listening());
		addMouseMotionListener(new Listening());
		addKeyListener(new Listening());
	}

	public static void main(String[] args) {
		Component component = new Component();

		frame = new JFrame();
		frame.add(component);
		frame.pack();

		frame.setTitle(title);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.start();
	}

	public void start() {
		requestFocus();
		listening = new Listening();
		level = new Level();
		character = new TheCharacter(Tile.tileSize, Tile.tileSize);
		
		new Tile();
		isRunning = true;
		new Thread(this).start();
	}
	public void tick() {
		level.tick();
		character.tick();
	}


	public void stop() {
		isRunning = false;
	}

	public void render() {
		Graphics g = screen.getGraphics();
		
		g.setColor(new Color(75, 100, 255, 255));
		g.fillRect(0, 0, pixel.width, pixel.height);
		level.render(g);
		character.render(g);
		
		g = getGraphics();
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();
	}

	public void run() {
		screen = createVolatileImage(pixel.width, pixel.height);

		while (isRunning) {
			tick();
			render();

			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		}
	}
}
