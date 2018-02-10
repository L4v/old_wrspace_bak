import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 720, HEIGHT = 480, SCALE = 2;
	public static final String NAME = "Pixel";
	public static boolean running = false;
	private BufferedImage image = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public SpriteSheetLoader loader;

	Random random = new Random();

	@Override
	public void run() {
		while (running) {
			init();
			render();
		}
	}

	public void stop() {
		running = false;
	}

	public void init() {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(Game.class.getResourceAsStream("/tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loader = new SpriteSheetLoader(sheet);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(NAME);
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		game.start();
	}

}
