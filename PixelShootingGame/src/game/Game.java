package game;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class Game extends Applet implements Runnable {

	private boolean[] k = new boolean[32767];
	private int m;

	public void start() {
		enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		new Thread(this).start();

	}

	public void run() {
		BufferedImage image = new BufferedImage(240, 240, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		Random random = new Random();
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		int[] sprites = new int[18 * 4 * 16 * 12 * 12];
		int pix = 0;
		for (int i = 0; i < 18; i++) {
			int skin = 0xFFf993;
			int clothes = 0xFFfff;

			if (i > 0) {
				skin = 0xa0ff90;
				clothes = (random.nextInt(0x1000000) & 0x7f7f7f);
			}
			for (int t = 0; t < 4; t++) {
				for (int d = 0; d < 16; d++) {
					double dir = d * Math.PI * 2 / 16.0;
					if (t == 1)
						dir += 0.5 * Math.PI * 2 / 16.0;
					if (t == 3)
						dir -= 0.5 * Math.PI * 2 / 16.0;

					double cos = Math.cos(dir);
					double sin = Math.sin(dir);

					for (int y = 0; y < 12; y++) {
						int col = 0x000000;
						for (int x = 0; x < 12; x++) {
							int xPix = (int) (cos * (x - 6) + sin * (y - 6) + 6.5);
							int yPix = (int) (cos * (y - 6) - sin * (x - 6) + 6.5);
							if (i == 17) {
								if (xPix > 3 && xPix < 9 && yPix > 3 && yPix < 9) {
									col = 0xff0000 + (t & 1) * 0xff00;
								}
							} else {
								if (t == 1 && xPix > 1 && xPix < 4 && yPix > 3 && yPix < 8) {
									col = skin;
								}
								if (t == 3 && xPix > 8 && xPix < 11 && yPix > 3 && yPix < 8) {
									col = skin;
								}
								if (xPix > 1 && xPix < 11 && yPix > 5 && yPix < 8) {
									col = clothes;
								}
								if (xPix > 4 && xPix < 8 && yPix > 4 && yPix < 8) {
									col = skin;
								}
							}
							sprites[pix++] = col;
							if (col > 1) {
								col = 1;
							} else {
								col = 0;
							}

						}
					}
				}
			}
		}
		int score = 0;
		int hurtDelay = 0;
		int bonusTime = 0;
		int xWin0 = 0;
		int yWin0 = 0;
		int xWin1 = 0;
		int yWin1 = 0;

		restart: while (true) {
			boolean gameStarted = false;
			int level = 0;
			int shootDelay = 0;
			int rushTime = 150;
			int damage = 20;
			int ammo = 20;
			int clips = 20;

			winLevel: while (true) {
				int tick = 0;
				level++;
				int[] map = new int[1024 * 1024];
				random = new Random(4329 + level);

				int[] monsterData = new int[320 * 16];
				{
					int i = 0;
					for (int y = 0; y < 1024; y++) {
						for (int x = 0; x < 1024; x++) {
							int br = random.nextInt(32) + 112;
							map[i] = (br / 3) << 16 | (br) << 8;

						}
					}
				}
			}
		}

	}
}
