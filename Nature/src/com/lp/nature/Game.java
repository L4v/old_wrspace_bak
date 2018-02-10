package com.lp.nature;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.lp.nature.entity.mob.Mob;
import com.lp.nature.entity.mob.Player;
import com.lp.nature.graphics.Screen;
import com.lp.nature.graphics.Sprite;
import com.lp.nature.input.Keyboard;
import com.lp.nature.input.Mouse;
import com.lp.nature.level.Level;
import com.lp.nature.level.SpawnLevel;
import com.lp.nature.level.TileCoordinate;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;

	public static String name = "Nature";

	private Thread thread;
	private JFrame frame;
	private Screen screen;
	private Level level;
	private Player player;
	private Keyboard keyboard;
	private Mob mob;
	private boolean running = false;
	private byte[] test = { 1, 4, 3, 4 };

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		keyboard = new Keyboard();
		level = new SpawnLevel("/levels/spawn.png");
		TileCoordinate playerSpawn = new TileCoordinate(20, 64);
		player = new Player(playerSpawn.x(), playerSpawn.y(), keyboard);
		player.init(level);

		addKeyListener(keyboard);
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException a) {
			a.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		requestFocus();
		while (running == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(name + "|" + ticks + " ticks|" + frames + " fps|" + level.getProjectiles().size() + " projectiles|" + player.shooting);
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}

	public void tick() {
		keyboard.tick();
		player.tick();
		level.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int xScroll = player.x - screen.width / 2, yScroll = player.y - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);

	
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(name);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();

	}

}
