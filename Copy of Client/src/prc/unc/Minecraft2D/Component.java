package prc.unc.Minecraft2D;

import java.applet.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JFrame;

public class Component extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int pixelSize = 3;

	public static Dimension size = new Dimension(700, 540);
	public static Dimension pixel = new Dimension(size.width / pixelSize, size.height / pixelSize);
	public static boolean isRunning = false;
	public static double dir = 0;
	public static boolean isJumping = false;
	public String n = "Night";
	public static MobRectangle mobrectangle;
	public static DoubleRectangle doublerectangle;
	public boolean started = false;
	public static boolean isMoving = false;
	public static Rectangle Start = new Rectangle(100, 75, 25, 10);
	public static Point mse = new Point(0, 0);
	public static boolean isMouseLeft = false;
	public static boolean isMouseRight = false;
	public static boolean isMouseLeftc = false;
	public static boolean isMouseRightc = false;
	public static Arrow arrow;
	public static Spawner spawner;
	public static String name = "2DBlocks";
	public static Dimension realSize;
	private Image screen;
	public static boolean Paused = true;
	public static Level level;
	public static Rectangle startB = new Rectangle(90, 100, 55, 10);
	public static Character character;
	public static Inventory inventory;
	public static Sky sky;
	public static ArrayList<Mob> mob = new ArrayList<Mob>();
	public static int moveFromBorder = 0;
	public static double sX = moveFromBorder, sY = moveFromBorder;

	public Component() {
		addMouseListener(new Listening());
		addMouseMotionListener(new Listening());
		addMouseWheelListener(new Listening());
		setPreferredSize(size);
		addKeyListener(new Listening());
	}

	public void start() {

		requestFocus();
		new Tile();
		level = new Level();
		mobrectangle = new MobRectangle();
		doublerectangle = new DoubleRectangle();
		character = new Character(Tile.tileSize, Tile.tileSize * 2);
		inventory = new Inventory();
		sky = new Sky();
		spawner = new Spawner();
		isRunning = true;
		new Thread(this).start();

	}

	public void stop() {
		isRunning = false;
	}

	private static JFrame frame;

	public static void main(String[] args) {

		Component component = new Component();

		frame = new JFrame();
		frame.add(component);
		frame.pack();

		frame.setTitle(name);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		component.start();
	}

	public void tick() {
		if (!Paused && character.alive == true) {
			sky.tick();
			level.tick((int) sX, (int) sY, (pixel.width / Tile.tileSize) + 2, (pixel.height / Tile.tileSize) + 2);
			inventory.tick();
			character.tick();
			for (int i = 0; i < mob.toArray().length; i++) {
				mob.get(i).tick();
			}
		}
	}

	public void render() {
		Graphics g = screen.getGraphics();

		g.setColor(new Color(100, 100, 255));
		g.fillRect(0, 0, pixel.width, pixel.height);
		sky.render(g);
		level.render(g, (int) sX, (int) sY, (pixel.width / Tile.tileSize) + 2, (pixel.height / Tile.tileSize) + 2);
		character.render(g);
		for (int i = 0; i < mob.toArray().length; i++) {
			mob.get(i).render(g);
		}
		if (Sky.time == 1) {
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, 700, 540);
			g.setColor(new Color(250, 250, 250, 50));
			g.fillOval(90, 60, 50, 60);
			g.setColor(new Color(250, 250, 250));
		}
		g.setColor(new Color(0, 250, 0, 250));
		g.fill3DRect(4, 16, character.Health, 10, true);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.setColor(new Color(255, 0, 50));
		g.drawString("HP:" + character.Health, 4, 10);
		inventory.render(g);
		if (Paused) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(0, 0, 350, 270);
			g.setColor(new Color(250, 250, 250));
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.drawString("Press ESC", Start.x - 8 ,Start.y +8);
			g.setColor(new Color(250, 250, 250));
			g.setColor(new Color(250, 250, 250));
			g.drawString("To continue to the game:", Start.x - 40, Start.y - 4);
		}
		if(!character.alive){
			g.setColor(new Color(0, 0, 0, 250));
			g.fillRect(0, 0, 350, 270);
			g.setColor(new Color(250, 250, 250, 250));
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.drawString("Press ESC to exit the game", 55, 75);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.setColor(new Color(250, 0, 0));
			g.drawString("You died", 75, 50);
		}
		g = getGraphics();
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);

		g.dispose();
	}

	public void run() {
		screen = createVolatileImage(pixel.width, pixel.height);
		// ///////GAME LOOP////////
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
